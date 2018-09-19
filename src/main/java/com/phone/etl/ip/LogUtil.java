package com.phone.etl.ip;

import com.phone.common.Constants;
import com.phone.etl.IpUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName LogUtil
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 对需要清洗的正行日志进行解析
 **/
public class LogUtil {
    private static final Logger logger = Logger.getLogger(LogUtil.class);

    /**
     * 处理正行的日志
     * @param log :221.13.21.192^A1535611950.612^A221.13.21.192^A/
     *            shopping.jsp?c_time=1535611600666&oid=123461&u_mid=dc64823d-5cb7-4e3d-8a87-fa2b4e096ea0&pl=java_server&en=e_cs&sdk=jdk&ver=1
     * @return
     */
    public static Map<String,String> parserLog(String log){
        Map<String,String> map = new ConcurrentHashMap<String,String>();
        if(StringUtils.isNotEmpty(log)){
            String [] fields = log.split("\\^A");
            if(fields.length == 4){

                //存储
                map.put(Constants.LOG_IP,fields[0]);
                map.put(Constants.LOG_SERVER_TIME,fields[1].replaceAll("\\.",""));
                //参数列表，单独处理
                String params = fields[3];
                handleParams(params,map);
                //处理ip解析
                handleIp(map);
                //处理userAgent
                handleUserAgent(map);
            }
        }
        return map;
    }

    /**
     将map中的ip取出来，然后解析成国家省市，最终存储到map中
     * @param map
     */
    private static void handleIp(Map<String,String> map) {
        if(map.containsKey(Constants.LOG_IP)){
            IpUtil.RegionInfo info = IpUtil.getRegionInfoByIp(map.get(Constants.LOG_IP));
            //将值存储到map中
            map.put(Constants.LOG_COUNTRY,info.getCountry());
            map.put(Constants.LOG_PROVINCE,info.getProvince());
            map.put(Constants.LOG_CITY,info.getCity());
        }
    }

    /**
     * 重map中取出b_iev的值，然后解析，然后存储到map中
     * @param map
     */
    private static void handleUserAgent(Map<String,String> map) {
        if(map.containsKey(Constants.LOG_USERAGENT)){
            UserAgentUtil.UserAgentInfo info = UserAgentUtil.parserUserAgent(map.get(Constants.LOG_USERAGENT));
            //将值存储到map中
            map.put(Constants.LOG_BROWSER_NAME,info.getBrowserName());
            map.put(Constants.LOG_BROWSER_VERSION,info.getBrowserVersion());
            map.put(Constants.LOG_OS_NAME,info.getOsName());
            map.put(Constants.LOG_OS_VERSION,info.getOsVersion());
        }
    }



    /**
     * 处理参数列表
     * @param params：
     *              shopping.jsp?c_time=1535611600666&oid=123461&u_mid=dc64823d-5cb7-4e3d-8a87-fa2b4e096ea0&pl=java_server&en=e_cs&sdk=jdk&ver=1
     * @param map
     *
     */
    private static void handleParams(String params, Map<String,String> map) {
        try {
            if(StringUtils.isNotEmpty(params)){
                int index = params.indexOf("?");
                if(index > 0){
                    String fields [] = params.substring(index+1).split("&");
                    //c_time=1535611600666
                    for (String field:fields) {
                        String kvs [] = field.split("=");
                        String v = URLDecoder.decode(kvs[1],"utf-8");
                        String k = kvs[0];
                        //判断key是否为空
                        if(StringUtils.isNotEmpty(k)){
                            //存储到map中
                            map.put(k,v);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn("value进行urldecode解码异常.",e);
        }
    }
}