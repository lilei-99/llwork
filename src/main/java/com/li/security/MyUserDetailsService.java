package com.li.security;

import com.li.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：ll
 * @date ：Created in 2021/9/24 17:34
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("loadUser");
        return employeeMapper.findUserDetailsByPhone(s);
    }
}
