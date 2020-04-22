package com.yyh.cloud189yyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Folder {
    private Long id;
    private Long parentId;
    private String name;
    private Date createDate;
}
