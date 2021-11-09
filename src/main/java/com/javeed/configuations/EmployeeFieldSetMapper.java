package com.javeed.configuations;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {

	@Override
	public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
		
		return new Employee(fieldSet.readLong("id"),
					fieldSet.readString("firstName"),
					fieldSet.readString("lastName"),
					fieldSet.readString("companyName"),
					fieldSet.readString("address"),
					fieldSet.readString("city"),
					fieldSet.readString("county"),
					fieldSet.readString("state")
					
				);
	}

}
