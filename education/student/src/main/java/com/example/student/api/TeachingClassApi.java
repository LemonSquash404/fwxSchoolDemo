package com.example.student.api;

import base.ActionResult;
import base.FeignName;
import com.example.student.api.fallback.TeachingClassFallBack;
import com.example.student.entity.Vo.TeachingClassVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = FeignName.TEACHING_CLASS_SERVER_NAME,fallback = TeachingClassFallBack.class,path = "/teachingClassSelect")
public interface TeachingClassApi {

    //查询人数不超过 num 的班级
    @GetMapping("/selectNotFullTeachingClass")
    ActionResult<List<TeachingClassVo>> selectNotFullTeachingClass(@RequestParam("grade") Integer grade, @RequestParam("stuNum")int stuNum);




}
