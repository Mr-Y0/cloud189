package com.yyh.cloud189yyh.controller;

import com.yyh.cloud189yyh.service.ICloudPanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class Cloud189Controller {
    @Autowired
    private ICloudPanService cloudPanService;

    @RequestMapping("/list/{id}")
    public String list(@PathVariable Long id, Model model) throws Exception {
        Map<String, List> map = cloudPanService.listFiles(id);
        model.addAttribute("fileList",map.get("fileList"));
        model.addAttribute("folderList",map.get("folderList"));
        return "list";
    }

    @RequestMapping("/")
    public String index(Model model) throws Exception {
        Map<String, List> map = cloudPanService.listFiles(null);
        model.addAttribute("fileList",map.get("fileList"));
        model.addAttribute("folderList",map.get("folderList"));
        return "list";
    }

    @RequestMapping("/downLoad/{id}")
    public String getDownLoadUrl(@PathVariable Long id) throws Exception {
        String url = cloudPanService.getFileDownloadUrl(id);
        return "redirect:"+url;
    };

    @RequestMapping("/updateToken/{password}/{token}")
    @ResponseBody
    public Boolean updateToken(@PathVariable String token,@PathVariable String password){
        if(password.equals("qwertyuiop")){
            cloudPanService.setToken(token);
            return true;
        }else{
            return false;
        }
    }
}
