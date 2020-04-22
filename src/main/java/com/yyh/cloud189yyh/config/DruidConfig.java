package com.yyh.cloud189yyh.config;

import com.alibaba.druid.support.http.StatViewFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DruidConfig {
    @Value("${druid.name}")
    private String userName;
    @Value("${druid.password}")
    private String password;
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> statViewServletServlet= new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        statViewServletServlet.addInitParameter("loginUsername",userName);
        statViewServletServlet.addInitParameter("loginPassword",password);
        return statViewServletServlet;
    }

    @Bean
    public FilterRegistrationBean statViewFilter(){
        FilterRegistrationBean<StatViewFilter> statViewFilterFilter = new FilterRegistrationBean(new WebStatFilter());
        statViewFilterFilter.addUrlPatterns("/*");
        statViewFilterFilter.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return statViewFilterFilter;
    }
}