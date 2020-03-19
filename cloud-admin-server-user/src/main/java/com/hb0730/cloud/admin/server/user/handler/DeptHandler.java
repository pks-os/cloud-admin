package com.hb0730.cloud.admin.server.user.handler;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.hb0730.cloud.admin.common.web.exception.BusinessException;
import com.hb0730.cloud.admin.common.web.response.ResultJson;
import com.hb0730.cloud.admin.common.web.utils.CodeStatusEnum;
import com.hb0730.cloud.admin.common.web.utils.JsonConvertBeanUtils;
import com.hb0730.cloud.admin.commons.dept.model.vo.SystemDeptVO;
import com.hb0730.cloud.admin.commons.user.dept.model.vo.UserDeptParamsVO;
import com.hb0730.cloud.admin.server.user.feign.IRemoteDept;
import com.hb0730.cloud.admin.server.user.feign.IRemoteUserDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DeptHandler {

    @Autowired
    private IRemoteDept remoteDept;
    @Autowired
    private IRemoteUserDept remoteUserDept;

    /**
     * 根据id获取组织
     *
     * @param id id
     * @return 组织信息
     */
    public SystemDeptVO getDeptById(@NonNull Long id) {
        List<Long> ids = Lists.newArrayList(id);
        ResultJson result = remoteDept.getDeptByIds(ids);
        return JsonConvertBeanUtils.convert(result, SystemDeptVO.class);

    }

    /**
     * <p>
     * 根据用户id获取组织id
     * </p>
     *
     * @param id 用户id
     * @return 组织id
     */
    public Long getDeptIdByUserId(@NonNull Long id) {
        ResultJson result = remoteUserDept.getDeptByUserId(id);
        List<Long> deptIds = JsonConvertBeanUtils.convertList(result, Long.class);
        return deptIds.get(0);
    }

    /**
     * <p>
     * 根据用户id获取组织信息
     * </p>
     *
     * @param userId 用户id
     * @return 组织信息
     */
    public SystemDeptVO getDeptByUserId(@NonNull Long userId) {
        Long deptId = getDeptIdByUserId(userId);
        return getDeptById(deptId);
    }

    /**
     * <p>
     * 分页获取指定组织用户
     * </p>
     *
     * @param page     页数
     * @param pageSize 属性
     * @param params   组织过滤条件
     * @return 分页后的组织
     */
    public PageInfo getPage(@NonNull Integer page, @NonNull Integer pageSize, UserDeptParamsVO params) {
        ResultJson result = remoteUserDept.getPage(page, pageSize, params);
        return JsonConvertBeanUtils.convert(result, PageInfo.class);
    }

    /**
     * 根据用户删除绑定
     *
     * @param id 用户id
     */
    public void removeByUserId(@NonNull Long id) {
        ResultJson resultJson = remoteUserDept.removeByUserId(id);
        if (!CodeStatusEnum.SUCCESS.getCode().equals(resultJson.getStatusCode())) {
            throw new BusinessException(resultJson.getData().toString());
        }
    }


    /**
     * 组织用户绑定
     *
     * @param userId 用户id
     * @param deptId 组织id
     */
    public void bindingDeptByUserId(@NonNull Long userId, @NonNull List<Long> deptId) {
        ResultJson result = remoteUserDept.bindingDeptByUserId(userId, deptId);
        if (!CodeStatusEnum.SUCCESS.getCode().equals(result.getStatusCode())) {
            throw new BusinessException(result.getData().toString());
        }
    }
}
