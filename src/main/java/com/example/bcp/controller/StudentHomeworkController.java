package com.example.bcp.controller;

import cn.hutool.core.io.FileUtil;
import com.example.bcp.entity.Result;
import com.example.bcp.service.StudentHomeworkService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("studentHomework")
public class StudentHomeworkController {

    //-------------------------------作业文件上传下载(学生端）---------------------------------

    @Autowired
    private StudentHomeworkService studentHomeworkService;

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("studentNo") String studentNo,
                         @RequestParam("homeworkNo") String homeworkNo,
                         @RequestParam("submitDescription") String submitDescription,
                         @RequestParam("submitTime") String submitTime,
                         @RequestParam("comment") String comment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newSubmitTime = LocalDateTime.parse(submitTime, formatter);

        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用文件上传逻辑
            String savedFilePath = uploadFile(file);
            // 获取新文件的 ID（这里假设你有一个方法获取最大 ID）
//            int newId = courseResourceService.maxNo() + 1;
            int size = studentHomeworkService.selectAllStudentHomework().size() + 1;
            String newHomeworkNo = "H" + size;


            // 插入文件记录到数据库
            //public void insertStudentHomework(String StudentNo, String HomeworkNo, String SubmitDescription,
            //      String SubmitPath , LocalDateTime SubmitTime,String Comment)
            String url = "http://" + ip + ":" + port + "/homework/download/" + savedFilePath;
            studentHomeworkService.insertStudentHomework(studentNo, homeworkNo, submitDescription, url, newSubmitTime, comment);

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
            originalFilename = studentHomeworkService.selectAllStudentHomework().size() + 1 + "." + extName;
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
