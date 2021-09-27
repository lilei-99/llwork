package com.li.service;

import com.li.common.Result;
import com.li.common.dto.LoginUser;
import com.li.pojo.Employee;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface EmployeeService  {


    Result login(LoginUser loginUser , HttpServletRequest request);

    Employee getByUsername(String username);

    Employee getEmployeeByPhone(String phone);
}
