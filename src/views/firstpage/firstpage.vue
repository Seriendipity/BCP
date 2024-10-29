<template>
  <el-container>
    <el-header class="head">
      <el-row :gutter="20">
        <el-col :span="18">
          <div class="grid-content">
            <h1 class="zitihead">智慧课程平台</h1>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/notes" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">笔记
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content ">
            <router-link to="/bookmarks" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">收藏夹
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/myinformation" style="text-decoration: none;">
              <div style="margin-top: 10px"><el-avatar :size="40" :src="circleUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">爱学习
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
            <div style="margin-top: 20px;text-align: center; "><el-avatar :size="100" :src="circleUrl"></el-avatar>
            </div>
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
import { useRouter } from 'vue-router';
import { reqUserInfo, reqCourseList, reqCourseIntro } from '@/api/api';
import { ElNotification } from 'element-plus';

const courses = ref([]);
const student = ref([]);
const router = useRouter();


const circleUrl = ref('src/assets/images/example.jpg');

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const userResponse = await reqUserInfo();
    const courseResponse = await reqCourseList();
    student.value = userResponse.data;
    courses.value = courseResponse.data;
    localStorage.setItem('userName', student.value.studentName);
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取信息失败',
    });
  }
});

const goToCourseInfo = async (courseId) => {
  try {
    const response = await reqCourseIntro(courseId);
    if (response && response.data) {
      const currentQuery = router.currentRoute.value.query; // 获取当前查询参数
      localStorage.setItem('courseId', courseId);
      router.push({
        path: '/CourseInfo',
        query: {
          ...currentQuery, // 保留当前查询参数
          courseId,
          courseIntro: response.data.courseInfo,
          courseName: response.data.courseName,
          courseNo: response.data.courseNo,
          teacherName: response.data.teacherName,
          establishCollege: response.data.establishCollege,
          semester: response.data.semester,
        },
      });
    }
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取课程信息失败',
    });
  }
};
</script>


<style>
.head {
  background-color: #005bac;

}

.backmain1 {
  height: 770px;
  background-color: #eaf6ff;
}

.zitihead {
  text-align: left;
  font-weight: bold;
  font-size: 24px;
  margin-top: 15px;
  color: #ffffff;
}

.ziti01 {
  text-align: center;
  font-weight: 400;
  font-size: 14px;
  margin-bottom: 25px;
  margin-top: 10px;
}

.ziti02 {
  text-align: center;
  font-weight: bold;
  font-size: 16px;
}

.ziti03 {
  text-align: left;
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 16px;
  margin-left: 10px;
  padding-top: 10px;
}

.ziti04 {
  text-align: left;
  font-weight: 400;
  margin-bottom: 2px;
  font-size: 14px;
  margin-left: 10px;
}

.backleft {
  width: "350px";
  max-height: "550px";
  background-color: #eaf6ff;
}

.backright {
  width: "350px";
  height: "600px";
  background-color: #eaf6ff;
}

.whiteback {
  border-radius: 4px;
  max-height: 350px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

}

.whiteback2 {
  margin-top: 20px;
  border-radius: 4px;
  height: 200px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.whiteback3 {
  /* margin-top: 20px; */
  border-radius: 4px;
  height: 400px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.scrollable {
  height: 690px;
  /* 固定高度 */
  overflow-y: auto;
  /* 垂直方向上的滚动条 */
  overflow-x: hidden;
  padding-right: 10px;
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.bg-white {
  background: #ffffff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 30px
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  height: 250px;
  margin-bottom: 10px;

}

body>.el-container {
  margin-bottom: 40px;
}
</style>