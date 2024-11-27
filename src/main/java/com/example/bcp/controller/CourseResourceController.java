package com.example.bcp.controller;

import com.example.bcp.entity.CourseResource;
import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Result;
import cn.hutool.core.io.FileUtil;
import com.example.bcp.service.CourseResourceService;
import com.example.bcp.service.CourseService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class CourseResourceController {
    @Autowired
    private CourseResourceService courseResourceService;

    @GetMapping("/allCourseSource")
    public Result selectAllCourseResource(@RequestParam String cid){
        List<CourseResource> selectByCid = courseResourceService.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        int index = 1;
        for(CourseResource cr : selectByCid){
            Map<String,Object> courseResource = new HashMap<>();
            String name = cr.getCourseResourcePath();
            List<String> list = new ArrayList<>(Arrays.asList(name.split("/")));
            String filename = list.get(list.size()-1);
            courseResource.put("name",filename);
            courseResource.put("index",index);
            courseResource.put("url",cr.getCourseResourcePath());
            courseResource.put("type",cr.getResourceType());
            courseResource.put("courseResourceNo",cr.getCourseResourceNo());
            responseData.put("CourseResource"+index,courseResource);
            index++;
        }
        return Result.success(responseData);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String courseResourceNo){
        try{
            courseResourceService.deleteCourseResource(courseResourceNo);
            return Result.success("删除成功");
        }catch (Exception e){
            return Result.error("删除失败");
        }
    }



    //-------------------------------文件上传下载---------------------------------

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("cid") String Cid) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用文件上传逻辑
            String savedFilePath = uploadFile(file);
            // 获取新文件的 ID（这里假设你有一个方法获取最大 ID）
//            int newId = courseResourceService.maxNo() + 1;

            int size = courseResourceService.selectAllCourseResource().size() + 1;
            String newCId = "R" + size;
            while (courseResourceService.selectByCid(newCId) != null){
                size++;
                newCId = "R"+size;
            }

            // 插入文件记录到数据库
            String url = "http://" + ip + ":" + port + "/file/download/" + savedFilePath;
            courseResourceService.insertCourseResource(newCId, Cid, FileUtil.extName(file.getOriginalFilename()), url);

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
            originalFilename = courseResourceService.selectAllCourseResource().size() + 1 + "." + extName;
        }

        // 保存文件到本地
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        System.out.println("文件的保存路径：" + saveFile.getAbsolutePath());
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去

        // 返回保存的文件名
        return originalFilename;
    }


    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable ("fileName") String fileName, HttpServletResponse response) throws IOException {

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