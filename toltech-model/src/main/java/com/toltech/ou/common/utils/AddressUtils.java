package com.toltech.ou.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.toltech.ou.common.config.IdongConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:01
 * @Version 1.0
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (IdongConfig.isAddressEnabled()) {
            String rspStr = com.toltech.ou.system.common.utils.HttpUtils.sendPost(IP_URL, "ip=" + ip);
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            JSONObject data = obj.getObject("data", JSONObject.class);
            String region = data.getString("region");
            String city = data.getString("city");
            address = region + " " + city;
        }
        return address;
    }
}
