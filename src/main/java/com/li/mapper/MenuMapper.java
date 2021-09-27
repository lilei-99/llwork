package com.li.mapper;


import com.li.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {


    List<Menu> getMenuWithRole();

    List<Menu> getAllMenus();
}
