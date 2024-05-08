package com.example.student.entity.Vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Tag(name = "学生修改条件")
public class StudentUpdateVo {

    private String id;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "年龄")
    private String age;

    @Schema(description = "年级")
    private String grade;
}
