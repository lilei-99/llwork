package com.li.mapper;


import com.li.pojo.Employee;
import com.li.security.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMapper {

    Employee findEmployeeByName(String name);

    MyUserDetails findUserDetailsByPhone(String phone);

    Employee findEmployeeByPhone(String phone);
}
