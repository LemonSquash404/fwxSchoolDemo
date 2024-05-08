package com.example.teachingClass.controller;

import base.ActionResult;
import com.example.teachingClass.dao.TeachingClass;
import com.example.teachingClass.dao.Vo.EditTeachingClassVo;
import com.example.teachingClass.dao.Vo.SelectTeachingClassParam;
import com.example.teachingClass.dao.Vo.TeachingClassVo;
import com.example.teachingClass.service.TeachingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachingClassSelect")

@Tag(name = "班级管理")
public class TeachingClassController {

    @Autowired
    TeachingClassService teachingClassService;

    @Operation(description = "全部班级查询")
    @GetMapping("/selectAllTeachingClass")
    public ActionResult selectAllTeachingClass() {
        List<TeachingClass> teachers = teachingClassService.selectAllTeachingClass();
        return ActionResult.success(teachers);
    }


    @Operation(description = "列表班级查询")
    @GetMapping("/selectTeachingClassByPage")
    public ActionResult selectTeachingClassByPage(SelectTeachingClassParam selectParam) {
        List<TeachingClass> teachingClassList = teachingClassService.selectTeachingClassByPage(selectParam);
        return ActionResult.success(teachingClassList);
    }

    @Operation(description = "新增班级")
    @PostMapping("/addTeachingClass")
    public ActionResult addTeachingClass(@RequestBody TeachingClass teachingClass) {
        int i = teachingClassService.addTeachingClass(teachingClass);
        if (i > 0){
            return ActionResult.success("新增成功");
        }
        return ActionResult.fail("新增失败");
    }

    @Operation(description = "修改班级")
    @PutMapping("/editTeachingClass")
    public ActionResult editTeachingClass(@RequestBody EditTeachingClassVo vo) {
        int i = teachingClassService.editTeachingClass(vo);
        if (i > 0){
            return ActionResult.success("修改成功");
        }
        return ActionResult.fail("修改失败");
    }

    @Operation(description = "删除班级")
    @DeleteMapping("/delTeachingClass")
    public ActionResult delTeachingClass(String ids) {
        int i = teachingClassService.delTeachingClass(ids);
        if (i > 0){
            return ActionResult.success("删除成功");
        }
        return ActionResult.fail("删除失败");
    }


    //查询人数不超过 num 的班级
    @GetMapping("/selectNotFullTeachingClass")
    @Operation(description = "查询人数不超过 num 的班级")
    public ActionResult selectNotFullTeachingClass(@RequestParam(name = "grade") Integer grade,@RequestParam(name = "stuNum")Integer stuNum){
        List<TeachingClassVo> teachingClasses = teachingClassService.selectNotFullTeachingClass(grade, stuNum);
        return ActionResult.success(teachingClasses);
    }
}
