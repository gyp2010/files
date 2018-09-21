package com.phone.analystic.mr;

import com.phone.analystic.modle.StatsBaseDimension;
import com.phone.analystic.modle.value.StatsOutpuValue;
import com.phone.analystic.mr.service.IDimension;
import org.apache.hadoop.conf.Configuration;

import java.sql.PreparedStatement;

/**
 * 操作结果表的接口
 */
public interface IOutputWritter {

    /**
     * 为每一个kpi的最终结果赋值的接口
     * @param conf
     * @param key
     * @param value
     * @param ps
     * @param iDimension
     */
    void ouput(Configuration conf, StatsBaseDimension key,
               StatsOutpuValue value, PreparedStatement ps, IDimension iDimension);
}
