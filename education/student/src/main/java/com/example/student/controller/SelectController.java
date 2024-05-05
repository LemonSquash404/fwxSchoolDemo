package com.example.student.controller;


import base.ActionResult;
import com.example.student.entity.StudentSelectParam;
import com.example.student.entity.StudentUpdateParam;
import com.example.student.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Slf4j
@Tag(name = "student")
public class SelectController {

    @Autowired
    ServerService serverService;

    @GetMapping("/selectAll")
    @Operation(description = "全部学生查询")
    public ActionResult select1(){
        return ActionResult.success("查询成功",serverService.see1())  ;
    }

    @GetMapping("/selectByPage")
    @Operation(description = "学生列表查询")
    public ActionResult selectByPage(StudentSelectParam param){
       return ActionResult.success("查询成功",serverService.selectByPage(param)) ;
    }
    @PostMapping("/add")
    @Operation(description = "添加学生")
    public ActionResult add1(@Valid @RequestBody StudentUpdateParam student){
        return ActionResult.success(serverService.add1(student));
    }
    @PutMapping("/updateName")
    @Operation(description = "根据id修改学生信息")
    public ActionResult updateNameById( StudentUpdateParam param){
        return ActionResult.success(serverService.updateNameById(param));
    }
    @DeleteMapping("/delete")
    @Operation(description = "根据id删除学生")
    public ActionResult delete1(String id){
        return ActionResult.success(serverService.delete1(id));
    }

    //从redis缓存分页模糊查询学生信息
    @GetMapping("/selectByRedis")
    @Operation(description = "redis缓存学生信息")
    public ActionResult selectByRedis(StudentSelectParam param){
        return ActionResult.success("查询成功",serverService.selectByRedis(param));
    }


}
