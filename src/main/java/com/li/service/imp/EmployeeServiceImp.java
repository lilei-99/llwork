package com.li.service.imp;

import com.li.common.Result;
import com.li.common.dto.LoginUser;
import com.li.mapper.EmployeeMapper;
import com.li.pojo.Employee;
import com.li.security.MyUserDetailsService;
import com.li.service.EmployeeService;
import com.li.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public Result login(LoginUser loginUser, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println(request.getSession().getId());
        System.out.println(captcha);
        if (StringUtils.isEmpty(loginUser.getCode())||!loginUser.getCode().equalsIgnoreCase(captcha)){
            return Result.fail("验证码输入错误，请重新输入！");
        }
        //登录
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginUser.getPhone());
        if (null==userDetails||!bCryptPasswordEncoder.matches(loginUser.getPassword(),userDetails.getPassword())){
            return Result.fail("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()){
            return Result.fail("账号被禁用，请联系管理员！");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                ,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        Employee employee = getEmployeeByPhone(loginUser.getPhone());
        request.setAttribute(loginUser.getPhone(),loginUser.getPhone());
        redisTemplate.opsForValue().set(loginUser.getPhone(),employee.getRoles(),30, TimeUnit.MINUTES);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token","Bearer "+token);
        tokenMap.put("tokenHead",tokenHead);
        return Result.succ(tokenMap);

    }

    @Override
    public Employee getByUsername(String username) {
        Employee employee = employeeMapper.findEmployeeByName(username);
        return  employee;
    }


    @Override
    public Employee getEmployeeByPhone(String phone) {
        return employeeMapper.findEmployeeByPhone(phone);
    }


}
