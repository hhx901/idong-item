package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

/**
 * @Author: ouyang
 * @Date: 2020/5/24 13:08
 * @Version 1.0
 */
@Data
@Excel("字典数据表")
@TableName("sys_dict_data")
public class DictData extends BaseEntity{
    private static final long serialVersionUID = 5355925020192577747L;

    /** 字典编码 */
    @TableId(value = "dict_code",type = IdType.AUTO)
    private Long dictCode;

    /** 字典排序 */
    @TableField("dict_sort")
    private Long dictSort;

    /** 字典标签 */
    @TableField("dict_label")
    private String dictLabel;

    /** 字典键值 */
    @TableField("dict_value")
    private String dictValue;

    /** 字典类型 */
    @TableField("dict_type")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @TableField("css_class")
    private String cssClass;

    /** 表格字典样式 */
    @TableField("list_class")
    private String listClass;

    /** 是否默认（Y是 N否） */
    @TableField("is_default")
    private String isDefault;

    /** 状态（0正常 1停用） */
    @TableField("status")
    private String status;

    public boolean getDefault() {
        return UserConstants.YES.equals(this.isDefault) ? true : false;
    }
}
