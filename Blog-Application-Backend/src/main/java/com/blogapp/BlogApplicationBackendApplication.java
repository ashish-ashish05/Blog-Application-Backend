package com.blogapp;

import com.blogapp.config.AppConstants;
import com.blogapp.entity.Role;
import com.blogapp.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogApplicationBackendApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private RoleRepository roleRepository;


	@Override
	public void run(String... args) throws Exception {
		try{
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ADMIN_USER");

			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");

			List<Role> roles = List.of(role1, role2);

			this.roleRepository.saveAll(roles);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
