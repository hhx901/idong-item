package com.toltech.ou.entity.monitor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.common.constant.ScheduleConstants;
import com.toltech.ou.common.utils.CronUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 17:26
 * @Version 1.0
 */
@Data
@TableName("sys_job")
@Excel("定时任务调度信息")
public class Job extends BaseEntity{
    private static final long serialVersionUID = -873723762773246296L;

    /** 任务ID */
    @TableId(value = "job_id" ,type = IdType.AUTO)
    private Long jobId;

    /** 任务名称 */
    @TableField("job_name")
    @NotBlank(message = "任务名称不能为空")
    @Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
    private String jobName;

    /** 任务组名 */
    @TableField("job_group")
    private String jobGroup;

    /** 调用目标字符串 */
    @TableField("invoke_target")
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(min = 0, max = 1000, message = "调用目标字符串长度不能超过500个字符")
    private String invokeTarget;

    /** cron执行表达式 */
    @TableField("cron_expression")
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
    private String cronExpression;

    /** cron计划策略 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行*/
    @TableField("misfire_policy")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** 是否并发执行（0允许 1禁止） */
    @TableField("concurrent")
    private String concurrent;

    /** 任务状态（0正常 1暂停） */
    @TableField("status")
    private String status;

    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

}
