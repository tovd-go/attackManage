package com.tovd.org.entity;

import lombok.Data;

@Data
public class Email {

    private Integer Id;
    private String subjectName;
    private String emailName;
    private String emailAll;
    private String emailFrom;
    private String emailTime;
    private Integer pageNum;
    private Integer pageSize;
}
