package com.toltech.ou.common.assist;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 14:26
 * @Version 1.0
 */
@Data
public class Ztree implements Serializable{
    private static final long serialVersionUID = -9166953007748938627L;

    /** 节点ID */
    private Long id;

    /** 节点父ID */
    private Long pId;

    /** 节点名称 */
    private String name;

    /** 节点标题 */
    private String title;

    /** 是否勾选 */
    private boolean checked = false;

    /** 是否展开 */
    private boolean open = false;

    /** 是否能勾选 */
    private boolean nocheck = false;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }
}
