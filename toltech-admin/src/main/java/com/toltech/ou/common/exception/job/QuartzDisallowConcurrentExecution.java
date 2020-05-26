package com.toltech.ou.common.exception.job;

import com.toltech.ou.common.utils.AbstractQuartzJob;
import com.toltech.ou.common.utils.JobInvokeUtil;
import com.toltech.ou.entity.monitor.Job;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 * @Author: ouyang
 * @Date: 2020/5/26 13:26
 * @Version 1.0
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
