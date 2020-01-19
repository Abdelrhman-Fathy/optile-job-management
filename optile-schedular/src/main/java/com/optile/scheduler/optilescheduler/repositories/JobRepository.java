package com.optile.scheduler.optilescheduler.repositories;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.optile.scheduler.optilescheduler.model.entities.Job;
import com.optile.scheduler.optilescheduler.util.Const;


@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	@Query("SELECT j FROM Job j where j.startTime <:startTime and j.status = 'QUEUED'") 
	List<Job> getFutureJobs(@Param("startTime") long startTime);

}
