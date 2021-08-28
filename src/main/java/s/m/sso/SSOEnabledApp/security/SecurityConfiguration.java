package s.m.sso.SSOEnabledApp.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/* 
 * extending WebSecurityConfigurerAdapter will force us to configure the security specifics
 */
@Component
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${security.ignore.urls}")
	private String[] publicUrls;
	
	@Autowired
	private PasswordEncoder encoder;

	/* Configure AUTHENTICATION settings */
	
	/* in case we also want a custom, in-house authentication to be present in the app,
	 * we can use authentication manager to configure a source of user information against which
	 * the user will be lookup up and the password will be matched
	 */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* for demo purposes we will use in-memory user info */
		auth.inMemoryAuthentication()
		.withUser("admin")
		.password(encoder.encode("admin123"))
		.roles("ADMIN");
	}
	
	/* configure AUTHORIZATION settings */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        /* define a custom login page */
        .formLogin()
        .loginPage("/auth/login")
        .failureUrl("/auth/error")
        .loginProcessingUrl("/auth/process")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll()
        .and()
        .authorizeRequests().antMatchers(publicUrls).permitAll()
        .and()
        .authorizeRequests()
        /* then set any other urls to be accessible only after authentication */
		.anyRequest().authenticated();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}