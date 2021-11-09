package com.javeed.configuations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Entity
public class Employee {

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Getter
	@Setter
	@Column
	private String firstName;
	
	@Getter
	@Setter
	@Column
	private String lastName;
	 
	@Getter
	@Setter
	@Column(name = "company_name")
	private String companyName;
	
	@Getter
	@Setter
	@Column
	private String address;
	
	@Getter
	@Setter
	@Column
	private String city;
	
	@Getter
	@Setter
	@Column
	private String county;
	
	@Getter
	@Setter
	@Column
	private String state;
	
	
}
