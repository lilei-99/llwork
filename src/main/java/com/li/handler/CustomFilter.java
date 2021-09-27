package com.li.handler;

import com.li.pojo.Menu;
import com.li.pojo.Role;
import com.li.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析请求所需的角色
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private MenuService menuService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();

//		if (requestUrl.equals("/login")){
//			return SecurityConfig.createList("Role_anonymous");
//		}
		List<Menu> menus = menuService.getMenusWithRole();

		for (Menu menu : menus) {
			//判断请求url与菜单角色是否匹配
			if (antPathMatcher.match(menu.getUrl(),requestUrl)){
				String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
				return SecurityConfig.createList(str);
			}
		}
		//没匹配的url默认登录即可访问
		return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}