package com.example.service.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * Handles login success correctly for humans and api clients.
 *
 * <p>When a human is login in via a browser. A login success should redirect to the login success
 * page or whatever url they were trying to get to before they were asked to login. This means
 * sending back a 302 redirect to the login error page, the user can retype the username / password.
 *
 * <p>When an api client login successfully a JSON response should be sent with HTTP 200 OK. It is
 * assumed that the api client will just call whatever api they want once they are logged in.
 *
 * @author Adib Saikali
 */
class AppLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {

    String apiLoginHeader = request.getHeader("x-api-login");
    if ("true".equals(apiLoginHeader)) {
      response.setContentType("application/json");
      response.setStatus(200);
      response.getWriter().println("{success:true}");
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }
}
