package com.yyh.cloud189yyh.service.impl;

import com.yyh.cloud189yyh.entity.File;
import com.yyh.cloud189yyh.entity.Folder;
import com.yyh.cloud189yyh.entity.User;
import com.yyh.cloud189yyh.service.ICloudPanService;
import com.yyh.cloud189yyh.util.Cloud189Util;
import com.yyh.cloud189yyh.util.DateUtil;
import com.yyh.cloud189yyh.util.HMACSHA1Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class Cloud189Service implements ICloudPanService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${cloud189.AppKey}")
    private String appKey;
    @Value("${cloud189.AppSecret}")
    private String appSecret;
    @Value("${cloud189.accessToken}")
    private String accessToken;
    @Value("${cloud189.userInfoUrl}")
    private String userInfoUrl;
    @Value("${cloud189.listFilesUrl}")
    private String listFilesUrl;
    @Value("${cloud189.fileDownloadUrl}")
    private String fileDownloadUrl;
    @Value("${cloud189.accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${cloud189.redis.urlKey}")
    private String urlKey;
    @Value("${cloud189.redis.listFileKey}")
    private String listFileKey;

    private Logger logger = LoggerFactory.getLogger(Cloud189Service.class);

    @Override
    public String authorize() {
        return null;
    }

    @Override
    public String accessToken() throws Exception {
//        Date date = new Date();
//        HashMap<String, String> properties = new HashMap<>();
//        StringBuilder sb=new StringBuilder();
//        sb.append("appKey=").append(appKey);
//        sb.append("&timestamp=").append(date.getTime());
//        properties.put("appKey",appKey);
//        properties.put("grantType","password");
//        properties.put("timestamp",date.getTime()+"");
//        properties.put("appSignature",HMACSHA1Util.HmacSHA1Encrypt(sb.toString(), appSecret));
//        properties.put("loginName","13383875237");
//        properties.put("password","123456789");
//        HttpEntity httpEntity = new HttpEntity<>(properties);
//        String s = restTemplate.postForObject(accessTokenUrl, httpEntity, String.class);
//        System.out.println("s = " + s);
        return null;
    }

    @Override
    public User userInfo() throws Exception {
        HttpEntity<String> httpEntity = defaultHttpEntity(HttpMethod.GET, "/getUserInfo.action");
        ResponseEntity<String> responseEntity = restTemplate.exchange(userInfoUrl, HttpMethod.GET, httpEntity, String.class);
        logger.debug(responseEntity.getBody());
        Document document = Jsoup.parse(responseEntity.getBody());
        String loginName = document.select("user loginName").eq(0).text();
        Long capacity = Long.valueOf(document.select("user capacity").eq(0).text());
        Long available = Long.valueOf(document.select("user available").eq(0).text());
        Long maxFilesize = Long.valueOf(document.select("user maxFilesize").eq(0).text());
        User user = new User(loginName, capacity, available, maxFilesize);
        return user;
    }

    @Override
    public Map<String, List> listFiles(Long folderId) throws Exception {
        Object temp = redisTemplate.opsForValue().get(listFileKey + folderId);
        if(temp!=null){
            HashMap<String, List> result =(HashMap<String, List>)temp;
            return result;
        }else{
            ArrayList<Folder> folderList = new ArrayList<>();
            ArrayList<File> fileList = new ArrayList<>();
            HttpEntity<String> httpEntity = defaultHttpEntity(HttpMethod.GET, "/listFiles.action");
            ResponseEntity<String> responseEntity = null;
            if (folderId == null) {
                responseEntity = restTemplate.exchange(listFilesUrl, HttpMethod.GET, httpEntity, String.class);
            } else {
                responseEntity = restTemplate.exchange(listFilesUrl + "?folderId={folderId}", HttpMethod.GET, httpEntity, String.class, folderId);
            }
            logger.debug(responseEntity.getBody());
            Document document = Jsoup.parse(responseEntity.getBody());
            Elements folders = document.select("folder");
            ListIterator<Element> foldersIterator = folders.listIterator();
            while (foldersIterator.hasNext()) {
                Element next = foldersIterator.next();
                Long id = Long.valueOf(next.select("id").eq(0).text());
                Long parentId = Long.valueOf(next.select("parentId").eq(0).text());
                String name = next.select("name").eq(0).text();
                Date createDate = DateUtil.format(next.select("createDate").eq(0).text());
                Folder folder = new Folder(id, parentId, name, createDate);
                folderList.add(folder);
            }

            Elements files = document.select("file");
            ListIterator<Element> filesIterator = files.listIterator();
            while (filesIterator.hasNext()) {
                Element next = filesIterator.next();
                Long id = Long.valueOf(next.selectFirst("id").text());
                String name = next.selectFirst("name").text();
                Long size = Long.valueOf(next.selectFirst("size").text());
                Date createDate = DateUtil.format(next.selectFirst("createDate").text());
                String md5 = next.selectFirst("md5").text();
                File file = new File(id, name, size, createDate, md5);
                fileList.add(file);
            }
            HashMap<String, List> result = new HashMap<>();
            result.put("fileList", fileList);
            result.put("folderList", folderList);
            redisTemplate.opsForValue().set(listFileKey + folderId,result,5,TimeUnit.SECONDS);
            return result;
        }

    }

    @Override
    public Object searchFiles(long folderId, String filename) {
        return null;
    }

    @Override
    public String getFileDownloadUrl(long fileId) throws Exception {
        Object temp = redisTemplate.opsForValue().get(urlKey + fileId);
        if(temp!=null){
            return temp.toString();
        }else{
            HttpEntity<String> httpEntity = defaultHttpEntity(HttpMethod.GET, "/getFileDownloadUrl.action");
            ResponseEntity<String> responseEntity = restTemplate.exchange(fileDownloadUrl + "?fileId={fileId}", HttpMethod.GET, httpEntity, String.class, fileId);
            logger.debug(responseEntity.getBody());
            Document document = Jsoup.parse(responseEntity.getBody());
            String downloadUrl = document.selectFirst("fileDownloadUrl").text();
            redisTemplate.opsForValue().set(urlKey+fileId,downloadUrl,3, TimeUnit.MINUTES);
            return downloadUrl;
        }

    }

    @Override
    public Object createFolder(long parentFolderId, String folderName) {
        return null;
    }

    @Override
    public Object renameFile(long fileId, String destFileName) {
        return null;
    }

    @Override
    public Object deleteFolder(long fileId) {
        return null;
    }

    @Override
    public void setToken(String token) {
        this.accessToken=token;
    }

    /**
     * 默认 请求头信息
     *
     * @return
     */
    public HttpEntity<String> defaultHttpEntity(HttpMethod method, String reqUrl) throws Exception {
        Date date = new Date();
        HttpHeaders headers = new HttpHeaders();
        headers.add("AccessToken", accessToken);
        headers.add("Signature", Cloud189Util.signature(accessToken, method.name(), reqUrl, date, appSecret));
        headers.add("Date", Cloud189Util.toDate(date));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }
}
