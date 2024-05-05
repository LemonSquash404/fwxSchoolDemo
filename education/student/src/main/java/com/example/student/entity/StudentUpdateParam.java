package com.example.student.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentUpdateParam {

    private String id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String sex;

    private String age;
}
