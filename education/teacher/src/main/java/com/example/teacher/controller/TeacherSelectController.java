package com.example.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacherSelect")

@Tag(name = "教师端")
public class TeacherSelectController {

    @Operation(description = "全部教师查询")
    @GetMapping("/selectAllTeacher")
    public String selectAllTeacher() {
        return "王老师";
    }
}
