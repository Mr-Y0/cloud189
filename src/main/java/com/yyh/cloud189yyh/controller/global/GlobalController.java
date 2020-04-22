package com.yyh.cloud189yyh.controller.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {

    private Logger logger= LoggerFactory.getLogger(GlobalController.class);

    @ExceptionHandler(Exception.class)
    public String exception(Exception ex, Model model){
        logger.error(ex.getMessage());
        ex.printStackTrace();
        model.addAttribute("errorMsg",ex.getMessage());
        return "exception";
    }
}
