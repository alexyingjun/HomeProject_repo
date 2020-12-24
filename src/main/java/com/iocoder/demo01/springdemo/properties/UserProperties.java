package com.iocoder.demo01.springdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {
    private long id;
    private int age;
    private String desc;
    private String uuid;
}
