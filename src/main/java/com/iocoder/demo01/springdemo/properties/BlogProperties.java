package com.iocoder.demo01.springdemo.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlogProperties {
    @Value("${com.didispace.blog.name}")
    private String name;
    @Value("${com.didispace.blog.title}")
    private String title;
    @Value("${com.didispace.blog.desc}")
    private String desc;
// 省略getter和setter


    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}