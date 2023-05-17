package com.road.caffeine.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wang zhe
 * 2023/5/17 10:22
 */
@Getter
@Setter
@ToString
public class UserInfo {
    private Integer id;
    private String name;
    private String sex;
    private String age;
}
