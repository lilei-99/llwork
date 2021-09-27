package com.li.controller;


import com.li.common.Result;
import com.li.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-07-10
 */
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("getAllMenus")
    public Result getAllMenu(){
        return menuService.getAllMenus();
    }
}
