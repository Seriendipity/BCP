package com.example.bcp.controller;

import cn.hutool.core.io.FileUtil;
import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Note;
import com.example.bcp.entity.Result;
import com.example.bcp.service.FavoriteService;
import com.example.bcp.service.NoteService;
import com.example.bcp.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/getStudentAllNote")
    public Result getStudentAllNote(HttpServletRequest request) {
        String userName = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();
        int index = 1;
        List<Note> selectByStudentNo = noteService.selectByStudentNo(userName);
        for(Note n : selectByStudentNo){
            Map<String,Object> note = new HashMap<>();
            note.put("noteNo",n.getNoteNo());
            note.put("notePath",n.getNotePath());
            note.put("noteInfo",n.getNoteInformation());
            note.put("authority",n.isAuthority());
            System.out.println(n.getLoadTime());
            String time = n.getLoadTime()+"";
            String uploadDate = time.replace("T"," ");
            note.put("uploadDate",uploadDate);
            responseData.put("Note"+index,note);
            index++;
        }
        return Result.success(responseData);
    }

    @GetMapping("/allPublicNote")
    public Result getAllPublicNote(HttpServletRequest request) {
        List<Note> notes = noteService.selectAllPublicNote();
        Map<String, Object> responseData = new HashMap<>();
        int index = 1;
        for (Note n : notes) {
            Map<String, Object> note = new HashMap<>();
            note.put("noteNo", n.getNoteNo());
            note.put("username", studentService.selectByStudentNo(n.getStudentNo()).getStudentName());
            note.put("notePath", n.getNotePath());
            note.put("noteInfo", n.getNoteInformation());
            responseData.put("note" + index, note);
            index++;
        }
        return Result.success(responseData);
    }

    @PostMapping("/deleteNote")
    public Result deleteNote(@RequestParam String noteNo) {
        try {
            List<Favorite> favorites = favoriteService.selectByFavoriteInformationNo(noteNo);
           for(Favorite f : favorites){
               favoriteService.deleteFavorite(f.getFavoriteNo(),f.getStudentNo(),noteNo);
           }
            noteService.deleteNote(noteNo);
            return Result.success("删除笔记成功");
        } catch (Exception e) {
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
    public Result updateNoteInformation(
            @RequestParam("noteNo") String noteNo,
            @RequestParam("noteInfo") String noteInformation,
            @RequestParam("notePath") MultipartFile notePath) throws IOException {
        try {
            System.out.println(noteInformation);
            // 保存文件，获取文件路径
            String filePath = uploadFile(notePath);  // 自定义文件保存方法
            String url = "http://" + ip + ":" + port + "/note/download/" + filePath;
            // 更新笔记信息
            noteService.updateNoteInformation(noteInformation, noteNo);
            noteService.updateNotePath(url, noteNo);  // 保存文件路径

            return Result.success("修改笔记信息成功");
        } catch (Exception e) {
            return Result.error("修改失败");
        }
    }


    @PostMapping("/updateNoteAuthority")
    public Result updateNoteAuthority(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        String noteNo = requestData.get("noteNo");

        Note n = noteService.selectByNoteNo(noteNo);
        try {
            noteService.updateNoteAuthority(noteNo, !n.isAuthority());
            return Result.success("更改成功");
        } catch (Exception e) {
            return Result.error("更改笔记公开信息失败");
        }
    }

    //-------------------------------笔记文件上传下载---------------------------------

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("noteInfo") String noteInformation,
                         HttpServletRequest request) {
        System.out.println(noteInformation);
        String studentNo = request.getAttribute("username").toString();
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用文件上传逻辑
            String savedFilePath = uploadFile(file);
            // 获取新文件的 ID（这里假设你有一个方法获取最大 ID）
//            int newId = courseResourceService.maxNo() + 1;

            int size = noteService.selectAllNote().size() + 1;
            String newNoteNo = "N" + size;
            while (noteService.selectByNoteNo(newNoteNo)!= null){
                size++;
                newNoteNo = "N"+size;
            }


            // 插入文件记录到数据库
//    public void insertNote(String NoteNo, String StudentNo, String NoteInformation,
//                           String NotePath , boolean Authority)
            String url = "http://" + ip + ":" + port + "/note/download/" + savedFilePath;
            noteService.insertNote(newNoteNo, studentNo, noteInformation, url, false);

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
            originalFilename = noteService.selectAllNote().size() + 1 + "." + extName;
        }

        // 保存文件到本地
        //File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        File saveFile = new File("D:\\vscode\\BMS\\vite-project\\public\\"+ originalFilename);
        System.out.println("文件的保存路径：" + saveFile.getAbsolutePath());
        System.out.println("----------");
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
