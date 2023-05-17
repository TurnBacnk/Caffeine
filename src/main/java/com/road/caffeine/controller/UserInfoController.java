package com.road.caffeine.controller;

import com.road.caffeine.entity.UserInfo;
import com.road.caffeine.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wang zhe
 * 2023/5/17 10:41
 */
@RestController
@RequestMapping("/api/user/")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("userInfo/query/{id}")
    public Object getUserInfo(@PathVariable Integer id) {
        UserInfo byId = userInfoService.getById(id);
        if (byId == null) {
            return "无用户";
        }
        return byId;
    }

    @PostMapping("userInfo/add")
    public Object createUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);
        return "SUCCESS";
    }

    @PutMapping("userInfo/update")
    public Object updateUserInfo(@RequestBody UserInfo userInfo) {
        UserInfo newUserInfo = userInfoService.updateUserInfo(userInfo);
        if (newUserInfo == null) {
            return "不存在该用户";
        }
        return newUserInfo;
    }

    @DeleteMapping("userInfo/{id}")
    public Object deleteUserInfo(@PathVariable Integer id) {
        userInfoService.deleteById(id);
        return "SUCCESS";
    }
}
