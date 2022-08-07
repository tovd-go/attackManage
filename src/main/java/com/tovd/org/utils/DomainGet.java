package com.tovd.org.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainGet {
    public String domainGet(String value){
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|icu)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(value);
        while (matcher.find()) {
            value= matcher.group();
        }
        return value;
    }
}
