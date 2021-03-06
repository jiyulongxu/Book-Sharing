package com.dyenigma.backend.service.impl;

import com.dyenigma.backend.constant.SystemConstant;
import com.dyenigma.backend.dao.SysPermissionMapper;
import com.dyenigma.backend.dao.SysRolePmsnMapper;
import com.dyenigma.backend.entity.SysPermission;
import com.dyenigma.backend.entity.SysUser;
import com.dyenigma.backend.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * sharing/com.dyenigma.backend.service.impl
 *
 * @Description :
 * @Author : dingdongliang
 * @Date : 2018/4/18 15:20
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission> implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private SysRolePmsnMapper sysRolePmsnMapper;

    /**
     * 根据用户ID查询其所拥有的所有权限
     *
     * @param userId 用户ID
     * @return java.util.List<com.dyenigma.backend.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/4/18 15:44
     */
    @Override
    public List<SysPermission> getUserPmsn(String userId) {
        return sysPermissionMapper.getUserPmsn(userId);
    }

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return java.util.List<com.dyenigma.backend.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/4/23 14:50
     */
    @Override
    public List<SysPermission> getCurrentPmsn(SysUser sysUser) {
        List<SysPermission> userPermission;
        if (SystemConstant.SYSTEM_ADMINISTRATOR.equals(sysUser.getAccount())) {
            userPermission = sysPermissionMapper.selectAll();
        } else {
            userPermission = getUserPmsn(sysUser.getUserId());
        }
        //获取session，存储用户权限信息
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(SystemConstant.SESSION_USER_PERMISSION, userPermission);
        return userPermission;
    }

    /**
     * 删除权限，需要同时删除角色-权限对应表中的该权限记录
     *
     * @param pmsnId 权限ID
     * @return void
     * @author dingdongliang
     * @date 2018/4/25 16:24
     */
    @Override
    public void delete(String pmsnId) {
        sysRolePmsnMapper.deleteByPmsnId(pmsnId);
        sysPermissionMapper.deleteByPrimaryKey(pmsnId);
    }
}
