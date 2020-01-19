package com.optile.scheduler.optilescheduler.adapter;

import org.springframework.stereotype.Component;

import com.optile.scheduler.optilescheduler.model.entities.Job;
@Component
public interface IJobAdapter {

	Job callJob(Job job);

}
