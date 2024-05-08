package com.example.teacher.controller;

import base.ActionResult;
import com.example.teacher.dao.Teacher;
import com.example.teacher.dao.Vo.EditTeacherVo;
import com.example.teacher.dao.Vo.SelectTeacherParam;
import com.example.teacher.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherSelect")

@Tag(name = "教师管理")
public class TeacherSelectController {

    @Autowired
    TeacherService teacherService;

    @Operation(description = "全部教师查询")
    @GetMapping("/selectAllTeacher")
    public ActionResult<List<Teacher>> selectAllTeacher() {
        List<Teacher> teachers = teacherService.selectAllTeacher();
        return ActionResult.success(teachers);
    }


    @Operation(description = "列表教师查询")
    @GetMapping("/selectTeacherByPage")
    public ActionResult<List<Teacher>> selectTeacherByPage(SelectTeacherParam selectParam) {
        List<Teacher> teachers = teacherService.selectTeacherByPage(selectParam);
        return ActionResult.success(teachers);
    }

    @Operation(description = "新增教师")
    @PostMapping("/selectAllTeacher")
    public ActionResult addTeacher(Teacher teacher) {
        int i = teacherService.addTeacher(teacher);
        if (i > 0){
            return ActionResult.success("新增成功");
        }
        return ActionResult.fail("新增失败");
    }

    @Operation(description = "修改教师")
    @PutMapping("/editTeacher")
    public ActionResult editTeacher(EditTeacherVo param) {
        int i = teacherService.editTeacher(param);
        if (i > 0){
            return ActionResult.success("修改成功");
        }
        return ActionResult.fail("修改失败");
    }

    @Operation(description = "删除教师")
    @DeleteMapping("/delTeacher")
    public ActionResult delTeacher(String ids) {
        int i = teacherService.delTeacher(ids);
        if (i > 0){
            return ActionResult.success("删除成功");
        }
        return ActionResult.fail("删除失败");
    }

}
