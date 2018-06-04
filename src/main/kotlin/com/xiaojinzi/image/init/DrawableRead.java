package com.xiaojinzi.image.init;

import com.xiaojinzi.image.bean.Project;
import com.xiaojinzi.image.bean.ProjectDrawable;
import org.jetbrains.annotations.NotNull;

/**
 * 这是一个接口,表示实现资源文件读取的接口
 */
public interface DrawableRead {

    /**
     * 读取一个项目下的资源文件,转化成一个ProjectDrawable对象
     *
     * @param pro
     * @return
     */
    @NotNull
    ProjectDrawable read(Project pro);

}
