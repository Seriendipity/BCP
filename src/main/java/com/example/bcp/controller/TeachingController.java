package com.example.bcp.controller;

import com.example.bcp.entity.Result;
import com.example.bcp.entity.Teacher;
import com.example.bcp.entity.Teaching;
import com.example.bcp.mapper.TeachingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("teaching")
public class TeachingController {
    @Autowired
    TeachingMapper teachingMapper;

    @GetMapping("allTeaching")
    private Result allTeaching() {
        List<Teaching> allTeaching = teachingMapper.selectAllTeaching();
        return Result.success(allTeaching);
    }

    @GetMapping("selectByTeacherNo/{TeacherNo}")
    private Result selectByTeacherNo(@PathVariable("TeacherNo") String TeacherNo) {
        List<Teaching> selectByTeacherNo = teachingMapper.selectByTeacherNo(TeacherNo);
        return Result.success(selectByTeacherNo);
    }

    @GetMapping("selectByCourseNo/{CourseNo}")
    private Result selectByCourseNo(@PathVariable("CourseNo") String CourseNo) {
        List<Teaching> selectByCourseNo = teachingMapper.selectByCourseNo(CourseNo);
        return Result.success(selectByCourseNo);
    }

    @GetMapping("selectByCid/{Cid}")
    private Result selectByCid(@PathVariable("Cid") String Cid) {
        Teaching selectByCid = teachingMapper.selectByCid(Cid);
        return Result.success(selectByCid);
    }

    @GetMapping("selectByTeacherNoAndCourseNo/{TeacherNo}/{CourseNo}")
    private Result selectByTeacherNoAndCourseNo(@PathVariable("TeacherNo") String TeacherNo,
                                                @PathVariable("CourseNo") String CourseNo) {
        List<Teaching> selectByTeacherNoAndCourseNo = teachingMapper.selectByTeacherNoAndCourseNo(TeacherNo, CourseNo);
        return Result.success(selectByTeacherNoAndCourseNo);
    }
}
