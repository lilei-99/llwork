package com.li.controller;


import com.li.common.Result;
import com.li.common.dto.LoginUser;
import com.li.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lilei
 * @since 2021-07-10
 */
@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser, HttpServletRequest request){
        System.out.println(loginUser);
        return employeeService.login(loginUser,request);
    }


}
