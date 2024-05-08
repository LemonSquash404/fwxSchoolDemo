package com.example.teachingClass.dao.Vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "班级参数")
public class TeachingClassVo {

    private int id;
    @Schema(description = "班级姓名")
    private String className;


    @Schema(description = "班级人数")
    private int classStuNum;

}

