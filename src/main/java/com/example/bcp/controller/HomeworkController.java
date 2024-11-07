package com.example.bcp.controller;

import cn.hutool.core.io.FileUtil;
import com.example.bcp.entity.Homework;
import com.example.bcp.entity.StudentHomework;

import com.example.bcp.mapper.HomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.service.HomeworkService;
import com.example.bcp.service.NotificationService;
import com.example.bcp.service.StudentCourseService;
import com.example.bcp.service.StudentHomeworkService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentHomeworkService studentHomeworkService;


    //返回某cid对应所有homework
    @GetMapping("/allCidHomework")
    public Result selectByCid(@RequestParam String cid, HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        if(userNo.startsWith("S")){
            List<Homework> homeworks = homeworkService.selectByCidAndVisible(cid);
            Map<String,Object> responseData = new HashMap<>();
            int index = 1;
            for(Homework homework:homeworks){
                Map<String,Object> hw = new HashMap<>();
                hw.put("homeworkNO",homework.getHomeworkNo());
                hw.put("homeworkDescription",homework.getHomeworkDescription());
                hw.put("homeworkStratTime",homework.getStartTime());
                hw.put("homeworkEndTime",homework.getEndTime());

                int studentNumbers = studentCourseService.selectByCid(cid).size();
                int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
                String submitNumber = submitNumbers+"/"+studentNumbers;
                hw.put("submitNumber",submitNumber);

                StudentHomework sh = studentHomeworkService.selectByStudentNoAndHomeworkNo(userNo, homework.getHomeworkNo());
                if(sh == null){
                    hw.put("status","未提交");
                    hw.put("grade",null);
                }else {
                    hw.put("status","已提交");
                    hw.put("grade",sh.getSubmitGrade());
                }

                responseData.put("homework"+index,hw);
                index++;
            }
            return Result.success(responseData);
        }else if(userNo.startsWith("T")){
            List<Homework> homeworks = homeworkService.selectByCid(cid);
            Map<String,Object> responseData = new HashMap<>();
            int index = 1;
            for(Homework homework:homeworks){
                Map<String,Object> hw = new HashMap<>();
                hw.put("homeworkNO",homework.getHomeworkNo());
                hw.put("homeworkDescription",homework.getHomeworkDescription());
                hw.put("homeworkStratTime",homework.getStartTime());
                hw.put("homeworkEndTime",homework.getEndTime());

                int studentNumbers = studentCourseService.selectByCid(cid).size();
                int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
                String submitNumber = submitNumbers+"/"+studentNumbers;
                hw.put("submitNumber",submitNumber);

                int correctingNumber = studentHomeworkService.selectCorrectingHomework(homework.getHomeworkNo()).size();
                System.out.println("C is " + correctingNumber);
                System.out.println("S is " + submitNumbers);
                if(!homework.getVisible()){
                    hw.put("correctStatus","未发布");
                } else if(correctingNumber == submitNumbers && correctingNumber != 0){
                    hw.put("correctStatus","已完成");
                }else{
                    hw.put("correctStatus","未完成");
                }
                responseData.put("homework"+index,hw);
                index++;
            }
            return Result.success(responseData);
        }
       return Result.success();
    }

    @GetMapping("/allStudentHomework")
    public Result selectByHomeworkNO(@RequestParam String homeworkNo) {
//        String username = request.getAttribute("username").toString();
        List<StudentHomework> studentHomeworks = studentHomeworkMapper.selectByHomeworkNo(homeworkNo);
        Map<String,Object> responseData = new HashMap<>();
        int index = 1;
        for(StudentHomework studentHomework:studentHomeworks){
            Map<String,Object> sh = new HashMap<>();
            sh.put("StudentNo",studentHomework.getStudentNo());
            sh.put("StudentDescription()",studentHomework.getSubmitDescription());
            sh.put("StudentHomeworkSubmitTime",studentHomework.getSubmitTime());
        }

        return Result.success(responseData);
    }

    @PostMapping("/updateHomework")
    public Result updateHomework(@RequestBody Map<String,Object> requestData){
        LocalDateTime newEndTime = (LocalDateTime) requestData.get("newEndTime");
        int fixedPoint = (int)requestData.get("scores");
        String homeworkNo = requestData.get("homeworkNo").toString();
        String homeworkDesc= requestData.get("homeworkDesc").toString();
        String homeworkPath = requestData.get("path").toString();
        homeworkService.updateHomeworkEndTime(newEndTime,homeworkNo);
        homeworkService.updateHomeworkPath(homeworkPath,homeworkNo);
        homeworkService.updateHomeworkDescription(homeworkDesc,homeworkNo);
        homeworkService.updateHomeworkGrade(fixedPoint,homeworkNo);
        return Result.success();
    }

    //对某student的作业打分
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestBody Map<String, Object> requestData) {
        int submitGrade = Integer.parseInt(requestData.get("SubmitGrade").toString());
        String studentNo = (String) requestData.get("StudentNo");
        String homeworkNo = (String) requestData.get("HomeworkNo");
        String comment = requestData.get("comment").toString();
        try {
            studentHomeworkMapper.updateStudentHomeworkSubmitGrade(submitGrade, studentNo, homeworkNo, comment);
            return Result.success("成功批改");
        } catch (Exception e) {
            return Result.success("批改失败");
        }

    }

    //-------------------------------作业文件上传下载---------------------------------

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("Cid") String Cid,
                         @RequestParam("homeworkDescription") String homeworkDescription,
                         @RequestParam("startTime") LocalDateTime startTime,
                         @RequestParam("endTime") LocalDateTime endTime,
                         @RequestParam("homeworkGrade") int homeworkGrade) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用文件上传逻辑
            String savedFilePath = uploadFile(file);
            // 获取新文件的 ID（这里假设你有一个方法获取最大 ID）
//            int newId = courseResourceService.maxNo() + 1;

            int size = homeworkService.selectAllHomework().size() + 1;
            String newHomeworkNo = "H" + size;


            // 插入文件记录到数据库
            // public void insertHomework(String HomeworkNo, String Cid, String HomeworkDescription,
            //        LocalDateTime StartTime , LocalDateTime EndTime , int HomeworkGrade)
            String url = "http://" + ip + ":" + port + "/homework/download/" + savedFilePath;
            homeworkService.insertHomework(newHomeworkNo,Cid,homeworkDescription,startTime,endTime,homeworkGrade);

            return Result.success(url);  //返回文件的链接
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }
    }

    // 文件上传逻辑
    public String uploadFile(MultipartFile file) throws IOException {
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }

        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        String extName = FileUtil.extName(originalFilename); // 获取文件扩展名png
        System.out.println("文件的原始名称：" + originalFilename);

        // 检查文件是否存在并处理重命名
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
            // 如果当前上传的文件已经存在了，那么这个时候就要重命名一个文件名称
            originalFilename = homeworkService.selectAllHomework().size() + 1 + "." + extName;
        }

        // 保存文件到本地
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        System.out.println("文件的保存路径：" + saveFile.getAbsolutePath());
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去

        // 返回保存的文件名
        return originalFilename;
    }


    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        String filePath = ROOT_PATH + File.separator + fileName;
        System.out.println(filePath);

        if (!FileUtil.exist(filePath)) {
            return Result.error("文件不存在");
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
        System.out.println("文件下载成功");
        return Result.success(filePath);
    }


}
