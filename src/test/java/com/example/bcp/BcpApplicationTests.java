package com.example.bcp;


import com.example.bcp.entity.*;
import com.example.bcp.entity.Class;
import com.example.bcp.mapper.CourseResourceMapper;
import com.example.bcp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class BcpApplicationTests {

    @Autowired
    private ClassService classService; // 注入 ClassService
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeachingService teachingService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private DiscussionService discussionService;
    @Test
    void contextLoads() {
//        List<Class> classes = classService.selectAllClass();
        classService.updateStudentNumber("C002" , 30);
        List<Class> classes = new ArrayList<>();
        classes.add(classService.selectByClassNo("C002"));
        for (Class aClass : classes) {
            System.out.print(aClass.getClassNo()+" ");
            System.out.print(aClass.getClassName()+" ");
            System.out.print(aClass.getClassMajor()+" ");
            System.out.print(aClass.getStudentNumber()+" ");
            System.out.print(aClass.getClassDepartment()+" ");
            System.out.println();
        }
    }
    @Test
    void TestStudent(){
        studentService.updateStudentPassword("password123" , "S001");
        studentService.updateStudentPicturePath("http://localhost:8081/file/download/test.jpg","S001");
        studentService.selectByStudentNo("S001");
    }

    @Test
    void TestTeacher(){
        teacherService.selectByTeacherNo("T001");
        teacherService.updateTeacherPassword("password123","T001");
        teacherService.updateTeacherPicturePath("/head_portait/T001.jpg","T001");
    }

    @Test
    void TestCourse(){
        courseService.selectByCourseNo("CS101");
    }

    @Test
    void TestTeaching(){
        teachingService.selectByTeacherNo("T001");
        teachingService.selectByCid("T001_CS101");
    }

    @Test
    void TestSC(){
        studentCourseService.selectAllStudentNo("T001_CS101");
        studentCourseService.selectByStudentNo("S001");
        studentCourseService.selectByCid("T001_CS101");
    }

    @Test
    void TestDiscussion(){
        discussionService.selectAllDiscussion();
        discussionService.selectByStudentNo("S001");
        discussionService.selectByDiscussionId("D001");
        discussionService.selectByCid("T001_CS101");
        discussionService.selectByCidAndMentioned("S001","T001_CS101");
        discussionService.insertDiscussion("S001","T001_CS101","D100","test",LocalDateTime.now(),"S002","test","test");
        discussionService.updateDiscussionInfo("test","D100");
        discussionService.selectByCidAndTopic("test","T001_CS101");
        discussionService.selectByCidAndMentioned("S001","T001_CS101");
        discussionService.selectByMentioned("S001");
        discussionService.increaseStarTimes("D100");
        discussionService.deleteDiscussion("D100");
    }
    @Autowired
    private CommentService commentService;
    @Test
    void TestComment(){
        commentService.selectAllComments();
        commentService.selectByDiscussionId("D002");
        commentService.selectByCommentId("C001");
        commentService.insertComment("C100","D002","test",0,LocalDateTime.now(),"test","S002","S001");
        commentService.commentTimes("D002");
        commentService.updateCommentLikes(1,"C100");
        commentService.updateCommentInfo("test","C100");
        commentService.deleteComment("C100");

    }

    @Autowired
    private NoteService noteService;
    @Test
    void TestNote(){
        noteService.selectAllNote();
        noteService.selectByStudentNo("S001");
        noteService.selectByNoteNo("N3");
        noteService.selectAllPublicNote();
        noteService.insertNote("N001","S001","Test","test",false);
        noteService.updateNoteAuthority("N001",true);
        noteService.updateNotePath("test","N001");
        noteService.updateNoteInformation("test","N001");
        noteService.deleteNote("N001");
    }

    @Autowired
    private FavoriteService favoriteService;
    @Test
    void TestFavorite(){
            favoriteService.selectByStudentOthers("S001");
            favoriteService.selectByStudentNoAndFavoriteNoAndFavoriteInformationNo("S001","F001","D002");
            favoriteService.selectByStudentNo("S001");
            favoriteService.selectByStudentNoAndFavoriteTitle("S001","学习");
            favoriteService.selectByStudentNoAndFavoriteNo("S001","F001");
            favoriteService.insertFavoriteOwn("F100","S001","D002","tttt",0,"S001");
            favoriteService.insertFavoriteOthers("F101","S002","D002","test","S001");
            favoriteService.updateFavoriteTitle("test","S001","F100","D002");
            favoriteService.updateFavoriteAuthority("S001","F100","test");
            favoriteService.deleteFavorite("F100","S001","D002");
            favoriteService.deleteFavorite("F101","S002","D002");
        }


    @Autowired
    private AssistantService assistantService;
    @Test
    void TestAssistant(){
        assistantService.selectByAssistantNo("A001");
        assistantService.updateAssistantPassword("password123","A001");
        assistantService.updateAssistantPicturePath("test","A001");
    }

    @Autowired
    private NotificationService notificationService;
    @Test
    void TestNotification(){
        notificationService.selectAllNotification();
        notificationService.selectByCid("T001_CS101");
        notificationService.selectBySendNo("T001");
        notificationService.selectByNotificationNo("N001");
        notificationService.insertNotification("N100","T001_CS101","T001","test","test");
        notificationService.updateNotificationInformation("N100","test","test");
        notificationService.deleteNotification("N100");
    }

    @Autowired
    private StudentNotificationService studentNotificationService;
    @Test
    void TestStudentNotification(){
        studentNotificationService.selectByStudentNo("S001");
        studentNotificationService.selectByStudentNoAndNotificationNo("S001","N001");
        studentNotificationService.insertStudentNotification("S002","N7");
        studentNotificationService.updateStudentNotificationState("S002","N7");
        studentNotificationService.deleteStudentNotification("S002","N7");
    }

    @Autowired
    private CourseResourceService crs;
    @Test
    void TestCourseResource(){
        crs.selectAllCourseResource();
        crs.selectByCid("T001_CS101");
        crs.insertCourseResource("R100","T001_CS101","test","test");
        crs.deleteCourseResource("R100");
    }

    @Autowired
    private HomeworkService homeworkService;
    @Test
    void TestHomework(){
        homeworkService.selectAllHomework();
        homeworkService.selectByHomeworkNo("H001");
        homeworkService.selectByCid("T001_CS101");
        homeworkService.selectByCidAndVisible("T001_CS101");
        homeworkService.insertHomework("T001_CS101","H100","test",LocalDateTime.now(),LocalDateTime.now(),100,"test","test");
        homeworkService.updateHomeworkEndTime(LocalDateTime.now(),"H100");
        homeworkService.updateHomeworkStartTime(LocalDateTime.now(),"H100");
        homeworkService.updateHomeworkInfo("test","H100");
        homeworkService.updateHomeworkDescription("test","H100");
        homeworkService.updateHomeworkPath("test","H100");
        homeworkService.updateHomeworkIsVisible("H100");
        homeworkService.updateHomeworkGrade(30,"H100");
        homeworkService.updateIsPeerReview(true,"H100");
        homeworkService.deleteHomework("H100");
    }

    @Autowired
    private StudentHomeworkService studentHomeworkService;
    @Test
    void TestStudentHomework(){
        studentHomeworkService.selectAllStudentHomework();
        studentHomeworkService.selectByHomeworkNo("H001");
        studentHomeworkService.selectCorrectingHomework("H001");
        studentHomeworkService.selectByStudentNoAndHomeworkNo("S001","H001");
        studentHomeworkService.insertStudentHomework("S001","H6","test","test",LocalDateTime.now(),"test");
        studentHomeworkService.updateStudentHomeworkPath("test","S001","H6");
        studentHomeworkService.updateIsTeacherGrade(true,"S001","H6");
        studentHomeworkService.updateVisible("H6");
        studentHomeworkService.selectByIsTeacherAndHomeworkNo("H6");
        studentHomeworkService.updateStudentHomeworkSubmitGrade(20,"S001","H6","test");
    }
}
