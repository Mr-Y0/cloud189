package com.yyh.cloud189yyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String loginName;
    private Long capacity;
    private Long available;
    private Long maxFilesize;
}
