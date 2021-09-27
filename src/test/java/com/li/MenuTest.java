package com.li;

import com.li.mapper.EmployeeMapper;
import com.li.mapper.MenuMapper;
import com.li.pojo.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ：ll
 * @date ：Created in 2021/9/27 15:08
 * @description：菜单测试
 * @modified By：
 * @version: $
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MenuTest {

    @Autowired
    MenuMapper menuMapper;

    @Test
    public void test(){

        List<Menu> menus =menuMapper.getAllMenus();
        menus.stream().forEach(System.out::println);

    }


}
