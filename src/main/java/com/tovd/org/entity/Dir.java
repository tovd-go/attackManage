package com.tovd.org.entity;

import lombok.Data;


@Data
public class Dir {

    private Integer id;
    private String subjectName;
    private String dirOwner;
    private String dirPath;
    private String dirUrl;
    private String dirTime;
    private String dirCode;
    private String dirMain;
    private Integer pageNum;
    private Integer pageSize;
}
