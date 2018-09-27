package com.phone.analystic.hive;

import com.phone.Util.TimeUtil;
import com.phone.analystic.modle.base.DateDimension;
import com.phone.analystic.mr.service.IDimension;
import com.phone.analystic.mr.service.impl.IDimensionImpl;
import com.phone.common.DateEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName DateDimensionUdf
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 获取时间维度id
 **/
public class DateDimensionUdf extends UDF {

    IDimension iDimension = new IDimensionImpl();

    public int evaluate(String date){
        if(StringUtils.isEmpty(date)){
            date = TimeUtil.getYesterday();
        }
        DateDimension dateDimension = DateDimension.buildDate(TimeUtil.parseString2Long(date),DateEnum.DAY);
        int id = 0;
        try {
            id = iDimension.getDiemnsionIdByObject(dateDimension);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void main(String[] args) {
        System.out.println(new DateDimensionUdf().evaluate("2018-09-19"));
    }
}