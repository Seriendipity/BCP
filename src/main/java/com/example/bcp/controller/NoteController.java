package com.example.bcp.controller;

import cn.hutool.core.io.FileUtil;
import com.example.bcp.entity.Note;
import com.example.bcp.entity.Result;
import com.example.bcp.service.NoteService;
import com.example.bcp.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    StudentService studentService;


    @GetMapping("/getStudentAllNote")
    public Result getStudentAllNote(HttpServletRequest request) {
        String userName = request.getAttribute("username").toString();
        Map<String,Object> responseData = new HashMap<>();
        int index =1 ;
        List<Note> selectByStudentNo = noteService.selectByStudentNo(userName);
        for(Note n : selectByStudentNo){
            Map<String,Object> note = new HashMap<>();
            note.put("noteNo",n.getStudentNo());
            note.put("notePath",n.getNotePath());
            note.put("noteInfo",n.getNoteInformation());
            note.put("authority",n.isAuthority());
            responseData.put("Note"+index,note);
            index++;
        }
        return Result.success(responseData);
    }

    @GetMapping("/allPublicNote")
    public Result getAllPublicNote(HttpServletRequest request){
        List<Note> notes = noteService.selectAllPublicNote();
        Map<String,Object> responseData = new HashMap<>();
        int index =1 ;
        for(Note n : notes){
            Map<String,Object> note = new HashMap<>();
            note.put("noteNo",n.getNoteNo());
            note.put("username",studentService.selectByStudentNo(n.getStudentNo()).getStudentName());
            note.put("notePath",n.getNotePath());
            note.put("noteInfo",n.getNoteInformation());
            responseData.put("note"+index,note);
            index++;
        }
        return Result.success(responseData);
    }
    @PostMapping("/deleteNote")
    public Result deleteNote(@RequestBody Map<String, String> requestData) {
        String noteNo = requestData.get("noteNo");
        try{
            noteService.deleteNote(noteNo);
            return Result.success("删除笔记成功");
        }catch (Exception e){
            return Result.error("删除笔记失败");
        }

    }

//    @PostMapping("/updateNoteAuthority")
//    public Result updateNoteAuthority(@RequestBody Map<String, Object> requestData) {
//        String noteNo = (String) requestData.get("NoteNo");
//        boolean authority = (Boolean) requestData.get("authority");
//
//        noteService.updateNoteAuthority(noteNo, authority);
//        return Result.success();
//    }


    @PostMapping("/updateNoteInformation")
    public Result updateNoteInformation(@RequestBody Map<String, String> requestData) {
        String noteNo = requestData.get("noteNo");
        String noteInformation = requestData.get("noteInformation");
        String notePath = requestData.get("notePath");
        try{
            noteService.updateNoteInformation(noteInformation, noteNo);
            noteService.updateNotePath(notePath,noteNo);
            return Result.success("修改笔记信息成功");
        }catch (Exception e){
            return Result.error("修改失败");
        }
    }


    @PostMapping("/updateNoteAuthority")
    public Result updateNoteAuthority(@RequestBody Map<String,String> requestData, HttpServletRequest request){
        String userNo = request.getAttribute("username").toString();
        String noteNo = requestData.get("noteNo");

        Note n = noteService.selectByNoteNo(noteNo);
        try {
            noteService.updateNoteAuthority(noteNo,!n.isAuthority());
            return Result.success("更改成功");
        }catch (Exception e){
            return Result.error("更改笔记公开信息失败");
        }
    }

    //---------------------------笔记上传--------------------------------------------

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestPart("studentNo") String studentNo,
                         @RequestPart("information") String information,
                         @RequestPart("authority") boolean authority) {

        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // 获取文件主名称aaa
        String extName = FileUtil.extName(originalFilename); // 获取文件扩展名png
        System.out.println("文件的原始名称：" + originalFilename);
        System.out.println("文件的主名称：" + mainName);
        try {

            if (!FileUtil.exist(ROOT_PATH)) {
                FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
            }

            int newId = noteService.maxNo() + 1;
            if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
                // 如果当前上传的文件已经存在了，那么这个时候就要重名一个文件名称
                originalFilename = newId + "." + extName;
            }

            File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
            System.out.println("文件的保存路径：" + saveFile.getAbsolutePath());
            file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
            String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;


            noteService.insertNote(Integer.toString(newId), studentNo, information, url, authority);

            return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址是后台提供出来的

            //另外一种思路
//            file.transferTo(new File(UPLOAD_DIR + fileName));
//            // 返回文件的下载链接
//            String fileDownloadUri = "/file/download?fileName=" + fileName;
//            return javax.xml.transform.Result.success(fileDownloadUri);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }
    }

}
