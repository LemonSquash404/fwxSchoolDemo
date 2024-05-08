package com.example.teacher.dao.Vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "教师查询vo")
//@EqualsAndHashCode(callSuper = true)
public class EditTeacherVo {

    private int id;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "性别")
    private String sex;
    @Schema(description = "年龄")
    private String age;

}

