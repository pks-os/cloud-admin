package com.hb0730.cloud.admin.server.role.permission.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hb0730.cloud.admin.commons.model.security.UserDetail;
import com.hb0730.cloud.admin.server.role.permission.system.model.entity.SystemRolePermissionEntity;
import com.hb0730.cloud.admin.server.role.permission.system.mapper.SystemRolePermissionMapper;
import com.hb0730.cloud.admin.server.role.permission.system.service.ISystemRolePermissionService;
import com.hb0730.cloud.admin.commons.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限  服务实现类
 * </p>
 *
 * @author bing_huang
 * @since 2020-02-19
 */
@Service
public class SystemRolePermissionServiceImpl extends BaseServiceImpl<SystemRolePermissionMapper, SystemRolePermissionEntity> implements ISystemRolePermissionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Long id, List<Long> permissionIds, UserDetail userDetail) {
        //移除
        SystemRolePermissionEntity updateEntity = new SystemRolePermissionEntity();
        updateEntity.setRoleId(id);
        UpdateWrapper<SystemRolePermissionEntity> updateWrapper = new UpdateWrapper<>(updateEntity);
        remove(updateWrapper);
        //新增
        List<SystemRolePermissionEntity> entitys = Lists.newArrayList();
        if (CollectionUtils.isEmpty(permissionIds)) {
            return false;
        }
        permissionIds.forEach(permissionId -> {
            SystemRolePermissionEntity entity = new SystemRolePermissionEntity();
            entity.setPermissionId(permissionId);
            entity.setIsEnabled(1);
            entity.setRoleId(id);
            entity.setCreateTime(new Date());
            entity.setCreateUserId(userDetail.getId());
            entitys.add(entity);
        });
        saveBatch(entitys);
        return false;
    }

    @Override
    public List<Long> getPermissionIdByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<Long> resultPermissionIds = Lists.newArrayList();
        roleIds.parallelStream().forEach(roleId -> {
            SystemRolePermissionEntity entity = new SystemRolePermissionEntity();
            entity.setRoleId(roleId);
            entity.setIsEnabled(1);
            QueryWrapper<SystemRolePermissionEntity> queryWrapper = new QueryWrapper<>(entity);
            List<SystemRolePermissionEntity> result = list(queryWrapper);
            if (!CollectionUtils.isEmpty(result)) {
                Set<Long> permissionIds = result.parallelStream().map(SystemRolePermissionEntity::getPermissionId).collect(Collectors.toSet());
                resultPermissionIds.addAll(permissionIds);
            }
        });
        return resultPermissionIds;
    }
}
