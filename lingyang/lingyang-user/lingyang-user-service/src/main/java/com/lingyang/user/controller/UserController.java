package com.lingyang.user.controller;

import com.lingyang.common.pojo.PageResult;
import com.lingyang.user.pojo.User;
import com.lingyang.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 23:51 2023/3/17
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //type: 1代表姓名 2代表手机号
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data, @PathVariable("type")Integer type){
        Boolean bool = this.userService.checkUser(data,type);
        if (bool == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);
    }

    //发送验证码
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //注册
    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid User user, @RequestParam("code")String code){
        Boolean register = this.userService.register(user, code);
        if (register == false){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(register);
    }

    /***
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username,@RequestParam("password")String password){
        User user = this.userService.queryUser(username,password);
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 根据查询条件分页查询用户信息
     * @param key
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<User>> queryBrandsByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
    ){
        PageResult<User> result = this.userService.queryUsersByPage(key,page,rows);
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


    //新增用户
    @PostMapping("user")
    public ResponseEntity<Void> saveUser(@RequestBody User user){
        this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //修改用户
    @PutMapping("user")
    public ResponseEntity<Void> updateUser(@RequestBody User user){
        this.userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
//        System.out.println("------------------" + user);
//        return null;
    }
}
