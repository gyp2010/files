package com.phone;

import com.phone.analystic.modle.base.DateDimension;
import com.phone.analystic.modle.base.PlatformDimension;
import com.phone.analystic.mr.service.IDimension;
import com.phone.analystic.mr.service.impl.IDimensionImpl;
import com.phone.common.DateEnum;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName DimensionTest
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description //TODO $
 **/
public class DimensionTest {

    public static void main(String[] args) {
        PlatformDimension pl = new PlatformDimension("website");
        DateDimension dt = DateDimension.buildDate(324789342343829L,DateEnum.DAY);
        IDimension dimension = new IDimensionImpl();
        try {
            System.out.println(dimension.getDiemnsionIdByObject(pl));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}