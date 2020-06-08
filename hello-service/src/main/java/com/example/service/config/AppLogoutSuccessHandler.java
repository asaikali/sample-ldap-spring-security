package com.example.service.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;


/**
 * Handles logout success correctly for humans and api clients.
 *
 * <p>When a human is login in via a browser. A logout success should redirect to the logout success
 * or back to the login screen. This means  sending back a 302 redirect to the designated logout
 * success page.
 *
 * <p>When an api client logs out successfully a JSON response should be sent with HTTP 200 OK.
 *
 * @author Adib Saikali
 */
class AppLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    if (isContentTypeJson(request)) {
      response.setContentType("application/json");
      response.setStatus(200);
      response.getWriter().println("{\"success\":\"true\"}");
    } else {
      super.onLogoutSuccess(request, response, authentication);
    }
  }

  /**
   * Checks if the http request body content type is application/json.
   *
   * @param request current servlet request
   * @return true if the http request content type was application/json
   */
  static boolean isContentTypeJson(HttpServletRequest request) {
    try {
      MediaType requestType = MediaType.parseMediaType(request.getContentType());
      return MediaType.APPLICATION_JSON.isCompatibleWith(requestType);
    } catch (InvalidMediaTypeException e) {
      return false;
    }
  }
}
