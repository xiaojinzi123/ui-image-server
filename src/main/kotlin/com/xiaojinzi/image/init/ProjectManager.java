package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.*;
import com.xiaojinzi.image.util.BusyException;
import com.xiaojinzi.image.util.UtilPath;
import org.apache.ibatis.jdbc.Null;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProjectManager {

    private ProjectManager() {
    }

    public static ProjectManager getInstance() {
        return instance;
    }

    private static ProjectManager instance = new ProjectManager();

    private Vector<Project> projects = new Vector<>();

    private File proFilder = new File(UtilPath.getRootPath());

    private Map<Integer, Boolean> isPullMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, Boolean> isReadImageListMap = Collections.synchronizedMap(new HashMap<>());

    private Timer timer = null;

    /**
     * 可以被多次调用
     */
    public void init() {

        if (timer == null) {
            timer = new Timer();

            // 用于不断去拉取代码
            TimerTask task = new TimerTask() {

                @Override
                public void run() {

//                System.out.println("classPath : " + UtilPath.getClassPath());
//                System.out.println("proPath : " + UtilPath.getProjectPath());
//                System.out.println("rootPath : " + UtilPath.getRootPath());
//                System.out.println("WEB_INFPath : " + UtilPath.getWEB_INF());

                    doAllPull();

                }

            };

            timer.schedule(task, 0L, 600000L);
        }

    }

    private void doAllPull() {

        for (Project project : projects) {
            pullPro(project);
        }

    }

    /**
     * 拉取代码和读取代码不能一起进行
     *
     * @param pro
     */
    private synchronized void pullPro(final Project pro) {

        Boolean isReadImageList = isReadImageListMap.get(pro.getId());

        if (isReadImageList != null && isReadImageList) { // 如果正在读取,就放弃执行
            return;
        }

        Boolean isPullNow = isPullMap.get(pro.getId());

        if (isPullNow != null && isPullNow == true) {
            return;
        }

        isPullMap.put(pro.getId(), true);

        new Thread(() -> {

            // 项目的目录
            File proFoler = getProjectPath(pro);

            try {

                boolean isSuccess = cloneAndSwitchToDevOrPullToNew(pro);

                if (!isSuccess) {
                    throw new Exception("unknow");
                }

            } catch (Exception e) {

                deleteFile(proFoler);

            } finally {

                isPullMap.put(pro.getId(), false);

            }

        }).start();

    }

    public File getProjectPath(Project pro) {

        return new File(proFilder, pro.getName() + "_" + pro.getId());

    }

    /**
     * 本地是否存在这个项目的代码
     *
     * @param pro
     * @return
     */

    public boolean isLocalExist(Project pro) {

        if (pro == null) {

            throw new NullPointerException("project can not be null");

        }

        // 项目的目录
        File proFoler = getProjectPath(pro);

        return proFoler.exists();

    }

    /**
     * 会自动拉取这个项目
     * 可以重复调用没问题
     *
     * @param project
     */
    public void addProject(Project project) {

        if (project == null) {
            return;
        }

        Integer id = project.getId();

        if (id == null) {
            return;
        }

        boolean isExist = false;

        for (Project itemPro : projects) {
            if (id.equals(itemPro.getId())) {
                isExist = true;
                break;
            }
        }

        if (isExist) {
            return;
        }

        projects.add(project);

    }

    private boolean cloneAndSwitchToDevOrPullToNew(Project pro) throws IOException, GitAPIException {

        // 项目的目录
        File proFoler = getProjectPath(pro);

        if (proFoler.exists()) { // 说明是已经拉过代码了,更新代码

            try {

                Git.open(proFoler)
                        .pull()
                        .call();

            } catch (Exception ignore) {
                System.out.println("=================== 文件夹已经存在拉取代码失败");
            }


        } else { // 拉代码

            deleteFile(proFoler);

            // 拿到了代码
            Git git = Git.cloneRepository()
                    .setDirectory(proFoler)
                    .setURI(pro.getRemoteAddress())
                    .call();

            // 检索所有远程分支

            List<Ref> remoteBranchList = git.branchList()
                    .setListMode(ListBranchCommand.ListMode.REMOTE)
                    .call();

            // 尝试找到开发的分支
            Ref preparePullBranch = null;

            for (Ref ref : remoteBranchList) {
                String name = ref.getName();
                if (name.endsWith("/" + pro.getBranch())) {
                    preparePullBranch = ref;
                    break;
                }
            }

            if (preparePullBranch == null) {
                return false;
            }

            String localBranchName = pro.getBranch() + "_" + pro.getId();

            // 拉取远程的dev分支到本地
            git.branchCreate()
                    .setStartPoint(preparePullBranch.getName())
                    .setName(localBranchName)
                    .call();

            // 切换分支到本地
            git.checkout()
                    .setName(localBranchName)
                    .call();

        }

        return true;

    }

    private static void deleteFile(File f) {

        if (f == null || !f.exists()) {
            return;
        }

        if (f.isFile()) {
            f.delete();
        } else {
            File[] files = f.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
            f.delete();
        }

    }

    /**
     * 读取项目的资源目录的东西,这里要防止更新线程去更新
     *
     * @param pro
     * @return
     */
    @Nullable
    public synchronized ProjectDrawable readList(Project pro) throws BusyException {

        Boolean isPullNow = isPullMap.get(pro.getId());

        if (isPullNow != null && isPullNow == true) {
            throw new BusyException("此项目正在更新,请稍候再试");
        }

        // 项目的目录
        File proFoler = getProjectPath(pro);

        if (!proFoler.exists()) {
            addProject(pro);
            doAllPull();
            throw new BusyException("项目不存在,系统正在拉取项目的代码,请过几分钟后再请求");
        }

        isReadImageListMap.put(pro.getId(), true);

        File resFolder = new File(proFoler, "/app/src/main/res");

        ProjectDrawable result = doReadList(resFolder);

        isReadImageListMap.put(pro.getId(), false);

        return result;

    }

    private ProjectDrawable doReadList(File resFolder) {

        ProjectDrawable projectDrawable = new ProjectDrawable();

        ArrayList<DrawableCategory> result = new ArrayList<>();

        // key 为图片的名字,后面为同名的图片资源
        Map<String, List<Drawable>> map = new HashMap<>();

        File[] files = resFolder.listFiles();

        if (files != null) {
            for (File file : files) {

                // 如果是 drawable 文件夹
                if (file.exists() && file.isDirectory() && file.getName().startsWith("drawable")) {

                    soveDrawablFolder(file, map, projectDrawable);

                }

            }
        }

        // 将整理的资源文件转化成前端显示的形式

        DrawableCategory categoryAll = new DrawableCategory("全部");

        Set<Map.Entry<String, List<Drawable>>> entries = map.entrySet();

        for (Map.Entry<String, List<Drawable>> entry : entries) {

            String drawableName = entry.getKey();
            List<Drawable> drawables = entry.getValue();

            Drawables ds = new Drawables(drawableName);
            if (drawables != null) {
                ds.getDrawables().addAll(drawables);
            }

            categoryAll.getDrawablesList().add(ds);

        }

        result.add(categoryAll);

        projectDrawable.getCategories().addAll(result);

        return projectDrawable;

    }

    private void soveDrawablFolder(File drawableFolder, Map<String, List<Drawable>> map, ProjectDrawable projectDrawable) {

        if (drawableFolder == null || !drawableFolder.exists() || drawableFolder.isFile()) {
            return;
        }

        File[] files = drawableFolder.listFiles();

        if (files == null) {
            return;
        }

        for (File itemFile : files) {

            if (itemFile.isDirectory()) {
                continue;
            }

            String fileName = itemFile.getName();

            if (fileName.toLowerCase().endsWith(".png") ||
                    fileName.toLowerCase().endsWith(".jpg")) {

                List<Drawable> drawables = map.get(fileName);

                if (drawables == null) {

                    drawables = new ArrayList<>();
                    map.put(fileName, drawables);

                }

                // 物理机的真实路径,做一个转化
                String imagePath = itemFile.getPath();

                imagePath = imagePath.replace(proFilder.getPath(), "");

                if (imagePath.length() > 0 && imagePath.startsWith(System.getProperty("file.separator"))) {
                    imagePath = imagePath.substring(System.getProperty("file.separator").length());
                }

                drawables.add(new Drawable(drawableFolder.getName(), imagePath));

                if (!projectDrawable.getDrawableSorts().contains(drawableFolder.getName())) {
                    projectDrawable.getDrawableSorts().add(drawableFolder.getName());
                }

            }

        }

    }


}
