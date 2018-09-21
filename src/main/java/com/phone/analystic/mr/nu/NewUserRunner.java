package com.phone.analystic.mr.nu;

import com.phone.analystic.mr.OutputToMySqlFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

/**
 * @ClassName NewUserRunner
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description //TODO $
 **/
public class NewUserRunner implements Tool {
    private Configuration conf = new Configuration();

    @Override
    public void setConf(Configuration conf) {
        conf.addResource("outpu_mapping.xml");
        conf.addResource("writter_mapping.xml");
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return null;
    }

    @Override
    public int run(String[] strings) throws Exception {
       Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);


        //设置redcuce的输出格式类
        job.setOutputFormatClass(OutputToMySqlFormat.class);

        return 0;
    }
}