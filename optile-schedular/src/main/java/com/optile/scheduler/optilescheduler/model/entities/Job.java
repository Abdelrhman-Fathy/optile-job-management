package com.optile.scheduler.optilescheduler.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.optile.scheduler.optilescheduler.util.Const;

@SuppressWarnings("serial")
@Entity
@Table(name = "jobs")
public class Job implements Comparable<Job>, Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private long startTime;
	private String config;
	private int priority;
	private String status;
	private String validateURL;
	private String postURL;
	private boolean immediateJob;
	private boolean valid;

	public Job() {

	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isImmediateJob() {
		return immediateJob;
	}

	public void setImmediateJob(boolean immediateJob) {
		this.immediateJob = immediateJob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidateURL() {
		return validateURL;
	}

	public void setValidateURL(String validateURL) {
		this.validateURL = validateURL;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}

	@Override
	public int compareTo(Job o) {
		return Long.compare(this.getStartTime() + this.getPriority() * Const.PRIORITY_WEIGHT, 
				o.getStartTime() + o.getPriority() * Const.PRIORITY_WEIGHT);
	}
	
	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", startTime=" + startTime + ", config=" + config + ", priority="
				+ priority + ", status=" + status + ", validateURL=" + validateURL + ", postURL=" + postURL
				+ ", immediateJob=" + immediateJob + ", valid=" + valid + "]";
	}


}
