package com.example.client;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

class FormLoginInterceptor implements ClientHttpRequestInterceptor {

  private LoginHelper loginHelper;
  private Optional<String> sessionCookie;

  public FormLoginInterceptor(LoginHelper loginHelper) {
    this.loginHelper = loginHelper;
    this.sessionCookie = loginHelper.login();
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {

    if(sessionCookie.isPresent()) {
      request.getHeaders().add(HttpHeaders.COOKIE, sessionCookie.get());
    }

    ClientHttpResponse response = execution.execute(request,body);
    if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
      loginHelper.clearSession();
    }
    return response;
  }
}
