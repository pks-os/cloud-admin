package com.hb0730.cloud.admin.server.permission.menu.system.model.vo;

import com.hb0730.cloud.admin.common.web.vo.BusinessDomainVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *     当前项目vo
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemPermissionMenuVO extends BusinessDomainVO {

    /**
     * 是否删除
     */
    private Integer isDelete = 0;

    /**
     * 是否启用
     */
    private Integer isEnabled = 1;

    /**
     * id
     */
    private Long id;

    /**
     * 权限id
     */
    private Long permissionId;
    private String name;
    /**
     * 菜单id
     */
    private Long menuId;
    private String menuName;
    /**
     * 备注
     */
    private String description;
}
