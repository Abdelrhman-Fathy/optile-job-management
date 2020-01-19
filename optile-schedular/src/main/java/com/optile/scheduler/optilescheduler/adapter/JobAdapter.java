package com.optile.scheduler.optilescheduler.adapter;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optile.scheduler.optilescheduler.model.entities.Job;
import com.optile.scheduler.optilescheduler.util.Const;

@Component
public class JobAdapter implements IJobAdapter{
	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LogManager.getLogger(JobAdapter.class);

	@Override
	public Job callJob(Job job) {
		job.setStatus(Const.STATUS_RUNNING);
		ResponseEntity<JsonNode> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<JsonNode> entity = new HttpEntity<JsonNode>(new ObjectMapper().readTree(job.getConfig()), headers);
			response = restTemplate.exchange(job.isValid() ? job.getPostURL() : job.getValidateURL(),
					HttpMethod.POST, entity,JsonNode.class);
			logger.info(response.getBody());
		} catch (Exception e) {
			logger.error(e);
			job.setStatus(Const.STATUS_FAILED);
		} finally {
			if(response != null && HttpStatus.OK.equals(response.getStatusCode())) {
				if(job.isValid()) job.setStatus(Const.STATUS_SUCCESS);
				else job.setValid(Boolean.TRUE);
			} else job.setStatus(Const.STATUS_FAILED);
		}
		return job;
	}

}
