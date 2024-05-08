package com.example.student.api.fallback;

import base.ActionResult;
import com.example.student.api.TeachingClassApi;
import org.springframework.stereotype.Component;

@Component
public class TeachingClassFallBack implements TeachingClassApi {


    @Override
    public ActionResult selectNotFullTeachingClass(Integer grade, int stuNUm) {
        return ActionResult.fail("查询人数未满班级失败");
    }
}
