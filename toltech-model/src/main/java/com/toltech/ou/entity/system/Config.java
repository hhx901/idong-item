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
 * @Date: 2020/5/25 10:47
 * @Version 1.0
 */
@Data
@Excel("参数配置表")
@TableName("sys_config")
public class Config extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @TableId(value = "config_id",type = IdType.AUTO)
    private Long configId;

    /** 参数名称 */
    @TableField("config_name")
    private String configName;

    /** 参数键名 */
    @TableField("config_key")
    private String configKey;

    /** 参数键值 */
    @TableField("config_value")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @TableField("config_type")
    private String configType;
}
