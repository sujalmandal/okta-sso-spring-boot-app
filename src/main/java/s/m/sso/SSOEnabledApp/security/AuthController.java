package s.m.sso.SSOEnabledApp.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	// Login form  
    @GetMapping("/auth/login")
    public String login() {
    	logger.info("# Serving login page from controller.");
        return "login-form.html";  
    }

    // Login form with error  
    @GetMapping("/auth/error")  
    public String loginError(Model model) {
    	logger.info("login failed");
        model.addAttribute("loginError", true);  
        return "login-form.html";  
    }  
}