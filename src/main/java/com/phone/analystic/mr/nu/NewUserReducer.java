package com.phone.analystic.mr.nu;

import com.phone.analystic.modle.StatsUserDimension;
import com.phone.analystic.modle.value.map.TimeOutputValue;
import com.phone.analystic.modle.value.reduce.OutputWritable;
import org.antlr.runtime.misc.Stats;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @ClassName NewUserReducer
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description //TODO $
 **/
public class NewUserReducer extends Reducer<StatsUserDimension,TimeOutputValue,
        StatsUserDimension,OutputWritable> {

    //2018-09-20 ios IE 8.0 new_user 3688
    //2018-09-20 ios IE 9.0 new_user 12
    //2018-09-20 ios IE 8.0 houryly_user 12
    //2018-09-20 ios IE 9.0 houryly_user 500
}