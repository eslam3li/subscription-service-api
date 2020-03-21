package ua.ivan909020.api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${subscription-service.username}")
	private String username;

	@Value("${subscription-service.password}")
	private String password;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		        .anyRequest().authenticated()
		    .and()
		        .httpBasic()
		    .and()
		        .csrf().disable()
		        .formLogin().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser(username).password("{noop}" + password).roles("ADMIN");
	}

}
