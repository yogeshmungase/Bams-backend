package com.management.studenattendancesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.amdocs.amdil"})
@EntityScan(basePackages = {"com.amdocs.amdil.base.db.model"})
public class StudentAttendanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentAttendanceSystemApplication.class, args);
	}

}

