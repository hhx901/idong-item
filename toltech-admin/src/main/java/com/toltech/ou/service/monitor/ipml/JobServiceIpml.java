package com.toltech.ou.service.monitor.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.constant.ScheduleConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.exception.job.TaskException;
import com.toltech.ou.common.utils.CronUtils;
import com.toltech.ou.common.utils.ScheduleUtils;
import com.toltech.ou.entity.monitor.Job;
import com.toltech.ou.mapper.monitor.JobMapper;
import com.toltech.ou.service.monitor.JobService;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 17:36
 * @Version 1.0
 */
@Service
public class JobServiceIpml extends ServiceImpl<JobMapper,Job> implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        List<Job> jobList = jobMapper.selectJobAll();
        for (Job job : jobList) {
            updateSchedulerJob(job, job.getJobGroup());
        }
    }

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(Job job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    public List<Job> selectJobList(Job job) {
        return jobMapper.selectJobList(job);
    }

    @Override
    public Job selectJobById(Long jobId) {
        return jobMapper.selectJobById(jobId);
    }

    @Override
    @Transactional
    public int pauseJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    @Transactional
    public int resumeJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    @Transactional
    public int deleteJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteJobById(jobId);
        if (rows > 0)
        {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    @Transactional
    public void deleteJobByIds(String ids) throws SchedulerException {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds)
        {
            Job job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    @Override
    public int changeStatus(Job job) throws SchedulerException {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    @Override
    @Transactional
    public void run(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        Job properties = selectJobById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    @Override
    @Transactional
    public int insertJob(Job job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    @Override
    public int updateJob(Job job) throws SchedulerException, TaskException {

        Job properties = selectJobById(job.getJobId());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }


    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
