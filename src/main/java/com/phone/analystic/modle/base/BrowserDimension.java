package com.phone.analystic.modle.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @ClassName BrowserDimension
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description
 **/
public class BrowserDimension extends BaseDimension{

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }


    @Override
    public int compareTo(BaseDimension o) {
        return 0;
    }
}