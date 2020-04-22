package com.yyh.cloud189yyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {
    private Long id;
    private String name;
    private Long size;
    private Date createDate;
    private String md5;
}
