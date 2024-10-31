//用户相关接口
import request from '@/utils/request'


//请求地址
enum API {
  LOGIN_URL = "/login",
  STUDENTINFO_URL = "/users/userInfo",
  COURSELIST = "/StudentCourse/allCourse",
  COURSEINTRO = "/teaching/getCourseInfo",
  TEACHERINFO = "/teaching/getTeacherInfo",
  CALENDAR_URL = "/teaching/getCalendar",
  SYLLABUS_URL = "/teaching/getSyllabus",
  NOTIFICATION_STUDENT = "/notifications/getNotification",
  STUDENT_DATA = "/StudentCourse/allStudent",
  NOTIFICATION_POST = "/notifications/send",
  NOTIFICATION_STATECHANGE = "/notifications/change_status",
  COURSERESOURCE_UPLOAD = "/file/upload",
  COURSERESOURCE_GET = "/file/allCourseSource",

}
//登录接口
export const reqLogin = (data: any) => request.post<any, any>(API.LOGIN_URL, data);
//获取学生信息
export const reqUserInfo = () => request.get<any, any>(API.STUDENTINFO_URL);
//获取课程列表
export const reqCourseList = () => request.get<any, any>(API.COURSELIST);
//获取课程介绍
export const reqCourseIntro = (courseId: any) => request.get<any, any>(API.COURSEINTRO + `?cid=${courseId}`)
//获取教师信息
export const reqTeacherInfo = (courseId: any) => request.get<any, any>(API.TEACHERINFO + `?cid=${courseId}`)
//获取教学日历
export const reqCalendar = (courseId: any) => request.get<any, any>(API.CALENDAR_URL + `?cid=${courseId}`)
//获取教学大纲
export const reqSyllabus = (courseId: any) => request.get<any, any>(API.SYLLABUS_URL + `?cid=${courseId}`)
//获取学生通知
export const reqNotificationStudent = (courseId: any) => request.get<any, any>(API.NOTIFICATION_STUDENT + `?cid=${courseId}`)
//获取学生选课列表
export const reqStudentData = (courseId: any) => request.get<any, any>(API.STUDENT_DATA + `?cid=${courseId}`)
//获取课程资源
export const reqFileList = (courseId: any) => request.get<any, any>(API.COURSERESOURCE_GET + `?cid=${courseId}`)
// export const awardInfo = () => request.get<any, any>(API.AWARD_GET);

//发布通知
export const postNotification = (data: any) => request.post<any, any>(API.NOTIFICATION_POST, data);
//通知已读
export const updateNotificationState = (data: any) => request.post<any, any>(API.NOTIFICATION_STATECHANGE, data);
//课程资源上传
export const reqUploadFile = (data: any) => request.post<any, any>(API.COURSERESOURCE_UPLOAD, data)
// export const awardPost = (data: any) => request.post<any>(API.AWARD_POST, data);