package com.optile.scheduler.optilescheduler.rest;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optile.scheduler.optilescheduler.model.entities.Job;
import com.optile.scheduler.optilescheduler.service.IJobService;

import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 
 * @author Abdelrahman Fathy
 * @since 19 Jan 2020
 * Restful API that will accept the jobs requests from all channels 
 *
 */
@RestController
@RequestMapping(path = "/")
@ControllerAdvice
public class SchedulerController {
	
	private static final Logger logger = LogManager.getLogger(SchedulerController.class);
	public static final String customErrorLogMessage = " request:%s thrown error:%s";
	
	@Autowired
	IJobService jobService;
	
	
	/**
	 * return all jobs
	 * 
	 * @return List<Job>
	 */
	@ApiOperation(value = "return all jobs , empty list if none", response = List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "sucess response "),
	        @ApiResponse(code = 500, message = "somthing went wrong , please try again later"),
	})
	@RequestMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	private ResponseEntity<List<Job>> getJobs(){
		List<Job> jobs = jobService.getJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK) ;
	}
	
	/**
	 * return Job with the generated Id
	 * 
	 * @return Job
	 * @throws Exception 
	 */
	@ApiOperation(value = "push job to be executed at a given timestamp", response = List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "sucess response "),
	        @ApiResponse(code = 404, message = "requested job not found "),
	        @ApiResponse(code = 500, message = "somthing went wrong , please try again later"),
	})
	@RequestMapping(value = "/pushjob", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	private ResponseEntity<Job> pushJob(@RequestBody Job job) throws Exception{
		jobService.pushJob(job);
		return new ResponseEntity<Job>(job, HttpStatus.OK) ;
	}
	
	/**
	 * return job by Id
	 * 
	 * @return Job
	 */
	@ApiOperation(value = "return the job by jobId", response = Job.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "job found "),
	        @ApiResponse(code = 500, message = "somthing went wrong , please try again later"),
	        @ApiResponse(code = 404, message = "the job was not found"),
	})
	@RequestMapping(value = "/jobs/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	private ResponseEntity<Job> getJob(@PathVariable(name = "jobId") Long id){
		Job job = jobService.getJob(id);
		return null!=job ? new ResponseEntity<Job>(job, HttpStatus.OK) : new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handelException(HttpServletRequest req,
			Exception genericException) {
		logger.error(
				String.format(customErrorLogMessage, req.getRequestURI(),genericException.getMessage()));
		logger.error(genericException);
		return new ResponseEntity<>(genericException.getMessage(), HttpStatus.valueOf(500));
	}

}
