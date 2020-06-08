/*
 * Copyright 2020 Programming Mastery Inc.
 *
 * All Rights Reserved Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Proprietary and confidential
 */

package com.example.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class PageController {

  @GetMapping("/login")
  public String login() {
    return "login_page";
  }

  @GetMapping("/")
  String root(){
    return "redirect:/time";
  }
}
