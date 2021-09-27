package com.li.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：ll
 * @date ：Created in 2021/9/22 16:02
 * @description：測試
 * @modified By：
 * @version: $
 */

@RestController
public class TestController {


    @RequestMapping("/hello")
    public String hello(){
        System.out.println("1");
        return "demo";
    }






    @RequestMapping("/system/init")
    public String hello1(){
        System.out.println("1");
        return "demo";
    }


}

