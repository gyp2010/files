package com.phone.analystic.mr.service;

import com.phone.analystic.modle.base.BaseDimension;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName IDimension
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 根据维度获取对应的id的接口
 **/
public interface IDimension {
    /**
     *
     * @param dimension  基础维度的对象
     * @return
     * @throws IOException
     * @throws SQLException
     */
    int getDiemnsionIdByObject(BaseDimension dimension) throws IOException,SQLException;
}