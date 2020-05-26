package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

/**
 * @Author: ouyang
 * @Date: 2020/5/24 13:13
 * @Version 1.0
 */
@Data
@Excel("字典类型表")
@TableName("sys_dict_type")
public class DictType extends BaseEntity{
    private static final long serialVersionUID = 5240883934116665461L;

    /** 字典主键 */
    @TableId(value = "dict_id",type = IdType.AUTO)
    private Long dictId;

    /** 字典名称 */
    @TableField("dict_name")
    private String dictName;

    /** 字典类型 */
    @TableField("dict_type")
    private String dictType;

    /** 状态（0正常 1停用） */
    @TableField("status")
    private String status;
}
