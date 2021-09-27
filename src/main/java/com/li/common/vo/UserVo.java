package com.li.common.vo;


import com.li.pojo.Role;
import lombok.Data;

@Data
public class UserVo {

    private String id;
    private String userName;
    private Role role;

}
