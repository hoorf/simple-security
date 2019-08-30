package com.github.ruifengho.simplesecurity.util;


import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.TreeMap;

public class SignUtil {


    public static String sign(Object obj, String keys) {
        try {
            Map<String, String> stringMap = BeanUtils.describe(obj);
            TreeMap<String, String> sortMap = new TreeMap<>(stringMap);

            StringBuilder sb = new StringBuilder();
            for (String key : sortMap.keySet()) {
                if(!"class".equals(key)) {
                    sb.append(key).append("=").append(sortMap.get(key)).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            String str = sb.toString();
            String sign = DesUtil.encrypt(str, keys);

            return str + "&sign=" + sign;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
