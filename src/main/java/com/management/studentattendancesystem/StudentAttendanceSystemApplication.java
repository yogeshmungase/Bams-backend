package com.management.studentattendancesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.management.studentattendancesystem"})
@EntityScan(basePackages = {"com.management.studentattendancesystem.base.db.model"})
public class StudentAttendanceSystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StudentAttendanceSystemApplication.class, args);
	}

}

