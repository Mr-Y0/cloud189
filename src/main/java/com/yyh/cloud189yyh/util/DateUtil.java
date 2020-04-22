package com.yyh.cloud189yyh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date format(String data) throws ParseException {
        Date parse = simpleDateFormat.parse(data);
        return parse;
    }
}
