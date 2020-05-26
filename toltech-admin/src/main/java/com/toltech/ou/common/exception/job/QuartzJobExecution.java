package com.toltech.ou.common.exception.job;

import com.toltech.ou.common.utils.AbstractQuartzJob;
import com.toltech.ou.common.utils.JobInvokeUtil;
import com.toltech.ou.entity.monitor.Job;
import org.quartz.JobExecutionContext;

/**
 * @Author: ouyang
 * @Date: 2020/5/26 13:20
 * @Version 1.0
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception
    {
        JobInvokeUtil.invokeMethod(job);
    }
}
