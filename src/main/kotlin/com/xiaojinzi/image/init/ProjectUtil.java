package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.Project;
import com.xiaojinzi.image.util.UtilPath;

import java.io.File;

public class ProjectUtil {

    public static final File proFilder = new File(UtilPath.getRootPath());

    public static File getProjectPath(Project pro) {

        return new File(proFilder, pro.getName() + "_" + pro.getId());

    }

}
