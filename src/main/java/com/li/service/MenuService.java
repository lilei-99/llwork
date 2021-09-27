package com.li.service;

import com.li.common.Result;
import com.li.pojo.Menu;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MenuService {

    //Result getMenus();

    List<Menu> getMenusWithRole();

    Result getAllMenus();
}
