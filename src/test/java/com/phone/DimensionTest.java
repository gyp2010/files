package com.phone;

import com.phone.analystic.modle.base.DateDimension;
import com.phone.analystic.modle.base.PlatformDimension;
import com.phone.common.DateEnum;

/**
 * @ClassName DimensionTest
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description //TODO $
 **/
public class DimensionTest {

    public static void main(String[] args) {
        PlatformDimension pl = new PlatformDimension("ios");
        DateDimension dt = DateDimension.buildDate(324789342343829L,DateEnum.DAY);
//        int id = aa(dt); //1
    }
}