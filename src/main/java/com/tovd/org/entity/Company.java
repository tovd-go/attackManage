package com.tovd.org.entity;

import lombok.Data;

@Data
public class Company {
    private Integer Id;
    private String companyId;
    private String companyName;
    private String companyHost;
    private String companyDomain;
    private String companyTime;
    private String companyChildName;
    private String companyWechatName;
    private String companyWechatId;
    private String companyWeibo;
    private String companySupply;
    private String subjectName;
    private Integer pageNum;
    private Integer pageSize;

}
