package demo.demoJWT;

import demo.demoJWT.model.User;
import demo.demoJWT.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DemoJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
//			userService.saveRole(new Role(null,"Role_User",null));
//			userService.saveRole(new Role(null,"Role_Manager",null));
//			userService.saveRole(new Role(null,"Role_Admin",null));
//			userService.saveRole(new Role(null,"Role_Super",null));
//
//			userService.saveUser(new User(null,"Hai","hai","123",new ArrayList<>()));
//			userService.saveUser(new User(null,"Hung","hung","123",new ArrayList<>()));
//			userService.saveUser(new User(null,"Hien","hien","123",new ArrayList<>()));
//
//			userService.addRoleToUser("hai","Role_Admin");
//			userService.addRoleToUser("hai","Role_Manager");
//
//			userService.addRoleToUser("hien","Role_User");
//			userService.addRoleToUser("hung","Role_Manager");
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
