import request from '@/utils/request'

enum API {

  COURSEINFO = ""

}

export const reqCourseInfo = () => request.get<any, any>(API.COURSEINFO);