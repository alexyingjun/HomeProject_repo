package com.iocoder.demo01.springdemo.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlogProperties {
    @Getter
    @Value("${com.didispace.blog.name}")
    private String name;
    @Getter
    @Value("${com.didispace.blog.title}")
    private String title;
    @Getter
    @Value("${com.didispace.blog.desc}")
    private String desc;
// 省略getter和setter
}