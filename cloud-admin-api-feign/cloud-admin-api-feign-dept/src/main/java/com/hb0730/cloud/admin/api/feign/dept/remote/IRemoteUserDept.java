package com.hb0730.cloud.admin.api.feign.dept.remote;

import com.hb0730.cloud.admin.api.feign.dept.mode.vo.UserDeptParamsVO;
import com.hb0730.cloud.admin.api.feign.dept.remote.fallback.RemoteUserDeptFallbackFactory;
import com.hb0730.cloud.admin.common.web.response.ResultJson;
import com.hb0730.cloud.admin.commons.feign.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.hb0730.cloud.admin.common.util.RequestMappingConstants.USER_DEPT_SERVER_REQUEST;
import static com.hb0730.cloud.admin.common.util.ServerNameConstants.USER_DEPT_SERVER;

/**
 * <p>
 * 远程调用用户组织绑定
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@FeignClient(value = USER_DEPT_SERVER, path = USER_DEPT_SERVER_REQUEST, configuration = FeignConfiguration.class, fallbackFactory = RemoteUserDeptFallbackFactory.class)
public interface IRemoteUserDept {
    /**
     * <p>
     * 根据用户获组织id
     * </p>
     *
     * @param userId 用户id
     * @return 组织id
     */
    @GetMapping("/getDeptId/{userId}")
    ResultJson getDeptByUserId(@PathVariable("userId") Long userId);

    /**
     * <p>
     * 根据用户信息绑定组织
     * </p>
     *
     * @param userId  用户id
     * @param deptIds 组织id
     * @return 是否成功
     */
    @PostMapping("/bindingDeptId/{userId}")
    ResultJson bindingDeptByUserId(@PathVariable("userId") Long userId, @RequestBody List<Long> deptIds);

    /**
     * <p>
     * 获取用户组织
     * </p>
     *
     * @param page     页数
     * @param pageSize 数量
     * @return 用户组织
     */
    @GetMapping("/getPage/{page}/{pageSize}")
    ResultJson getPage(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @RequestBody UserDeptParamsVO params);

    /**
     * <p>
     * 根据用户id删除
     * </p>
     *
     * @param userId 用户id
     * @return 是否成功
     */
    @GetMapping("/delete/user/{id}")
    ResultJson removeByUserId(@PathVariable("id") Long userId);
}
