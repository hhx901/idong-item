package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ouyang
 * @Date: 2020/5/20 18:07
 * @Version 1.0
 */
@Data
@Excel("用户和岗位关联表")
@TableName("sys_user_post")
public class UserPost implements Serializable{
    private static final long serialVersionUID = 3343615380313336041L;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;
    /** 岗位ID */
    @TableField("post_id")
    private Long postId;
}
