package com.example.teachingClass.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("teaching_class")
public class TeachingClass {

    private int id;

    @TableField("grade")
    private String grade;

    @TableField("class_name")
    private String className;



    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("creat_time")
    private Date creatTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("edit_time")
    private Date editTime;

}

