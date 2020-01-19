<h1 align="center"> Optile Job Executer </h1> <br>

<p align="center">
  Implementation of service that will accept jobs to be scheduled or executed immediately based on the input and start time.
</p>

## Table of Contents

- [Introduction](#introduction)
- [Business Requirement](SJava_Backend_Case_Study.pdf)
- [Solution Presentation](scheduler_task.pptx)
- [Solution Architecture](#solution-architecture)
- [Application Architecture](#application-architecture)
- [System Implementation](#system-implementation)
- [Application Packages](#application-packages)
- [Solution microservices](#solution-microservices)
- [API Documentation](#API)
- [Areas to improve](#improve)
- [Assumptions](#Assumptions)
- [Test Cases](#test)



## Introduction

  * The application adopts the [microservices Architecture patterns](https://microservices.io/patterns/microservices.html).
  * Application contains 2 spring boot jars, one for the scheduler engine and the other one as sample job for testing purpose.
  
## Solution-Architecture

  * the business usecase is best to be implemetned by microservice architure.
  * There is two subdomains identified as per the usecase (Customers and Vehicles) and it requires a service aggregator that retirve information from both domains and provide it to frontend/customer-facing application(monitoring dashboard).

![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/solution-architecture.JPG)


## application-architecture
  * Scheduler to accept job requests. that will be scheduled or executed immediately based on the client input.
![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/spplication-architecture.JPG)

## system-implementation
  * Scheduler is a spring boot microservice that will receive job request.
  * Each job will have a priority, start time, immediate Boolean flag.
  * Each job is a microservice [spring boot], has post URL and validate URL and configuration parameters.
  * Once scheduler get the job, validation service will be invoked to insure proper configuration parameters before storing the job in the priority queue.
  * If scheduled job is valid, it will be stored into DB and picked before it’s execution time.
  * Scheduler will run every 1 minute a crone job that read the scheduled jobs and execute it.
  * Priority for the queue is considering the start time and priority using the following formula
  * start timestamp  + Priority * 1 billion
  * Immediate jobs considered with priority 1 and start time as current timestamp, hence same formula is applied. 
  * set for jobs based on start time
  * Technology stack : Spring boot , Spring data, DB :HSQLDB, Swagger UI, Java 8, maven

## application-packages
  * scheduler 
![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/scheduler-packages.png)

  * jobs
![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/jobs-packages.png)

## Solution-microservices
### Scheduler 
API to submit any new scheduled job to be executed. It support retrieve of jobs data by Id or get full job list from database.
* chassis framework : Spring boot , Spring data 
* DB :HSQLDB

### Jobs
That has the implementation of jobs to be executed. each job should have validate API and execute API. Implementation of basic email sender provided as part of this assignment.
  * chassis framework : Spring boot , email sender

## API
  * Below is swagger UI for scheduler API’s
![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/api1.png)

![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/api2.png)

## Improve
  * Dockers to be configured to the microservices architecture
  * Elk stake to be used to monitoring the system
  * Deploying to AWS cloud service to ensure scaling and availability with low cost.
  * Serverless [Lambda functions] is a job option supporting scaling and AWS offers first 1 million transactions free of cost.
  * Kubernetes to be used for continues integration
  * More unite test cases to be written to cover the code.
  * Sonarcloud to be used to review code quality
  * Netfelx zuul API gateway for API versioning, security and service metrics

## Assumptions
  * Max thread pool size is 10.
  * Job execution will follow microservices architecture, hence each job will be a webservice call.
  * If two jobs has the same start time, priority will be considered in the execution. Otherwise job with lowest start time will be executed after job with higher start time even if its priority is higher [low number].
  * Lowest priority is 10 and highest priority is 1.
## Test
  * Two test cases for the major API’s are executed and successful
  * Get all jobs
  * Push Job
![alt text](https://raw.githubusercontent.com/Abdelrhman-Fathy/optile-job-management/master/test.png)
