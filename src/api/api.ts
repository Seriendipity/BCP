//用户相关接口
import request from '@/utils/request'


//请求地址
enum API {
  LOGIN_URL = "/login",
  STUDENTINFO_URL = "/users/userInfo",
  COURSELIST = "/StudentCourse/allCourse",
  COURSEINTRO = "/teaching/getCourseInfo",
  TEACHERINFO = "/teaching/getTeacherInfo",
  CALENDAR_URL = "teaching/getCalendar",
  SYLLABUS_URL = "teaching/getSyllabus",


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
// export const awardInfo = () => request.get<any, any>(API.AWARD_GET);
// export const awardPost = (data: any) => request.post<any>(API.AWARD_POST, data);