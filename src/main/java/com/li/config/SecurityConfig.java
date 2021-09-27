package com.li.config;

import com.li.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * Security配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	@Autowired
	private CustomFilter customFilter;
	@Autowired
	private CustomUrlDecisionManager customUrlDecisionManager;


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/css/**",
				"/js/**",
				"/index.html",
				"favicon.ico",
				"/doc.html",
				"/webjars/**",
				"/swagger-resources/**",
				"/v2/api-docs/**",
				"/captcha",
				"/ws/**",
				"/code"
		);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

        http.cors();
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		//使用JWT，不需要csrf
		http.csrf()
				.disable()
				//基于token，不需要session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest()
				.authenticated()
				//动态权限配置
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setAccessDecisionManager(customUrlDecisionManager);
						object.setSecurityMetadataSource(customFilter);
						return object;
					}
				})
				.and()
				//禁用缓存
				.headers()
				.cacheControl();
		         //添加jwt 登录授权过滤器
		http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		http.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthorizationEntryPoint);

	}




	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
		return new JwtAuthencationTokenFilter();
	}

}