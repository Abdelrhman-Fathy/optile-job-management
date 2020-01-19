package com.optile.scheduler.optilescheduler.service;

import org.springframework.stereotype.Service;

import com.optile.scheduler.optilescheduler.model.entities.Job;

/**
 * 
 * @author Abdelrahman Fathy
 * @since 19 Jan 2020
 * Executer service interface to ease depedency injection
 *
 */

@Service
public interface IJobExecuterService {

	public void loadScheduledJobs();

	public Job executeImmediateJob(Job job) throws Exception;
	public void execute() throws Exception;

}
