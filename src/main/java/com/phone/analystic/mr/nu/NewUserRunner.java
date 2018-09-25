package com.phone.analystic.mr.nu;

import com.phone.Util.TimeUtil;
import com.phone.analystic.modle.StatsUserDimension;
import com.phone.analystic.modle.value.map.TimeOutputValue;
import com.phone.analystic.modle.value.reduce.OutputWritable;
import com.phone.analystic.mr.OutputToMySqlFormat;
import com.phone.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @ClassName NewUserRunner
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 新增用户的runner
truncate dimension_browser;
truncate dimension_currency_type;
truncate dimension_date;
truncate dimension_event;
truncate dimension_inbound;
truncate dimension_kpi;
truncate dimension_location;
truncate dimension_os;
truncate dimension_payment_type;
truncate dimension_platform;
truncate event_info;
truncate order_info;
truncate stats_device_browser;
truncate stats_device_location;
truncate stats_event;
truncate stats_hourly;
truncate stats_inbound;
truncate stats_order;
truncate stats_user;
truncate stats_view_depth;

 **/
public class NewUserRunner implements Tool {
    private static final Logger logger = Logger.getLogger(NewUserRunner.class);
    private static Configuration conf = new Configuration();

    public static void main(String[] args) {
        try {
            ToolRunner.run(conf,new NewUserRunner(),args);
        } catch (Exception e) {
            logger.warn("运行新增用户指标失败.",e);
        }
    }

    @Override
    public void setConf(Configuration conf) {
        conf.addResource("output_mapping.xml");
        conf.addResource("output_writter.xml");
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    @Override
    public int run(String[] args) throws Exception {
       Configuration conf = getConf();

        //处理设置参数
        handleArgs(conf,args);

        Job job = Job.getInstance(conf);

        job.setJarByClass(NewUserRunner.class);


        //设置map
        job.setMapperClass(NewUserMapper.class);
        job.setMapOutputKeyClass(StatsUserDimension.class);
        job.setMapOutputValueClass(TimeOutputValue.class);

        //设置reduce
        job.setReducerClass(NewUserReducer.class);
        job.setOutputKeyClass(StatsUserDimension.class);
        job.setOutputValueClass(OutputWritable.class);

        //处理输入
        this.handleInput(job);

        //处理输出
        job.setOutputFormatClass(OutputToMySqlFormat.class);
//        return job.waitForCompletion(true)?0:1;
        if(job.waitForCompletion(true)){
            this.computeTotalNewUser(job);
          return 0;
        } else {
            return 1;
        }
    }

    /**
     * 计算新增的总用户
     * 1、获取运行日期当天和前一天的时间维度，并获取其对应的时间维度id，判断id是否大于0。
     * 2、根据时间维度的id获取前天的总用户和当天的新增用户。
     * 3、更新新增总用户
     * @param job
     */
    private void computeTotalNewUser(Job job) {
    }

    /**
     *
     * @param conf
     * @param args
     */
    private void handleArgs(Configuration conf, String[] args) {
        String date = null;
        if(args.length > 0){
            //循环args
            for(int i = 0 ; i<args.length;i++){
                //判断参数中是否有-d
                if(args[i].equals("-d")){
                    if(i+1 <= args.length){
                        date = args[i+1];
                        break;
                    }
                }
            }

            //判断
            if(StringUtils.isEmpty(date)){
                date = TimeUtil.getYesterday();
            }
            //将date存储到conf中
            conf.set(GlobalConstants.RUNNING_DATE,date);
        }
    }

    /**
     * 设置输入输出
     * @param job
     */
    private void handleInput(Job job) {
        String [] fields = job.getConfiguration().get(GlobalConstants.RUNNING_DATE).split("-");
        String month = fields[1];
        String day = fields[2];
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            Path inpath = new Path("/ods/"+month+"/"+day);
            if(fs.exists(inpath)){
                FileInputFormat.addInputPath(job,inpath);
            } else {
                throw  new RuntimeException("输入路径不存储在.inpath:"+inpath.toString());
            }
        } catch (IOException e) {
            logger.warn("设置输入输出路径异常.",e);
        }
    }
}