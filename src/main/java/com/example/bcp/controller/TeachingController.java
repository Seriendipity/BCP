package com.example.bcp.controller;

import com.example.bcp.entity.Course;
import com.example.bcp.entity.Result;
import com.example.bcp.entity.Teacher;
import com.example.bcp.entity.Teaching;
import com.example.bcp.mapper.TeachingMapper;
import com.example.bcp.service.CourseService;
import com.example.bcp.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teaching")
public class TeachingController {
    @Autowired
    TeachingMapper teachingMapper;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;

    @GetMapping("/allTeaching")
    private Result allTeaching() {
        List<Teaching> allTeaching = teachingMapper.selectAllTeaching();
        return Result.success(allTeaching);
    }

    @GetMapping("/selectByTeacherNo")
    private Result selectByTeacherNo(@RequestParam String TeacherNo) {
        List<Teaching> selectByTeacherNo = teachingMapper.selectByTeacherNo(TeacherNo);
        return Result.success(selectByTeacherNo);
    }

    @GetMapping("/selectByCourseNo")
    private Result selectByCourseNo(@RequestParam String CourseNo) {
        List<Teaching> selectByCourseNo = teachingMapper.selectByCourseNo(CourseNo);
        return Result.success(selectByCourseNo);
    }

    @GetMapping("/selectByCid")
    private Result selectByCid(@RequestParam String Cid) {
        Teaching selectByCid = teachingMapper.selectByCid(Cid);
        return Result.success(selectByCid);
    }

    @GetMapping("/selectByTeacherNoAndCourseNo")
    private Result selectByTeacherNoAndCourseNo(@RequestParam String TeacherNo,
                                                @RequestParam String CourseNo) {
        List<Teaching> selectByTeacherNoAndCourseNo = teachingMapper.selectByTeacherNoAndCourseNo(TeacherNo, CourseNo);
        return Result.success(selectByTeacherNoAndCourseNo);
    }

    @GetMapping("/getCourseInfo")
    public Result getCourseInfo(@RequestParam String cid , HttpServletRequest request){
        System.out.println("进入获取课程信息界面");
        System.out.println("-----------------------------------");
        String username = request.getAttribute("username").toString();
        Teaching t = teachingMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();

        String courseNo = t.getCourseNo();
        Course c = courseService.selectByCourseNo(courseNo);

        String teacherNo = t.getTeacherNo();
        Teacher teacher = teacherService.selectByTeacherNo(teacherNo);

        responseData.put("courseInfo",t.getCourseIntroduction());
        responseData.put("courseNo",c.getCourseNo());
        responseData.put("cid",cid);
        responseData.put("courseName",c.getCourseName());
        responseData.put("teacherName",teacher.getTeacherName());
        responseData.put("establishCollege",t.getEstablishCollege());
        responseData.put("semester",t.getSemester());
        responseData.put("username",username);
        System.out.println(responseData);
        return Result.success(responseData);
    }

    @GetMapping("/getSyllabus")
    public Result getSyllabus(@RequestParam String cid, HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        Teaching t = teachingMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("Syllabus",t.getSyllabus());
        responseData.put("username",username);
        return Result.success(responseData);
    }

    @GetMapping("/getCalendar")
    public Result getCalendar(@RequestParam String cid , HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        Teaching t = teachingMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("Calendar",t.getTeachingCalendar());
        responseData.put("username",username);
        return Result.success(responseData);
    }

    @GetMapping("/getTeacherInfo")
    public Result getTeacherInfo(@RequestParam String cid , HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        Teaching teaching = teachingMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        String teacherNo = teaching.getTeacherNo();
        Teacher t = teacherService.selectByTeacherNo(teacherNo);
        responseData.put("teacherNo",t.getTeacherNo());
        responseData.put("teacherName",t.getTeacherName());
        responseData.put("teacherTitle",t.getTeacherTitle());
        responseData.put("teacherPhone",t.getTeacherPhone());
        responseData.put("teacherInfo",t.getTeacherInfo());
        responseData.put("establishCollege",teaching.getEstablishCollege());
        responseData.put("TeacherStation",t.getTeacherStation());
        responseData.put("teacherEmail",t.getTeacherEmail());
        responseData.put("teacherBirthday",t.getTeacherBirthday());
        responseData.put("teacherSex",t.getTeacherSex());
        responseData.put("head_portait",t.getPicturePath());
        responseData.put("username", username);
        return Result.success(responseData);
    }


}
