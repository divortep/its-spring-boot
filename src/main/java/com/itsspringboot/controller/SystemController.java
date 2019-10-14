package com.itsspringboot.controller;

import com.itsspringboot.service.SystemService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/system")
@Controller
public class SystemController {

  private SystemService systemService;

  @Autowired
  public SystemController(final SystemService systemService) {
    this.systemService = systemService;
  }

  @RequestMapping("/latestClientVersion")
  public ResponseEntity<String> getLatestClientVersion() throws IOException {
    return ResponseEntity.ok(systemService.getLatestClientVersion());
  }
}
