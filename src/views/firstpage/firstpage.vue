<template>
  <el-container>
    <el-header class="head">
      <el-row :gutter="20">
        <el-col :span="21">
          <div class="grid-content ">
            <h1 class="zitihead">智慧课程平台</h1>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/myinformation" style="text-decoration: none;"></router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">{{ student.studentName }}
            </h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <el-container>
        <el-aside class="backleft">
          <div class="whiteback" :data="student">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
            <h1 class="ziti01">学生</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ student.studentName }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ student.studentNo }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ student.dept }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ student.email }}</h1>
          </div>
          <div class="whiteback2">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">课程提醒</h1>
          </div>
        </el-aside>

        <el-main height="600px">
          <h1 style="text-align: left; font-weight: bold;margin-bottom: 10px;font-size: large;margin-top:-12px;">课程列表
          </h1>
          <div class="scrollable">
            <el-row :gutter="20">
              <el-col :span="8" v-for="course in courses" :key="course.cid">
                <div class="grid-content bg-white" @click="goToCourseInfo(course.cid)">
                  <h1 class="ziti03">{{ course.courseName }}</h1>
                  <h1 class="ziti04">课程号: {{ course.courseNo }}</h1>
                  <h1 class="ziti04">课序号: {{ course.cid }}</h1>
                  <div class="avatar-container">
                    <el-image style="width: 94%; height: 100%;margin-top: 10%;margin-left:3%" :src="course.picture"
                      :fit="fit"></el-image>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-main>

        <el-aside class="backright">
          <div class="whiteback3">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">通知公告</h1>
          </div>
        </el-aside>
      </el-container>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqUserInfo, reqCourseList, reqCourseIntro } from '@/api/api';
import { ElNotification } from 'element-plus';

const courses = ref([]);
const student = ref([]);

onMounted(async () => {
  try {
    const userResponse = await reqUserInfo();
    const courseResponse = await reqCourseList();
    student.value = userResponse.data;
    courses.value = courseResponse.data;
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取信息失败'
    });
  }
});

const goToCourseInfo = async (courseId) => {
  try {
    const response = await reqCourseIntro(courseId);
    if (response && response.data) {
      // 跳转到CourseInfo页面，并传递课程信息
      this.$router.push({
        path: '/CourseInfo',
        query: {
          courseId,
          courseIntro: response.data.courseInfo,
          courseName: response.data.courseName,
          courseNo: response.data.courseNo,
          teacherName: response.data.teacherName,
          establishCollege: response.data.establishCollege,
          semester: response.data.semester,
        }
      });
    }
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取课程信息失败'
    });
  }
};
</script>

<style scoped>
.header {
  position: relative;
  background: url(head-background.png) no-repeat;
  background-size: cover;
  box-shadow: --el-box-shadow-light;
}

.common-layout .el-aside {
  color: var(--el-text-color-primary);
  background-color: #005bac;
  height: calc(100vh - 64px);
}

.common-layout .el-menu {
  border-right: none;
}

.common-layout .el-main {
  padding: 20px;
  background-color: #E5EAF3;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.layout_tabbar {
  width: 1240px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #ffffff;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.layout_main {
  padding: 16px;
}

.scrollbar {
  height: 100%;
}

.layout_slidder {
  display: flex;
  flex-direction: column;
  height: 100%;
  font-weight: bold;
}

.lesson-name {
  font-family: "宋体";
  color: #fafafa;
}

.school-text {
  font-family: "宋体";
  color: #fafafa;
}

.teacher-number {
  font-family: "宋体";
  color: #fafafa;
}

.semester {
  font-family: "宋体";
  color: #fafafa;
}
</style>
