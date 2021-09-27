package com.li.service.imp;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.li.common.Result;
import com.li.mapper.MenuMapper;
import com.li.pojo.Employee;
import com.li.pojo.Menu;
import com.li.security.MyUserDetails;
import com.li.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    MenuMapper menuMapper;
    @Override
    public Result getAllMenus() {

        Collection<? extends GrantedAuthority> employeeAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        List<Menu> menus = menuMapper.getAllMenus();


        return Result.succ(menus);
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenuWithRole();
    }
}
