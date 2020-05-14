package com.imooc.miaosha.controller;


import com.imooc.miaosha.domain.redis.RedisService;
import com.imooc.miaosha.domain.redis.UserKey;
import com.imooc.miaosha.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.xml.transform.Result;

import static javafx.scene.input.KeyCode.T;
import static org.apache.el.util.MessageFactory.get;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String>home(){
        return Result.success("Hello,World");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String>error(){
        return Result.error(CodeMsg.SESSION_ERROR);
    }

    @RequestMapping("/hello/themaleaf")
    public String themaleaf(Model model){
        model.addAttribute("name","Joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<String>dbGet(){
        User user=userService.getById(1);
        return Result.success(user);
    }
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean>dbTx(){
        userService.tx();
        return Result.success(true);
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User>redisGet(){
        User user=redisService.get(UserKey.getById,"key2","hello,imooc");
        return Result.success(user);
    }
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean>redisSet() {
        Long ret= redisService.set(Prefix,"key2","hello,imooc");
        String str=redisService.get(Prefix,"key2",String.class);
        return Result.success(ret);
    }
}
