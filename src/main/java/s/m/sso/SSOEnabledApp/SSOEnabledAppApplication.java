package s.m.sso.SSOEnabledApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import s.m.sso.SSOEnabledApp.security.LoginContext;

@SpringBootApplication
@RestController
public class SSOEnabledAppApplication {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(SSOEnabledAppApplication.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Welcome home "+LoginContext.CURRENT.getUsername();
	}
	
	@RequestMapping("/resource")
	@ResponseBody
	public String resource() {
		logger.info("# protected resource accessed by {} using authentication type {}",
				LoginContext.CURRENT.getUsername(),LoginContext.CURRENT.getAuthType());
		return "protected resource accessed";
	}
}