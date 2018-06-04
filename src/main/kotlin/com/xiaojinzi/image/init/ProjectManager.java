package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.*;
import com.xiaojinzi.image.util.BusyException;
import com.xiaojinzi.image.util.UtilPath;
import org.apache.ibatis.jdbc.Null;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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
            File proFoler = ProjectUtil.getProjectPath(pro);

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
        File proFoler = ProjectUtil.getProjectPath(pro);

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
        File proFoler = ProjectUtil.getProjectPath(pro);

        if (proFoler.exists()) { // 说明是已经拉过代码了,更新代码

            try {

                PullCommand pullCommand = Git.open(proFoler)
                        .pull();

                // 说明有帐号密码
                if (pro.getGitName() != null && pro.getGitName().length() > 0) {
                    pullCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(pro.getGitName(), pro.getGitPass()));
                }

                pullCommand.call();

            } catch (Exception ignore) {

                System.out.println("=================== 文件夹已经存在拉取代码失败");

            }


        } else { // 拉代码

            deleteFile(proFoler);

            // 拿到了代码
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setDirectory(proFoler)
                    .setURI(pro.getRemoteAddress());
            if (pro.getGitName() != null && pro.getGitName().length() > 0) {
                cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(pro.getGitName(), pro.getGitPass()));
            }
            Git git = cloneCommand.call();

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
     * 这个方法一定要注意,标志符号的设置一定有有始有终!!
     *
     * @param pro
     * @return
     */
    @Nullable
    public synchronized ProjectDrawable readList(Project pro) throws BusyException {

        try {

            Boolean isPullNow = isPullMap.get(pro.getId());

            if (isPullNow != null && isPullNow == true) {
                throw new BusyException("此项目正在更新,请稍候再试");
            }

            // 项目的目录
            File proFoler = ProjectUtil.getProjectPath(pro);

            if (!proFoler.exists()) {
                addProject(pro);
                doAllPull();
                throw new BusyException("项目不存在,系统正在拉取项目的代码,请过几分钟后再请求");
            }

            // 标识这个项目正在读取
            isReadImageListMap.put(pro.getId(), true);

            // 拿到资源读取接口
            DrawableRead drawableRead = pro.tryGetDrawableRead();

            if (drawableRead == null) {

                throw new BusyException("此项目没有对应的读取项目的实现！");

            } else {

                ProjectDrawable result = drawableRead.read(pro);

                return result;

            }

        } finally {
            // 标识这个项目已经完成读取
            isReadImageListMap.put(pro.getId(), false);
        }

    }

}
