package com.toltech.ou.entity.Base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Maps;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: ouyang
 * @Date: 2020/5/20 17:22
 * @Version 1.0
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -8488280957816405940L;

    /** 搜索值 */
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    @TableField("create_by")
    private String createBy;

    /** 创建时间 */
    @TableField("create_time")
    private Date createTime;

    /** 更新者 */
    @TableField("update_by")
    private String updateBy;

    /** 更新时间 */
    @TableField("update_time")
    private Date updateTime;

    /** 备注 */
    @TableField("remark")
    private String remark;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = Maps.newHashMap();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
