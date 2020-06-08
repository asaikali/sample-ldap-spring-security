/*
 * Copyright 2020 Programming Mastery Inc.
 *
 * All Rights Reserved Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */

package com.example.service.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Handles requests to login correctly for humans and api clients.
 *
 * <p>When a human tries to access a secured url and they are not logged in. An HTTP 302 redirect to
 * the login page is required.
 *
 * <p>When an api client tries to access a secured url and they are not logged in. A JSON response
 * should be sent with a 401 unauthorized http status code. This way the api client decide to login
 * if it wants.
 */
class AppAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
  public AppAuthenticationEntryPoint() {
    super("/login");
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    if (isContentTypeJson(request)) {
      response.setContentType("application/json");
      response.setStatus(401);
      response.getWriter().println("{  \"status\": 401, \"message\": \"please login\" }");
    } else {
      super.commence(request, response, authException);
    }
  }

  /**
   * Checks if the http request body content type is application/json.
   *
   * @param request current servlet request
   * @return true if the http request content type was application/json
   */
   boolean isContentTypeJson(HttpServletRequest request) {
    try {
      MediaType requestType = MediaType.parseMediaType(request.getContentType());
      return MediaType.APPLICATION_JSON.isCompatibleWith(requestType);
    } catch (InvalidMediaTypeException e) {
      return false;
    }
  }
}
