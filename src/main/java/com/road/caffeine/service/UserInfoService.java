package com.road.caffeine.service;

import com.road.caffeine.entity.UserInfo;

/**
 * @author wang zhe
 * 2023/5/17 10:23
 */
public interface UserInfoService {

    /**
     * 增加用户信息
     * @param userInfo 用户信息
     */
    void addUserInfo(UserInfo userInfo);

    /**
     * 根据Id获取用户
     * @param id id
     * @return 用户信息
     */
    UserInfo getById(Integer id);

    /**
     * 修改用户信息
     *
     * @param userInfo 用户细腻
     * @return 用户信息
     */
    UserInfo updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户信息
     *
     * @param id 用户Id
     */
    void deleteById(Integer id);

}
