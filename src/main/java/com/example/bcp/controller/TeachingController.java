package com.example.bcp.controller;

import com.example.bcp.mapper.TeachingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teaching")
public class TeachingController {
    @Autowired
    TeachingMapper teachingMapper;
}
