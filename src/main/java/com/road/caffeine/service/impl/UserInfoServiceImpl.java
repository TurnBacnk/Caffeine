package com.road.caffeine.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.road.caffeine.entity.UserInfo;
import com.road.caffeine.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wang zhe
 * 2023/5/17 10:27
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    /**
     * 模拟数据库
     */
    private final Map<Integer, UserInfo> USER_INFO_MAP = new ConcurrentHashMap<>();

    @Resource
    private Cache<String, Object> caffeineCache;


    @Override
    public void addUserInfo(UserInfo userInfo) {
        log.info("create user");
        USER_INFO_MAP.put(userInfo.getId(), userInfo);
        caffeineCache.put(String.valueOf(userInfo.getId()), userInfo);
    }

    @Override
    public UserInfo getById(Integer id) {
        UserInfo userInfo = (UserInfo) caffeineCache.asMap().get(String.valueOf(id));
        if (userInfo != null) {
            return userInfo;
        }
        userInfo = USER_INFO_MAP.get(id);
        if (userInfo != null) {
            caffeineCache.put(String.valueOf(userInfo.getId()), userInfo);
        }
        return userInfo;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        log.info("update");
        if (!USER_INFO_MAP.containsKey(userInfo.getId())) {
            return null;
        }
        UserInfo oldUserInfo = USER_INFO_MAP.get(userInfo.getId());
        oldUserInfo.setName(userInfo.getName());
        oldUserInfo.setAge(userInfo.getAge());
        oldUserInfo.setSex(userInfo.getSex());

        USER_INFO_MAP.put(oldUserInfo.getId(), oldUserInfo);

        caffeineCache.put(String.valueOf(oldUserInfo.getId()), oldUserInfo);

        return oldUserInfo;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("delete");
        USER_INFO_MAP.remove(id);
        caffeineCache.asMap().remove(String.valueOf(id));
    }
}
