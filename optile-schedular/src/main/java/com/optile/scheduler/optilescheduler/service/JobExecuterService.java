package com.optile.scheduler.optilescheduler.service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.optile.scheduler.optilescheduler.adapter.JobAdapter;
import com.optile.scheduler.optilescheduler.model.entities.Job;
import com.optile.scheduler.optilescheduler.repositories.JobRepository;
import com.optile.scheduler.optilescheduler.util.Const;
/**
 * 
 * @author Abdelrahman Fathy
 * @since 19 Jan 2020
 * Executer service implementation 
 *
 */
@Service
public class JobExecuterService implements IJobExecuterService {

	@Autowired
	private JobAdapter jobAdapter;
	private static final Logger logger = LogManager.getLogger(JobExecuterService.class);

	@Autowired
	private JobRepository jobRepository;
	private PriorityBlockingQueue<Job> jobQueue = new PriorityBlockingQueue<Job>();
	private boolean running;
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	

	@Override
	public void loadScheduledJobs() {
		List<Job>jobs = jobRepository.getFutureJobs(System.currentTimeMillis() + Const.SCHEDULER_INTERVAL);
		jobQueue.addAll(jobs);
		logger.info("loaded jobs size" + jobs.size());
	}

	private Job executeJob(Job job) {
		logger.info("executeJob thread ["  + Thread.currentThread().getName() + "] for job [" + job +"]");
		jobAdapter.callJob(job);
		jobRepository.save(job);
		return job;
	}

	public void executeJobsQueue() {
		running = Boolean.TRUE;
		while (!jobQueue.isEmpty()) {
			Job job = jobQueue.poll();
			executor.submit(new Runnable() {
				@Override
				public void run() {
					executeJob(job);
				}
			});
		}
		running = Boolean.FALSE;
	}

	@Scheduled(fixedRate = Const.SCHEDULER_INTERVAL)
	@Async
	public void execute() throws Exception {
		logger.info("scheduler started" + Thread.currentThread().getName());
		loadScheduledJobs();
		executeJobsQueue();
		logger.info("scheduler finished"+ Thread.currentThread().getName());
	}
	
	public void addToJobQueue(Job job) {
		jobQueue.add(job);
	}

	@Override
	public Job executeImmediateJob(Job job) throws Exception {
		addToJobQueue(job);
		if(!isRunning())
			executeJobsQueue();
		return job;
	}
	public boolean isRunning() {
		return running;
	}

	
}
