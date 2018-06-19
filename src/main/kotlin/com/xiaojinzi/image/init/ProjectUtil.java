package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.Project;
import com.xiaojinzi.image.util.UtilPath;

import java.io.File;

public class ProjectUtil {

    public static final File rootFilder = new File(UtilPath.getRootPath());

    public static File getProjectPath(Project pro) {

        return new File(rootFilder, pro.getName() + "_" + pro.getId());

    }

}
