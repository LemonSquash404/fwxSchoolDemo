package com.example.student.controller;


import base.ActionResult;
import com.example.student.api.TeachingClassApi;
import com.example.student.entity.Vo.StudentSelectParam;
import com.example.student.entity.Vo.StudentUpdateVo;
import com.example.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Slf4j
@Tag(name = "student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeachingClassApi teachingClassApi;

    @GetMapping("/selectAll")
    @Operation(description = "全部学生查询")
    public ActionResult select1(){
        return ActionResult.success("查询成功",studentService.selectAll())  ;
    }

    @GetMapping("/selectByPage")
    @Operation(description = "学生列表查询")
    public ActionResult selectByPage(StudentSelectParam param){
       return ActionResult.success("查询成功",studentService.selectByPage(param)) ;
    }
    @PostMapping("/add")
    @Operation(description = "添加学生")
    public ActionResult add1(@Valid @RequestBody StudentUpdateVo student){
        return ActionResult.success(studentService.add(student));
    }
    @PutMapping("/updateName")
    @Operation(description = "根据id修改学生信息")
    public ActionResult updateNameById( StudentUpdateVo param){
        return ActionResult.success(studentService.updateNameById(param));
    }
    @DeleteMapping("/delete")
    @Operation(description = "根据id删除学生")
    public ActionResult delete(String ids){

      int success =   studentService.delete(ids);
        if (success > 0){
            return ActionResult.success("删除成功");
        }
        return ActionResult.fail("删除失败");
    }

    //从redis缓存分页模糊查询学生信息
    @GetMapping("/selectByRedis")
    @Operation(description = "redis缓存学生信息")
    public ActionResult selectByRedis(StudentSelectParam param){
        return ActionResult.success("查询成功",studentService.selectByRedis(param));
    }

    //自动分班 -- 每个班学生不能大于5
    @GetMapping("/autoDivideClassesForStudent")
    @Operation(description = "自动分班")
    public ActionResult autoDivideClassesForStudent(@RequestParam(name = "grade")Integer grade){
        studentService.autoDivideClassesForStudent(grade);
        return ActionResult.success("自动分班成功");
    }

    //手动分班
    @GetMapping("/divideClassesForStudent")
    @Operation(description = "手动分班")
    public ActionResult divideClassesForStudent(@RequestParam(name = "studentId") Integer studentId, @RequestParam(name = "classId")Integer classId){
        studentService.divideClassesForStudent(studentId,classId);
        return ActionResult.success("分班成功");
    }

}
