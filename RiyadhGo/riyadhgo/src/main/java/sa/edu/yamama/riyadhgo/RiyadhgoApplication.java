package sa.edu.yamama.riyadhgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import sa.edu.yamama.riyadhgo.domain.User;
import sa.edu.yamama.riyadhgo.security.RiyadhgoAuthService;
import sa.edu.yamama.riyadhgo.security.UserRoleNames;

@SpringBootApplication
public class RiyadhgoApplication {

	@Autowired
	private RiyadhgoAuthService userService;

	public static void main(String[] args) { //main method for running back-end of RiyadhGo
		SpringApplication.run(RiyadhgoApplication.class, args);
	}

	@EventListener
	public void seed(ContextRefreshedEvent event) {

		if (!userService.isRegisteredBefore("admin@rgo.com")) { //admin credentials
			User admin = new User();
			admin.setEmail("admin@rgo.com");
			admin.setName("RiyadhGo Admin");
			admin.setPassword("123");
			admin.setRole(UserRoleNames.ADMIN);
			userService.registerUser(admin);
		}

	}

}
