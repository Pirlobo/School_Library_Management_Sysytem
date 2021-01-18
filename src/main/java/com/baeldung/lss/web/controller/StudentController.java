package com.baeldung.lss.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

	
	@GetMapping("/studentPage")
	public String studentPage() {
		return "successfullLoginPage.html";
	}
}
