package com.example.bcp.controller;

import cn.hutool.core.io.FileUtil;
import com.example.bcp.entity.Homework;
import com.example.bcp.entity.Result;
import com.example.bcp.entity.StudentHomework;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.service.HomeworkService;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.service.StudentCourseService;
import com.example.bcp.service.StudentHomeworkService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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

    @Autowired
    PeerReviewAssignmentService peerReviewAssignmentService;

    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:Indentation"})
    @PostMapping("/assignHomework")
    public Result assignHomework(@RequestBody Map<String, Object> requestData) {
        String homeworkNo = requestData.get("HomeworkNo").toString();
        String startTime1 = requestData.get("StartTime").toString();
        String endTime1 = requestData.get("EndTime").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTime1, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTime1, formatter);

        homeworkService.updateIsPeerReview(true, homeworkNo);


        List<StudentHomework> studentHomeworks = studentHomeworkService.selectByHomeworkNo(homeworkNo);
        int studentCount = studentHomeworks.size();

        List<StudentHomework> allAssignments = new ArrayList<>();
        for (StudentHomework sh : studentHomeworks) {
            allAssignments.add(sh);
        }

        Collections.shuffle(allAssignments); // 随机打乱作业
        List<StudentHomework> assignmentResults = new ArrayList<>(allAssignments);//随机作业整体右移
        Collections.rotate(assignmentResults, 1);
        for (int i = 0; i < studentCount; i++) {
            StudentHomework currentStudentHomework = allAssignments.get(i);
            String currentStudentNo = currentStudentHomework.getStudentNo();
            System.out.println("currentStudet" + currentStudentNo + "i:" + i);
            StudentHomework assignedHomework = assignmentResults.get(i);
            String assignedStudentNo = assignedHomework.getStudentNo();
            System.out.println("assignedStudentNo" + assignedStudentNo + "i:" + i);

            if (i == 0 && peerReviewAssignmentService.selectByHomeworkNo(homeworkNo) != null) {
                peerReviewAssignmentService.deleteByHomework(homeworkNo);
                peerReviewAssignmentService.insertPeerReviewAssignment(assignedStudentNo, currentStudentNo, homeworkNo, startTime, endTime);
                System.out.println("第一份：被评作业者：" + assignedStudentNo + "评审员：" + currentStudentNo);
            } else {
                peerReviewAssignmentService.insertPeerReviewAssignment(assignedStudentNo, currentStudentNo, homeworkNo, startTime, endTime);
            }
            //插入每个人的第二份互评作业
            StudentHomework nextHomework;
            String nextStudenNo;
            if (i != 0) {
                nextHomework = assignmentResults.get(i - 1);
                nextStudenNo = nextHomework.getStudentNo();
            } else {
                nextHomework = assignmentResults.get(studentCount - 1);
                nextStudenNo = nextHomework.getStudentNo();
            }
            peerReviewAssignmentService.insertPeerReviewAssignment(nextStudenNo, currentStudentNo, homeworkNo, startTime, endTime);
            System.out.println("第二份：" + "被评作业者：" + nextStudenNo + "评审员：" + currentStudentNo + "I:" + i);
        }


