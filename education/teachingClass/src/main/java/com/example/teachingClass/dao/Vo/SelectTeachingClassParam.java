package com.example.teachingClass.dao.Vo;

import base.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "查询班级参数")
public class SelectTeachingClassParam extends Pagination {

    private int id;

    @Schema(description = "班级名称")
    private String className;


}

