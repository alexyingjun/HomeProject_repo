package com.iocoder.demo01.democlass.serialiseandlombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=true)
public class AdminUser extends User {
    @Getter private String role;


}
