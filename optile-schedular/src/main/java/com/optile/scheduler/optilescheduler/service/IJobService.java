package com.optile.scheduler.optilescheduler.service;

import java.util.List;

import com.optile.scheduler.optilescheduler.model.entities.Job;
/**
 * 
 * @author Abdelrahman Fathy
 * @since 19 Jan 2020
 * Job service interface to ease depedency injection
 *
 */
public interface IJobService {
	public abstract Job getJob(long id);
	public abstract Job pushJob(Job job) throws Exception;
	public abstract List<Job> getJobs();
}
