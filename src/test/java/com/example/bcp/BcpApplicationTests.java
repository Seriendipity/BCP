package com.example.bcp;


import com.example.bcp.entity.*;
import com.example.bcp.entity.Class;
import com.example.bcp.mapper.CourseResourceMapper;
import com.example.bcp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//        List<Student> students = studentService.selectAllStudent();
        List<Student> students = new ArrayList<>();
        studentService.updateStudentPassword("aaaaaaa" , "S001");
        students.add(studentService.selectByStudentNo("S001"));
        for(Student s : students){
            System.out.print(s.getStudentNo() + " ");
            System.out.print(s.getStudentName()+" ");
            System.out.print(s.getTotalCredit()+" ");
            System.out.print(s.getPassword()+" ");
            System.out.println();
        }

    }

    @Test
    void TestTeacher(){
//        List<Teacher> teachers = teacherService.selectAllTeacher();
        List<Teacher> teachers = new ArrayList<>();
        teacherService.updateTeacherTitle("教授","T001");
        teachers.add(teacherService.selectByTeacherNo("T001"));
        for(Teacher t : teachers){
            System.out.print(t.getTeacherNo()+" ");
            System.out.print(t.getTeacherName()+" ");
            System.out.print(t.getTeacherTitle()+" ");
            System.out.print(t.getTeacherBirthday()+" ");
            System.out.print(t.getPassword()+" ");
            System.out.println();
        }
    }

    @Test
    void TestCourse(){
       // List<Course> courses = courseService.selectAllCourse();
        List<Course> courses = new ArrayList<>();
        courses.add(courseService.selectByCourseNo("CS101"));
        for(Course c :courses){
            System.out.print(c.getCourseNo() + " ");
            System.out.print(c.getCourseName()+ " ");
            System.out.print(c.getCourseCredit()+ " ");
            System.out.println();
        }
    }

    @Test
    void TestTeaching(){
//     List<Teaching>  teachings = teachingService.selectAllTeaching();
       // List<Teaching> teachings = teachingService.selectByTeacherNoAndCourseNo("T001","CS101");
       List<Teaching> teachings = new ArrayList<>();
       teachings.add(teachingService.selectByCid("T001_CS101"));
        for(Teaching t : teachings){
            System.out.print(t.getCourseNo()+" ");
            System.out.print(t.getTeacherNo()+" ");
            System.out.print(t.getSyllabus()+" ");
            System.out.print(t.getCid()+" ");
            System.out.print(t.getTeachingCalendar()+" ");
            System.out.print(t.getLanguage()+" ");
            System.out.print(t.getSemester()+" ");
            System.out.println();
        }
    }

    @Test
    void TestSC(){
        List<StudentCourse> studentCourses = studentCourseService.selectByCid("T001_CS101");
        for(StudentCourse sc: studentCourses){
            System.out.print(sc.getStudentNo()+" ");
            System.out.print(sc.getCid()+" ");
            System.out.print(sc.getGrade()+" ");
            System.out.println();
        }
    }

    @Test
    void TestDiscussion(){
//        discussionService.insertDiscussion("S003","T001_CS101","D003","test", LocalDateTime.now());
        discussionService.deleteDiscussion("D003");
        List<Discussion> discussions = discussionService.selectAllDiscussion();
        for(Discussion d : discussions){
            System.out.print(d.getCid() + " ");
            System.out.print(d.getDiscussionId()+" ");
            System.out.print(d.getDiscussionInformation()+" ");
            System.out.print(d.getDiscussionPostingTime()+" ");
            System.out.print(d.getStudentNo()+" ");
            System.out.println();
        }
    }
    @Autowired
    private CommentService commentService;
    @Test
    void TestComment(){
//        commentService.deleteComment("C003");
//        List<Comment> comments = commentService.selectAllComments();
        List<Comment> comments = commentService.selectByDiscussionId("D001");
        for(Comment c : comments){
            System.out.print(c.getCommentId()+" ");
            System.out.print(c.getDiscussionId()+" ");
            System.out.print(c.getCommentInformation()+" ");
            System.out.print(c.getCommentPostingTime()+" ");
            System.out.print(c.getLikesNumber()+" ");
            System.out.println();
        }
    }

    @Autowired
    private NoteService noteService;
    @Test
    void TestNote(){
//        List<Note> notes = noteService.selectByStudentNo("S001");

//        noteService.updateNoteAuthority("N001",true);
        noteService.updateNoteInformation("Test","N001");
        List<Note> notes = noteService.selectAllNote();

        for(Note n : notes){
            System.out.print(n.getNoteNo()+" ");
            System.out.print(n.getNoteInformation()+" ");
            System.out.print(n.getStudentNo()+" ");
            System.out.print(n.getNotePath()+" ");
            System.out.print(n.isAuthority()+" ");
            System.out.println();
        }
    }

    @Autowired
    private FavoriteService favoriteService;
    @Test
    void TestFavorite(){
//        favoriteService.insertFavorite("F003","S003","Test","Study");
//        favoriteService.deleteFavorite("F003","S003");
        favoriteService.updateFavoriteTitle("Test","S001","F001");
        List<Favorite> favorites = favoriteService.selectAllFavorite();
        for(Favorite f : favorites){
            System.out.print(f.getFavoriteNo()+" ");
            System.out.print(f.getFavoriteInformationNO()+" ");
            System.out.print(f.getFavoriteTitle()+" ");
            System.out.print(f.getStudentNo()+" ");
            System.out.println();
        }
    }

    @Autowired
    private AssistantService assistantService;
    @Test
    void TestAssistant(){
//        List<Assistant> assistants = assistantService.selectByCid("T001_CS101");
//        assistantService.insertAssistant("A003","T001_CS101");
        assistantService.deleteAssistant("A003");
        List<Assistant> assistants = assistantService.selectAllAssistant();
        for(Assistant a : assistants){
            System.out.print(a.getAssistantNo()+" ");
            System.out.print(a.getCid()+" ");
            System.out.println();
        }
    }

    @Autowired
    private NotificationService notificationService;
    @Test
    void TestNotification(){
       notificationService.deleteNotification("N003");
        List<Notification> notifications = notificationService.selectAllNotification();
//        List<Notification> notifications = new ArrayList<>();
//         notifications.add(notificationService.selectByNotificationNo("N002"));
        for(Notification n : notifications){
            System.out.print(n.getCid()+" ");
            System.out.print(n.getNotificationNo()+" ");
            System.out.print(n.getSendNo()+" ");
            System.out.print(n.getNotificationInformation());
            System.out.println();
        }
    }

    @Autowired
    private StudentNotificationService studentNotificationService;
    @Test
    void TestStudentNotification(){
        studentNotificationService.deleteStudentNotification("S001","N001");
        List<StudentNotification> sns = studentNotificationService.selectAllStudentNotification();
        for(StudentNotification sn : sns){
            System.out.print(sn.getNotificationNo()+" ");
            System.out.print(sn.getStudentNo()+" ");
            System.out.print(sn.getNotificationState()+" ");
            System.out.print(sn.getNotificationDate()+" ");
            System.out.println();
        }
    }

    @Autowired
    private CourseResourceService crs;
    @Test
    void TestCourseResource(){
//        crs.insertCourseResource("R003","T001_CS101","PPT","test");
//        crs.updateCourseResource("I love you","R003");
        crs.deleteCourseResource("R003");
        List<CourseResource> courseResources = crs.selectAllCourseResource();
        for(CourseResource cr: courseResources){
            System.out.print(cr.getCid()+" ");
            System.out.print(cr.getCourseResourceNo()+" ");
            System.out.print(cr.getCourseResourcePath()+" ");
            System.out.print(cr.getResourceType()+" ");
            System.out.println();
        }
    }

    @Autowired
    private HomeworkService homeworkService;
    @Test
    void TestHomework(){
//        homeworkService.insertHomework("H003","T001_CS101","Test",LocalDateTime.now(),LocalDateTime.now(),100);
//        homeworkService.updateHomeworkEndTime(LocalDateTime.parse("2024-11-11T11:11:11"),"H003");
        homeworkService.deleteHomework("H003");
        List<Homework> homeworks = homeworkService.selectAllHomework();
        for(Homework h : homeworks){
            System.out.print(h.getCid()+" ");
            System.out.print(h.getHomeworkNo()+" ");
            System.out.print(h.getHomeworkDescription()+" ");
            System.out.print(h.getHomeworkGrade()+" ");
            System.out.print(h.getStartTime()+" ");
            System.out.print(h.getEndTime()+" ");
            System.out.println();
        }
    }

    @Autowired
    private StudentHomeworkService studentHomeworkService;
    @Test
    void TestStudentHomework(){
//        studentHomeworkService.insertStudentHomework("S001","H002","Test","/test",LocalDateTime.now(),100);
//        studentHomeworkService.updateStudentHomeworkPath("/tttttt","S001","H002");
//        studentHomeworkService.updateStudentHomeworkSubmitGrade(60,"S001","H002");
        List<StudentHomework> studentHomeworks =studentHomeworkService.selectAllStudentHomework();
        for(StudentHomework h : studentHomeworks){
            System.out.print(h.getHomeworkNo()+" ");
            System.out.print(h.getStudentNo()+" ");
            System.out.print(h.getSubmitPath()+" ");
            System.out.print(h.getSubmitGrade()+" ");
            System.out.print(h.getSubmitDescription()+" ");
            System.out.print(h.getSubmitTime()+" ");
            System.out.println();
        }
    }
}
