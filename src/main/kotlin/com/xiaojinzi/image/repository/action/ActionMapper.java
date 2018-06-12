package com.xiaojinzi.image.repository.action;

import com.xiaojinzi.image.base.dao.BaseDao;
import com.xiaojinzi.image.bean.Action;
import com.xiaojinzi.image.bean.Project;
import org.apache.ibatis.annotations.Param;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ActionMapper extends BaseDao<Action, Integer> {

    @Nullable
    Action queryBySourceCategoryAndSource(@NotNull @Param("sourceCategory") String sourceCategory, @NotNull @Param("source") String source);

}
