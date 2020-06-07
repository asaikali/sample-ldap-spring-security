package com.example;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {

  @GetMapping("/")
  String  get() {
    return "Hello the time is  " + LocalDateTime.now();
  }
}
