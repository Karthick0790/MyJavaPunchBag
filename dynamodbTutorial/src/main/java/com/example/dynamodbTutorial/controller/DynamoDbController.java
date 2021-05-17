package com.example.dynamodbTutorial.controller;

import com.example.dynamodbTutorial.DTO.StudentDTO;
import com.example.dynamodbTutorial.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public StudentDTO insert(@RequestBody StudentDTO dto) {
        return studentService.insert(dto);
    }
}
