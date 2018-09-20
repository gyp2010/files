package com.phone.analystic.mr.service.impl;

import com.phone.analystic.modle.base.BaseDimension;
import com.phone.analystic.mr.service.IDimension;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName IDimensionImpl
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 获取基础维度id的实现
 **/
public class IDimensionImpl implements IDimension {
    private Map<String,Integer> cache = new LinkedHashMap<String,Integer>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return this.size() > 5000;
        }
    };


    /**
     *1、根据维度对象里面的属性值，赋值给对应的sql，然后查询，如果有则返回对应的维度Id.
     *   如果查询没有，则先添加到数据库中并返回对应的id值。
     *
     * @param dimension  基础维度的对象
     * @return
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public int getDiemnsionIdByObject(BaseDimension dimension) throws IOException, SQLException {
        return 0;
    }

    //自定输出格式OutputFormat
//    DBOutputFormat
}