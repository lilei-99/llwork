package com.li.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

    private String phone;

    private String password;

    private String code;
}