//        // 存储每个学生分配到的作业(避免重复）
//        List<List<StudentHomework>> studentAssignments = new ArrayList<>();
//        for (int i = 0; i < studentCount; i++) {
//            studentAssignments.add(new ArrayList<>());
//        }
//
//        int index = 0;
//        // 第一次作业分配
//        for (int i = 0; i < studentCount; i++) {
//            StudentHomework currentStudentHomework = studentHomeworks.get(i);
//            String currentStudentNo = currentStudentHomework.getStudentNo();
//            //分配一个！=学号的
//            while (allAssignments.get(index).getStudentNo().equals(currentStudentNo)) {
//                index += 1;
//                if (index > allAssignments.size()) {
//                    return Result.success("分配错误");
//                }
//                allAssignments.add(allAssignments.get(index));
//            }
//            StudentHomework assignedHomework = allAssignments.get(index);
//            String assignedStudentNo = assignedHomework.getStudentNo();
//            studentAssignments.get(i).add(assignedHomework);
//            try {
//                // 尝试插入新的评价任务
//                peerReviewAssignmentService.insertPeerReviewAssignment(assignedStudentNo, currentStudentNo, homeworkNo, startTime, endTime);
//                System.out.println("被评作业者："+assignedStudentNo+"评审员："+currentStudentNo);
//            } catch (Exception e) {
//                // 如果插入失败，首先尝试删除旧数据
//                try {
//                    peerReviewAssignmentService.deleteByHomework(homeworkNo);
//                    System.out.println("被评作业者："+assignedStudentNo+"评审员："+currentStudentNo);
//                } catch (Exception deleteException) {
//                    // 如果删除失败，记录删除失败的异常信息
//                    System.err.println("删除旧的评价任务失败: " + deleteException.getMessage());
//                    // 根据需求选择是否继续处理或终止操作
//                }
//
//                // 删除成功或失败后，重新插入新的数据
//                try {
//                    peerReviewAssignmentService.insertPeerReviewAssignment(assignedStudentNo, currentStudentNo, homeworkNo, startTime, endTime);
//                } catch (Exception insertException) {
//                    // 如果插入失败，记录插入失败的异常信息
//                    System.err.println("插入新的评价任务失败: " + insertException.getMessage());
//                }
//            }
//
//        }
//
//        // 重新打乱作业
//        allAssignments.clear();
//        for (StudentHomework sh : studentHomeworks) {
//            allAssignments.add(sh);
//        }
//
//        Collections.shuffle(allAssignments);
//
//
//        index = 0;
//        // 第二次作业分配
//        for (int i = 0; i < studentCount; i++) {
//            StudentHomework currentStudentHomework = studentHomeworks.get(i);
//            String currentStudentNo = currentStudentHomework.getStudentNo();
//            //分配一个！=学号的&&不同于上次分配结果
//            StudentHomework sh = studentAssignments.get(i).get(0);
//            while (allAssignments.get(index).getStudentNo().equals(currentStudentNo) ||
//                    allAssignments.get(index).getStudentNo().equals(sh.getStudentNo())) {
//                index += 1;
//                if (index > allAssignments.size()) {
//                    return Result.success("分配错误");
//                }
//                allAssignments.add(allAssignments.get(index));
//            }
//            StudentHomework assignedHomework = allAssignments.get(index);
//            String assignedStudentNo = assignedHomework.getStudentNo();
//            studentAssignments.get(i).add(assignedHomework);
//            peerReviewAssignmentService.insertPeerReviewAssignment(assignedStudentNo, currentStudentNo, homeworkNo, startTime, endTime);
//            System.out.println("被评作业者："+assignedStudentNo+"评审员："+currentStudentNo);
//        }
//
        int index = 0;
        // 随机选择学生作业
        Random random = new Random();
        Set<String> selectedStudentNo = new HashSet<>();
        int numToSelect = (int) Math.ceil(studentHomeworks.size() * 0.15); // 选择15%

        while (selectedStudentNo.size() < numToSelect) {
            index = random.nextInt(studentHomeworks.size());
            StudentHomework selected = studentHomeworks.get(index);
            String studentNo = selected.getStudentNo();

            if (!selectedStudentNo.contains(studentNo)) {
                selectedStudentNo.add(studentNo);
                studentHomeworkService.updateIsTeacherGrade(true, studentNo, selected.getHomeworkNo());
            }
        }
        return Result.success();
    }


    @GetMapping("/oneHomework")
    public Result selectOneHomework(@RequestParam String homeworkNO) {
        Homework homework = homeworkService.selectByHomeworkNo(homeworkNO);
        Map<String, Object> responseData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        responseData.put("cid", homework.getCid());
        responseData.put("postStatus", homework.getVisible());
        responseData.put("homeworkStartTime", homework.getStartTime().format(formatter));
        responseData.put("homeworkEndTime", homework.getEndTime().format(formatter));
        responseData.put("homeworkDescription", homework.getHomeworkDescription());
        responseData.put("homeworkInfo", homework.getHomeworkInfo());
        responseData.put("file", homework.getHomeworkPath());
        responseData.put("scores", homework.getHomeworkGrade());
        int studentNumbers = studentCourseService.selectByCid(homework.getCid()).size();
        int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
        responseData.put("submitNumbers", submitNumbers);
        responseData.put("studentNumbers", studentNumbers);

        return Result.success(responseData);
    }

    //返回某cid对应所有homework
    @GetMapping("/allCidHomework")
    public Result selectByCid(@RequestParam String cid, HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        if (userNo.startsWith("S")) {
            List<Homework> homeworks = homeworkService.selectByCidAndVisible(cid);
            Map<String, Object> responseData = new HashMap<>();
            int index = 1;
            for (Homework homework : homeworks) {
                Map<String, Object> hw = new HashMap<>();
                hw.put("homeworkNO", homework.getHomeworkNo());
                hw.put("homeworkDescription", homework.getHomeworkDescription());
                hw.put("homeworkStartTime", homework.getStartTime());
                hw.put("homeworkEndTime", homework.getEndTime());

                int studentNumbers = studentCourseService.selectByCid(cid).size();
                int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
                hw.put("submitNumbers", submitNumbers);
                hw.put("studentNumbers", studentNumbers);

                StudentHomework sh = studentHomeworkService.selectByStudentNoAndHomeworkNo(userNo, homework.getHomeworkNo());
                if (sh == null) {
                    hw.put("status", "未提交");
                    hw.put("grade", null);
                } else {
                    hw.put("status", "已提交");
                    hw.put("grade", sh.getSubmitGrade());
                }

                responseData.put("homework" + index, hw);
                index++;
            }
            return Result.success(responseData);
        } else if (userNo.startsWith("T")) {
            List<Homework> homeworks = homeworkService.selectByCid(cid);
            Map<String, Object> responseData = new HashMap<>();
            int index = 1;
            for (Homework homework : homeworks) {
                Map<String, Object> hw = new HashMap<>();
                hw.put("homeworkNO", homework.getHomeworkNo());
                hw.put("homeworkDescription", homework.getHomeworkDescription());
                hw.put("homeworkStartTime", homework.getStartTime());
                hw.put("homeworkEndTime", homework.getEndTime());

                int studentNumbers = studentCourseService.selectByCid(cid).size();
                int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
                hw.put("submitNumbers", submitNumbers);
                hw.put("studentNumbers", studentNumbers);

                int correctingNumber = studentHomeworkService.selectCorrectingHomework(homework.getHomeworkNo()).size();
                System.out.println("C is " + correctingNumber);
                System.out.println("S is " + submitNumbers);
                if (!homework.getVisible()) {
                    hw.put("postStatus", false);
                } else {
                    hw.put("postStatus", true);
                }

                if (correctingNumber == submitNumbers && correctingNumber != 0) {
                    hw.put("correctStatus", true);
                } else {
                    hw.put("correctStatus", false);
                }
                responseData.put("homework" + index, hw);
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
        Map<String, Object> responseData = new HashMap<>();
        for (StudentHomework studentHomework : studentHomeworks) {
            Map<String, Object> sh = new HashMap<>();
            sh.put("StudentNo", studentHomework.getStudentNo());
            sh.put("StudentDescription()", studentHomework.getSubmitDescription());
            sh.put("StudentHomeworkSubmitTime", studentHomework.getSubmitTime());
        }

        return Result.success(responseData);
    }

    @PostMapping(value = "/updateHomework", consumes = "multipart/form-data")
    public Result updateHomework(@RequestParam String newEndTime,
                                 @RequestParam String newStartTime,
                                 @RequestParam String scores,
                                 @RequestParam String homeworkNo,
                                 @RequestParam String homeworkInfo,
                                 @RequestParam String homeworkDesc,
                                 @RequestParam(required = false) MultipartFile file) throws IOException {

        System.out.println(newStartTime);
        System.out.println(newEndTime);
        String startTime;
        String endTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newEndTimes;
        LocalDateTime newStartTimes;

        if (newStartTime.endsWith(")") && newEndTime.endsWith(")")) {
            //都是Zone形式
            startTime = getLocalTime(newStartTime);
            endTime = getLocalTime(newEndTime);
            newStartTimes = LocalDateTime.parse(startTime, formatter);
            newEndTimes = LocalDateTime.parse(endTime, formatter);
        } else if (!newStartTime.endsWith(")") && newEndTime.endsWith(")")) {
            newStartTimes = LocalDateTime.parse(newStartTime, formatter);
            endTime = getLocalTime(newEndTime);
            newEndTimes = LocalDateTime.parse(endTime, formatter);
        } else if (newStartTime.endsWith(")") && !newEndTime.endsWith(")")) {
            startTime = getLocalTime(newStartTime);
            newStartTimes = LocalDateTime.parse(startTime, formatter);
            newEndTimes = LocalDateTime.parse(newEndTime, formatter);
        } else {
            newStartTimes = LocalDateTime.parse(newStartTime, formatter);
            newEndTimes = LocalDateTime.parse(newEndTime, formatter);
        }

        int fixedPoint = Integer.parseInt(scores);
        if (file != null && !file.isEmpty()) {
            String savedPath = uploadFile(file);
            String url = "http://" + ip + ":" + port + "/homework/download/" + savedPath;
            homeworkService.updateHomeworkPath(url, homeworkNo);
        }

        homeworkService.updateHomeworkEndTime(newEndTimes, homeworkNo);
        homeworkService.updateHomeworkDescription(homeworkDesc, homeworkNo);
        homeworkService.updateHomeworkGrade(fixedPoint, homeworkNo);
        homeworkService.updateHomeworkStartTime(newStartTimes, homeworkNo);
        homeworkService.updateHomeworkInfo(homeworkInfo, homeworkNo);
        return Result.success();
    }

    String getLocalTime(String time) {
        // 1. 去掉中文时区名称 "(中国标准时间)"
        String cleanedTime = time.replaceAll("\\(.*\\)", "").trim();  // 去除括号内的中文时区名称

        // 2. 将字符串按空格分隔
        String[] parts = cleanedTime.split("\\s+");

        // 3. 使用switch语句将月份缩写转化为对应的月份数字
        int month = 0;  // 初始化一个变量，默认为0，表示无效月份
        switch (parts[1]) {
            case "Jan":
                month = 1;
                break;
            case "Feb":
                month = 2;
                break;
            case "Mar":
                month = 3;
                break;
            case "Apr":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "Jun":
                month = 6;
                break;
            case "Jul":
                month = 7;
                break;
            case "Aug":
                month = 8;
                break;
            case "Sep":
                month = 9;
                break;
            case "Oct":
                month = 10;
                break;
            case "Nov":
                month = 11;
                break;
            case "Dec":
                month = 12;
                break;
            default:
                System.out.println("Invalid month");
                break;
        }
        return parts[3] + "-" + month + "-" + parts[2] + " " + parts[4];
    }

    //对某student的作业打分
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestBody Map<String, Object> requestData) {
        int submitGrade = Integer.parseInt(requestData.get("grade").toString());
        String studentNo = requestData.get("StudentNo").toString();
        String homeworkNo = requestData.get("HomeworkNo").toString();
        String comment = requestData.get("comment").toString();
        try {
            studentHomeworkMapper.updateStudentHomeworkSubmitGrade(submitGrade, studentNo, homeworkNo, comment);
            return Result.success("成功批改");
        } catch (Exception e) {
            return Result.success("批改失败");
        }

    }

    //-------------------------------作业文件上传下载(教师端）---------------------------------

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("Cid") String Cid,
                         @RequestParam("homeworkDescription") String homeworkDescription,
                         @RequestParam("startTime") String startTime,
                         @RequestParam("endTime") String endTime,
                         @RequestParam("homeworkGrade") int homeworkGrade,
                         @RequestParam("homeworkInfo") String homeworkInfo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newStartTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime newEndTime = LocalDateTime.parse(endTime, formatter);


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
            System.out.println(Cid);
            System.out.println(Cid.length());
            homeworkService.insertHomework(Cid, newHomeworkNo, homeworkDescription, newStartTime, newEndTime, homeworkGrade, homeworkInfo, url);

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
        //File saveFile = new File("D:\\vscode\\BMS\\vite-project\\public\\" + originalFilename);
        File saveFile = new File("D:\\桌面\\课程\\大三上\\综合实训\\BCP\\demo1\\BCP\\files" + originalFilename);
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
