package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student")
public class Student {

    @TableId
    private int id;

    @TableField("name")
    private String name;
    @TableField("sex")
    private String sex;
    @TableField("age")
    private String age;
    @TableField("grade")
    private String grade;
    @TableField("delFlag")
//    @TableLogic(value = "0",delval = "1")
    private String delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("creat_time")
    private Date creatTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("edit_time")
    private Date editTime;
}
