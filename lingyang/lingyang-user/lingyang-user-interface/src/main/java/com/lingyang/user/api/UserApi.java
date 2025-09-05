package com.lingyang.user.api;

import com.lingyang.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author yangzicheng
 * @Date Created in 1:02 2023/3/19
 */
public interface UserApi {
    @GetMapping("query")
    public User queryUser(@RequestParam("username")String username, @RequestParam("password")String password);
}
