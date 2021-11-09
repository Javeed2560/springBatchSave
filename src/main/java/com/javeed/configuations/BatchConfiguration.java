package com.javeed.configuations;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class BatchConfiguration { 
	
	@Autowired
	public JobBuilderFactory builderFactory;
	
	@Autowired	
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public DataSource dataSource;
	
	@Bean
	public Job readCSVFileJob() {
		return builderFactory
				.get("readCSVFilesJob")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
	
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Employee, Employee> chunk(10)
				.reader(reader())
				.writer(writer())
				.build();
	}


	@Bean
	public FlatFileItemReader<Employee> reader() {
		
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
		
		reader.setResource(new FileSystemResource("src/main/resources/inputData.csv"));
		
		reader.setLinesToSkip(1);
		
		reader.setLineMapper(new DefaultLineMapper<>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] {"id","firstName","lastName","companyName","address","city",
								"county","state"});
					}
				});
//				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
//					{
//						setTargetType(Employee.class);
//					}
//				});
				setFieldSetMapper(new EmployeeFieldSetMapper());
				afterPropertiesSet();
			}
		});
		return reader;
	}
	
//	 @Bean
//	public ConsoleItemWriter<Employee> writer(){
//		 
//		return new ConsoleItemWriter<Employee>();
//	}

	 @Bean
	 public JdbcBatchItemWriter<Employee> writer(){
		 
		 JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<>();
		 
		 itemWriter.setDataSource(dataSource);
		 itemWriter.setSql("INSERT INTO EMPLOYEE VALUES (:id, :firstName, :lastName, :companyName,	:address, :city, :county, :state)");
		 itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		 itemWriter.afterPropertiesSet();
		 return itemWriter;
	 }
	
}
