package com.javeed;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchExampleApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchExampleApplication.class, args);
	}

	public void run(String... args) throws Exception{
		JobParameters params =new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.addLong("JobId",System.currentTimeMillis())
				.addLong("time",System.currentTimeMillis()).toJobParameters();
		try {
			JobExecution execution = jobLauncher.run(job, params);
			System.out.println("STATUS :: "+execution.getStatus());
		} catch (JobExecutionAlreadyRunningException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JobRestartException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
