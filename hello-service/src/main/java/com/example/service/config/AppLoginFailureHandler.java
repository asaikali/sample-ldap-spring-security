package com.example.service.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Handles login failures correctly for humans and api clients.
 *
 * <p>When a human is login in via a browser. A login failure should redirect to the login error
 * page. This means sending back a 302 redirect to the login error page, the user can retype the
 * username / password.
 *
 * <p>When an api client login fails a JSON response should be sent with a 401 unauthorized http
 * status code. This way the api client can inform the user that the login failed and request
 * correct credentials.
 *
 * @author Adib Saikali
 */
class AppLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    String apiLoginHeader = request.getHeader("x-api-login");
    if ("true".equals(apiLoginHeader)) {
      response.setContentType("application/json");
      response.setStatus(401);
      response
          .getWriter()
          .println(
              "{\n"
                  + "  \"status\": 401,\n"
                  + "  \"message\": \"unable to login, try with correct credentials\"\n"
                  + "}");
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }
}
