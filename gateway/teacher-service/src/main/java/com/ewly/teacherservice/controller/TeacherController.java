package com.ewly.teacherservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    @GetMapping("teacher")
    public String teach() {
        return "教书学习";
    }
}
