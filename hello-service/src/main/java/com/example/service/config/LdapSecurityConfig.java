package com.example.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Example Showing how to configure Spring Security with a Generic
 * LDAP Server. If you want to see an example of how to configure AD see
 * the ActiveDirectorySecurityConfiguration Class.
 */
@Profile("!ActiveDirectory")
@Configuration
public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().fullyAuthenticated();

		// configure what happens when an api or web browser make unauthenticated request
		http.exceptionHandling().authenticationEntryPoint(new AppAuthenticationEntryPoint());

		// configure login from
		http.formLogin()
				.defaultSuccessUrl("/time")
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(new AppLoginSuccessHandler())
				.failureHandler(new AppLoginFailureHandler())
				.permitAll();

			http.csrf().disable();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
				.contextSource()
					.url("ldap://localhost:8389/dc=springframework,dc=org")
					.and()
				.passwordCompare()
					.passwordEncoder(new BCryptPasswordEncoder())
					.passwordAttribute("userPassword");
	}

}
