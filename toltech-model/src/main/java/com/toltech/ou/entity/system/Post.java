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
 * @Date: 2020/5/20 18:04
 * @Version 1.0
 */
@Data
@TableName("sys_post")
@Excel("岗位表")
public class Post  extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 岗位序号 */
    @TableId(value = "post_id",type = IdType.AUTO)
    private Long postId;

    /** 岗位编码 */
    @TableField("post_code")
    private String postCode;

    /** 岗位名称 */
    @TableField("post_name")
    private String postName;

    /** 岗位排序 */
    @TableField("post_sort")
    private String postSort;

    /** 状态（0正常 1停用） */
    @TableField("status")
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;
}
