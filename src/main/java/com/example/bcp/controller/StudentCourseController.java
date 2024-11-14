package com.example.bcp.controller;

import com.example.bcp.entity.Result;
import com.example.bcp.entity.StudentCourse;
import com.example.bcp.entity.Teaching;
import com.example.bcp.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StudentCourse")
public class StudentCourseController {
    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    TeachingService teachingService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassService classService;

    /**
     *  返回选修某门课的所有学生
     */
    @GetMapping("/allStudent")
    public Result allStudent(@RequestParam String cid,HttpServletRequest request) {
        List<StudentCourse> studentList = studentCourseService.selectByCid(cid);
        String username = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        String studentNo,studentName,classNo,className;
        int grade;

        for(StudentCourse sc : studentList){
            grade = sc.getGrade();
            studentNo = sc.getStudentNo();
            studentName = studentService.selectByStudentNo(studentNo).getStudentName();
            classNo = studentService.selectByStudentNo(studentNo).getClassNo();
            className = classService.selectByClassNo(classNo).getClassName();
            // 将学生信息放入一个子Map中
            Map<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("index",i);
            studentInfo.put("studentNo", studentNo);
            studentInfo.put("studentName", studentName);
            studentInfo.put("grade", grade);
            studentInfo.put("classNo",classNo);
            studentInfo.put("className", className);
            responseData.put("student"+i,studentInfo);
            i++;
        }
        responseData.put("username",username);
        return Result.success(responseData);
    }

    /**
     *  导出选修某门课程的学生名单
     *
     */

    @GetMapping("/exportStudentList")
    public ResponseEntity<byte[]> exportStudentList(@RequestParam String cid) {
        List<StudentCourse> studentList = studentCourseService.selectByCid(cid);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student List");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Index");
        headerRow.createCell(1).setCellValue("StudentNo");
        headerRow.createCell(2).setCellValue("StudentName");
        headerRow.createCell(3).setCellValue("Grade");
        headerRow.createCell(4).setCellValue("ClassNo");
        headerRow.createCell(5).setCellValue("ClassName");

        // 填充学生信息
        int rowIndex = 1;
        for (StudentCourse sc : studentList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(rowIndex - 1);
            row.createCell(1).setCellValue(sc.getStudentNo());
            row.createCell(2).setCellValue(studentService.selectByStudentNo(sc.getStudentNo()).getStudentName());
            row.createCell(3).setCellValue(sc.getGrade());
            String classNo = studentService.selectByStudentNo(sc.getStudentNo()).getClassNo();
            row.createCell(4).setCellValue(classNo);
            row.createCell(5).setCellValue(classService.selectByClassNo(classNo).getClassName());
        }

        String filePath = "student_list.xlsx"; // 替换为你的目录
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 导出为 Excel 文件
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=student_list.xlsx");
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  查询某学生选修的所有课程或者教师教授的所有课程
     */
    @GetMapping(value = "/allCourse")
    public Result allCourse( HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();
        if(username.startsWith("S")){
            int i = 1;
            String cid,courseNo,courseName,picture;

            List<StudentCourse> courseList = studentCourseService.selectByStudentNo(username);
            for(StudentCourse sc : courseList){
                cid = sc.getCid();
                courseNo = teachingService.selectByCid(cid).getCourseNo();
                courseName = courseService.selectByCourseNo(courseNo).getCourseName();
                picture = teachingService.selectByCid(cid).getPicture();
                // 将课程信息放入一个子Map中
                Map<String, Object> courseInfo = new HashMap<>();
                courseInfo.put("cid", cid);
                courseInfo.put("courseNo", courseNo);
                courseInfo.put("courseName", courseName);
                courseInfo.put("picture", picture);
                responseData.put("course"+i,courseInfo);
                i++;
            }
            System.out.println(responseData);
        } else if (username.startsWith("T")) {

            int i = 1;
            List<Teaching> teachings = teachingService.selectByTeacherNo(username);
            for(Teaching t : teachings){
                String cid = t.getCid();
                String courseNo = teachingService.selectByCid(cid).getCourseNo();
                String courseName = courseService.selectByCourseNo(courseNo).getCourseName();
                String picture = teachingService.selectByCid(cid).getPicture();
                // 将课程信息放入一个子Map中
                Map<String, Object> courseInfo = new HashMap<>();
                courseInfo.put("cid", cid);
                courseInfo.put("courseNo", courseNo);
                courseInfo.put("courseName", courseName);
                courseInfo.put("picture", picture);
                responseData.put("course"+i,courseInfo);
                i++;
            }


        }

        return Result.success(responseData);

    }
}
