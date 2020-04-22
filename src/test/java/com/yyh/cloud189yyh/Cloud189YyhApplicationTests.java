package com.yyh.cloud189yyh;

import com.sun.crypto.provider.HmacSHA1;
import com.yyh.cloud189yyh.service.ICloudPanService;
import com.yyh.cloud189yyh.util.Cloud189Util;
import com.yyh.cloud189yyh.util.HMACSHA1Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@SpringBootTest
class Cloud189YyhApplicationTests {

    private Logger logger= LoggerFactory.getLogger(Cloud189YyhApplicationTests.class);
    @Test
    void contextLoads() throws Exception {



    }

}
