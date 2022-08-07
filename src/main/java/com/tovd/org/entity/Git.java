package com.tovd.org.entity;

import lombok.Data;

@Data
public class Git {


    private Integer id;
    private String gitOwner;
    private String gitFileName;
    private String gitPath;
    private String gitDesc;
    private String gitFileTime;
    private String gitTime;
    private String subjectName;
    private Integer pageNum;
    private Integer pageSize;
}
