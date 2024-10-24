//用户相关接口
import request from '@/utils/request'


//请求地址
enum API {
  LOGIN_URL = "/login",
  STUDENTINFO_URL = "/users/userInfo",
  COURSEINFO = "/StudentCourse/allCourse"

}
//登录接口
export const reqLogin = (data: any) => request.post<any, any>(API.LOGIN_URL, data);
//获取学生信息
export const reqUserInfo = () => request.get<any, any>(API.STUDENTINFO_URL);
//获取课程列表
export const reqCourseInfo = () => request.get<any, any>(API.COURSEINFO);
// export const awardInfo = () => request.get<any, any>(API.AWARD_GET);
// export const awardPost = (data: any) => request.post<any>(API.AWARD_POST, data);