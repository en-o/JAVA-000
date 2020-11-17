package com.tn.starteruse;

import com.tn.starter.entity.Klass;
import com.tn.starter.entity.School;
import com.tn.starter.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//@SpringBootApplication(scanBasePackages = "com.tn")
@SpringBootApplication
public class StarterUseApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(StarterUseApplication.class, args);

//		Student student = run.getBean(Student.class);
//		System.out.println(student.toString());
//		student.create();
//		Klass klass = new Klass();
		Klass klass = run.getBean(Klass.class);
		klass.dong();
//		School school = new School();
//		school.ding();

	}

}
