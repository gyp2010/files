package com.phone.analystic.mr.nu;

import com.phone.analystic.modle.StatsUserDimension;
import com.phone.analystic.modle.value.map.TimeOutputValue;
import com.phone.analystic.modle.value.reduce.OutputWritable;
import com.phone.common.KpiType;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName NewUserReducer
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 用户模块下的新增用户的reducer
 **/
public class NewUserReducer extends Reducer<StatsUserDimension,TimeOutputValue,
        StatsUserDimension,OutputWritable> {
    private static final Logger logger = Logger.getLogger(NewUserReducer.class);
    private OutputWritable v = new OutputWritable();
    private Set unique = new HashSet();  //用于去重uuid
    private MapWritable map = new MapWritable();
    private Map<StatsUserDimension,Set<String>> cache = new HashMap<StatsUserDimension,Set<String>>();

    @Override
    protected void reduce(StatsUserDimension key, Iterable<TimeOutputValue> values, Context context) throws IOException, InterruptedException {
        //清空map
        this.unique.clear();
        //循环
        for (TimeOutputValue tv: values) {
            this.unique.add(tv.getId());
            /*if(key.getStatsCommonDimension().getKpiDimension().getKpiName().equals(KpiType.NEW_USER.kpiName)){
                if(this.cache.containsKey(StatsUserDimension.clone(key))){
                    this.cache.get(StatsUserDimension.clone(key)).add(tv.getId());
                } else {
                Set<String> set = new HashSet<String>();
                set.add(tv.getId());
                this.cache.put(StatsUserDimension.clone(key),set);
            }
            }
            //如果是browser_new_user
            if(key.getStatsCommonDimension().getKpiDimension().getKpiName().equals(KpiType.BROWSER_NEW_USER.kpiName)){
                if(this.cache.containsKey(StatsUserDimension.clone(key))){
                    this.cache.get(StatsUserDimension.clone(key)).add(tv.getId());
                } else {
                    Set<String> set = new HashSet<String>();
                    set.add(tv.getId());
                    this.cache.put(StatsUserDimension.clone(key),set);
                }
            }*/
        }


        //构造输出的v
       this.v.setKpi(KpiType.valueOfKpiName(key.getStatsCommonDimension().getKpiDimension().getKpiName()));
        this.map.put(new IntWritable(-1),new IntWritable(this.unique.size()));
        this.v.setValue(this.map);
        //输出
        context.write(key,this.v);

        //循环map输出
       /* for (Map.Entry<StatsUserDimension,Set<String>> en:cache.entrySet()){
            this.v.setKpi(KpiType.valueOfKpiName(en.getKey().getStatsCommonDimension().getKpiDimension().getKpiName()));
            this.map.put(new IntWritable(-1),new IntWritable(en.getValue().size()));
            this.v.setValue(this.map);
            //输出
            context.write(key,this.v);
        }*/
    }
}