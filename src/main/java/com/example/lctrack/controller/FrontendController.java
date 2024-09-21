package com.example.lctrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
  // Forward all non-file requests to root, allowing React Router to handle client side routing
  @GetMapping(value = "/{path:[^\\.]*}")
  public String forward() {
    return "forward:/";
  }
}

