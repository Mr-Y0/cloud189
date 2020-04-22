package com.yyh.cloud189yyh.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cloud189Util {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

    /**
     * 签名
     * @param accessToken  token
     * @param operate 请求方式
     * @param requestURI 请求地址
     * @param appSecret app签名
     * @return
     */
    public static String signature(String accessToken, String operate, String requestURI, Date date, String appSecret) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessToken=").append(accessToken);
        sb.append("&Operate=").append(operate);
        sb.append("&RequestURI=").append(requestURI);
        sb.append("&Date=").append(Cloud189Util.toDate(date));
        return HMACSHA1Util.HmacSHA1Encrypt(sb.toString(),appSecret);
    }

    public static String toDate(Date date){
        return simpleDateFormat.format(date);
    }
}
