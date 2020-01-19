package com.optile.scheduler.optilescheduler.service;

import java.util.List;

import com.optile.scheduler.optilescheduler.adapter.IJobAdapter;
import com.optile.scheduler.optilescheduler.model.entities.Job;
import com.optile.scheduler.optilescheduler.repositories.JobRepository;
import com.optile.scheduler.optilescheduler.util.Const;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService implements IJobService {

	/**
	 * 
	 * @author Abdelrahman Fathy
	 * @since 19 Jan 2020
	 * job service implementation
	 *
	 */

	@Autowired
	IJobAdapter jobAdapter;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	IJobExecuterService jobExecuter;

	@Override
	public Job pushJob(Job job) throws Exception {
		if (job.getName() != null && job.getPostURL() != null && job.getValidateURL() != null) {
			if (!jobAdapter.callJob(job).isValid())
				throw new Exception("Invalid paramters");
			if (job.getStartTime() < System.currentTimeMillis() + Const.SCHEDULER_INTERVAL)
				job.setImmediateJob(true);
			job.setStatus(Const.STATUS_QUEUED);
			jobRepository.save(job);
			if (job.isImmediateJob()) {
				jobExecuter.executeImmediateJob(job);
			}
		} else
			throw new Exception("Invalid Job name or execution URL" + job);
		return job;
	}

	@Override
	public Job getJob(long id) {
		return jobRepository.getOne(id);
	}

	@Override
	public List<Job> getJobs() {
		return jobRepository.findAll();
	}
}
