/* eslint-disable @typescript-eslint/no-explicit-any */
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
  NOTIFICATION_ALL = "/notifications/getAllNotification",
  CHANGEPASSWORD = "/users/updatePassword",
  GET_AVATAR = "/users/getPicture",
  UPDATE_AVATAR = "/users/updateAvatar",
  GET_MYNOTELIST = "/note/getStudentAllNote",
  GET_ALLNOTE = "/note/allPublicNote",
  ADD_NOTE = "/note/upload",
  DELETE_NOTE = "/note/deleteNote",
  UPDATE_NOTEINFO = "/note/updateNoteInformation",
  UPDATENOTEVISIBLE = "/note/updateNoteAuthority",
  UPDATENOTEFAVORITE = "/favorite/insertFavoriteOther",
  GET_FAVORITE = "/favorite/selectByStarNoteFromOthers",
  EXPORTSTUDENTLIST = "/StudentCourse/exportStudentList",
  GET_HOMEWORKLIST = "/homework/allCidHomework",
  UPDATE_HOMEWORK_SETTINGS = "/homework/updateHomework",
  EDIT_HOMEWORK = "/homework/oneHomework",
  FINAL_GRADE = "",
  GET_HOMEWORK_EVALUATION_TIME = "1",
  SEND_HOMEWORK = "/homework/updateVisible",
  AI_HELPER = "/ai/aiService"
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
//获取所有通知
export const reqNotificationAll = () => request.get<any, any>(API.NOTIFICATION_ALL)
//获取头像
export const requireAvatar = () => request.get<any, any>(API.GET_AVATAR)
//获取我的笔记
export const requireMyNote = () => request.get<any, any>(API.GET_MYNOTELIST)
//获取公开笔记
export const requireAllNote = () => request.get<any, any>(API.GET_ALLNOTE)
//获取笔记收藏状态
export const reqFavoriteStatus = () => request.get<any, any>(API.GET_FAVORITE)
//导出选课学生名单
export const requireStudentList = (courseId: any) => request.get<any, any>(API.EXPORTSTUDENTLIST + `?cid=${courseId}`)
//得到学生端作业列表
export const requireStudentHomework = (courseId: any) => request.get<any, any>(API.GET_HOMEWORKLIST + `?cid=${courseId}`)
//得到老师端作业布置列表
export const requireTeacherSendHomework = (courseId: any) => request.get<any, any>(API.GET_HOMEWORKLIST + `?cid=${courseId}`)
//得到要编辑的作业
export const editSingleHomework = (homeworkNO: any) => request.get<any, any>(API.EDIT_HOMEWORK + `?homeworkNO=${homeworkNO}`)
//得到最终成绩
export const getFinalHomeworkScore = (homeworkNO: any) => request.get<any, any>(API.FINAL_GRADE + `?homeworkNO=${homeworkNO}`)
//得到互评最终时间
export const getEvaluationEndTime = (homeworkNO: any) => request.get<any, any>(API.GET_HOMEWORK_EVALUATION_TIME + `?homeworkNO=${homeworkNO}`)
// export const awardInfo = () => request.get<any, any>(API.AWARD_GET);


//发布通知
export const postNotification = (data: any) => request.post<any, any>(API.NOTIFICATION_POST, data);
//通知已读
export const updateNotificationState = (data: any) => request.post<any, any>(API.NOTIFICATION_STATECHANGE, data);
//课程资源上传
export const reqUploadFile = (data: any) => request.post<any, any>(API.COURSERESOURCE_UPLOAD, data)
//修改密码
export const reqUpdatePassword = (data: any) => request.post<any, any>(API.CHANGEPASSWORD, data)
//修改头像
export const reqUpdateAvatar = (data: any) => request.post<any, any>(API.UPDATE_AVATAR, data)
//新建笔记
export const reqAddNote = (data: any) => request.post<any, any>(API.ADD_NOTE, data)
//修改笔记收藏状态
export const reqUpdateLike = (data: any) => request.post<any, any>(API.UPDATENOTEFAVORITE, data)
//修改笔记可见状态
export const reqUpdateVisible = (data: any) => request.post<any, any>(API.UPDATENOTEVISIBLE, data)
//修改笔记
export const reqUpdateNote = (data: any) => request.post<any, any>(API.UPDATE_NOTEINFO, data);
//删除笔记
export const reqDeleteNote = (data: any) => request.post<any, any>(API.DELETE_NOTE, data);
//更新作业信息
export const updateHomeworkSetting = (data: any) => request.post<any, any>(API.UPDATE_HOMEWORK_SETTINGS, data);
//发布作业
export const updateHomeworkStatus = (data: any) => request.post<any, any>(API.SEND_HOMEWORK, data);
//AI助手
export const ai_Helper = (data: any) => request.post<any, any>(API.AI_HELPER, data);
// export const awardPost = (data: any) => request.post<any>(API.AWARD_POST, data);