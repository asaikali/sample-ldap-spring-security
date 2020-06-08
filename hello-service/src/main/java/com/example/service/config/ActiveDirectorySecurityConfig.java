package com.example.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

/** Example showing how to configure Active Directory with the built in spring security
 * active directory ldap provider. Run the sample application with the ActiveDirectory
 * Profile turned on -Dspring.profiles.active=ActiveDirectory and configure the
 * application-ActiveDirectory.yml file to point to your AD instance
 */
@Profile("ActiveDirectory")
@Configuration
public class ActiveDirectorySecurityConfig extends WebSecurityConfigurerAdapter {

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

  @Value("${spring.ldap.urls}")
  private String url;
  @Value("${demo.ActiveDirectory.domain}")
  private String domain;
  @Value("${demo.ActiveDirectory.searchFilter}")
  private String searchFilter;

  @Bean
  public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
    ActiveDirectoryLdapAuthenticationProvider adProvider = new
        ActiveDirectoryLdapAuthenticationProvider(domain, url);
    // modify the default search filter to check tha the user is in storeServices Group
    // if you don't set this field it will just take any user in AD not just users that
    // part of a  certain group
    adProvider.setSearchFilter(searchFilter);
    return adProvider;
  }


}
