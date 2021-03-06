package com.dyenigma.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * backend/com.dyenigma.backend.entity
 *
 * @Description : 系统用户实体类，继承BaseDomain
 * @Author : dingdongliang
 * @Date : 2018/4/17 10:25
 */
@Getter
@Setter
public class SysUser extends BaseDomain {
    private String userId;

    private String userName;

    private String userNo;

    private Date joinTime;

    private String account;

    private String password;

    private String userEmail;

    private String userPhone;

    private String userAddr;

    private String gender = "M";

    private Date firstLogin;

    private Date prevLogin;

    private String prevIp;

    private Date lastLogin;

    private String loginCount;

    private String status = "E";

    private String userDesc;

    private Integer isOnline = 0;

    private Integer sort = 1;

    private Set<String> menuList;

    private Set<String> pmsnList;
}
