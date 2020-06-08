# sample-ldap-spring-security

# WARNING Work In Progress hello-client RestTemplate Interceptor and LoginHelper are not final they have a few edge
# cases that require some testing.

This repo provides an example of how to use spring security ldap to secure an application. The `hello-service` is an
example that leverages spring security to authenticate the user. `hello-service` has two configurations

* Active Directory which requires a real Widows AD sever to talk you can find the code in `src/main/java/com/example/service/config/ActiveDirectorySecurityConfig.java`
* Generic embedded LDAP server you can find the code in `src/main/java/com/example/service/config/LdapSecurityConfig.java`

startup order 

* h2-server
* ldap-server
* hello-service login with ben / benspassword
* hello-client

