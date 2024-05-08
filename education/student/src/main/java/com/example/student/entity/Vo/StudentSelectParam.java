package com.example.student.entity.Vo;

import base.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "学生查询条件")
public class StudentSelectParam extends Pagination {

    private String id;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "性别")
    private String sex;
    @Schema(description = "年龄")
    private String age;
    @Schema(description = "年级")
    private String grade;

}
