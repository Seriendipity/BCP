<!-- eslint-disable vue/multi-word-component-names -->
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
              <div style="margin-top: 10px"><el-avatar :size="40" :src="userInfo.avatarUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">{{ userInfo.userName }}
            </h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <el-container class="main">
        <el-aside class="backleft">
          <div class="whiteback" :data="userInfo">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
            <div style="margin-top: 20px;text-align: center; "><el-avatar :size="100"
                :src="userInfo.avatarUrl"></el-avatar>
            </div>
            <h1 class="ziti01">学生</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ userInfo.userName }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ userInfo.userId }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ userInfo.dept }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ userInfo.email }}</h1>
          </div>
          <div class="whiteback2">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">课程作业提醒</h1>
            <div class="homework-scrollable">
              <el-row :gutter="20">
                <el-col :span="24" v-for="(homework, index) in homeworks" :key="index">
                  <div class="homework-bar">
                    <div class="homework-info">
                      <h2 class="homework-lesson">{{ homework.courseName }}</h2>
                      <h2 class="homework-title">{{ homework.homeworkDesc }}</h2>
                      <p class="homework-time">起始时间：{{ homework.homeworkStartTime }}</p>
                      <p class="homework-time">终止时间：{{ homework.homeworkEndTime }}</p>
                    </div>
                    <el-tag :type="'warning'">
                      未完成
                    </el-tag>
                  </div>
                </el-col>
              </el-row>
            </div>
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
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 10px;">通知公告</h1>
            <div class="notification-scrollable">
              <el-row :gutter="20">
                <el-col :span="24" v-for="(notification, index) in notifications" :key="index">
                  <div class="notification-bar">
                    <div class="notification-info" @click="viewNotification(notification)">
                      <h2 class="notification-lesson">{{ notification.courseName }}</h2>
                      <h2 class="notification-title">{{ notification.notificationTitle }}</h2>
                      <p class="notification-time">{{ notification.notificationPostingTime }}</p>
                    </div>
                    <el-tag :type="notification.notificationState === '已读' ? 'success' : 'warning'">
                      {{ notification.notificationState }}
                    </el-tag>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>

        </el-aside>
      </el-container>
    </el-main>
  </el-container>

  <!-- 查看通知的弹出框 -->
  <el-dialog v-model="dialogVisible" title="查看通知" width="600px" class="notification-dialog">
    <div class="dialog-content">
      <p class="dialog-title"><strong>主题:</strong> {{ currentNotification.notificationTitle }}</p>
      <p class="dialog-info"><strong>内容:</strong> {{ currentNotification.notificationInfo }}</p>
    </div>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="closeDialog" type="primary">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { reqUserInfo, reqCourseList, reqCourseIntro, reqNotificationAll, updateNotificationState, reqUnfinishedHomework } from '@/api/api';
import { ElNotification } from 'element-plus';
import { requireAvatar } from '../../api/api';

const courses = ref([]);
const userInfo = ref([]);
const router = useRouter();
const dialogVisible = ref(false);
const currentNotification = ref({});
const notifications = ref([]);
const homeworks = ref([]);


// 模拟数据
const mockDataNotify = [
  {
    courseName: '数据库系统',
    notificationTitle: '课程调整通知',
    notificationInfo: '由于特殊情况，本周的课程时间将调整，请查看具体时间安排。',
    notificationPostingTime: '2024-10-28 14:00',
    notificationState: '未读',
  },
  {
    courseName: '人工智能基础',
    notificationTitle: '期末考试安排',
    notificationInfo: '请注意，期末考试将在12月1日举行，请提前做好复习准备。',
    notificationPostingTime: '2024-10-29 09:00',
    notificationState: '已读',
  },
  {
    courseName: '软件学院团委宣传部',
    notificationTitle: '社团活动通知',
    notificationInfo: '下周五有社团活动，欢迎大家参加！',
    notificationPostingTime: '2024-10-30 11:00',
    notificationState: '未读',
  },
];

const mockDataHomework = [
  {
    courseName: '高等数学',
    homeworkDesc: '第3章作业：微分公式及应用',
    homeworkStartTime: '2024-11-20 08:00:00',
    homeworkEndTime: '2024-11-25 23:59:59',
  },
  {
    courseName: '线性代数',
    homeworkDesc: '矩阵与向量作业',
    homeworkStartTime: '2024-11-21 10:00:00',
    homeworkEndTime: '2024-11-28 23:59:59',
  },
  {
    courseName: '数据结构',
    homeworkDesc: '链表实现的基本操作',
    homeworkStartTime: '2024-11-22 14:00:00',
    homeworkEndTime: '2024-11-27 20:00:00',
  },
  {
    courseName: '操作系统',
    homeworkDesc: '文件系统原理课后习题',
    homeworkStartTime: '2024-11-23 09:00:00',
    homeworkEndTime: '2024-11-29 18:00:00',
  },
  {
    courseName: '编译原理',
    homeworkDesc: '语法分析与语义分析作业',
    homeworkStartTime: '2024-11-24 08:30:00',
    homeworkEndTime: '2024-12-01 22:00:00',
  },
  {
    courseName: '人工智能导论',
    homeworkDesc: 'AI伦理与应用案例分析',
    homeworkStartTime: '2024-11-25 11:00:00',
    homeworkEndTime: '2024-12-02 21:00:00',
  },
];

const mockDataUser = {
  userName: '张三',
  userId: '20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};

// 获取通知数据
const fetchNotifications = async () => {
  try {
    const response = await reqNotificationAll(); // 获取通知数据
    const homeworkResponse = await reqUnfinishedHomework();
    notifications.value = response.data || []; // 更新通知数据
    homeworks.value = homeworkResponse.data || [];
  } catch (err) {
    console.log(err);
    // 捕获错误并使用模拟数据
    notifications.value = mockDataNotify;
    console.log(mockDataHomework);
    homeworks.value = mockDataHomework;
    console.log(homeworks)
    console.log(err)
    ElNotification({
      message: '获取失败',
      type: 'error',
    });
  }
};


onMounted(() => {
  fetchNotifications(); // 组件挂载时获取通知数据

});

// 查看通知详情
const viewNotification = async (notification) => {
  currentNotification.value = notification; // 设置当前通知
  dialogVisible.value = true; // 打开弹出框

  if (notification.notificationState === '未读') {
    // 将状态更新为已读
    notification.notificationState = '已读';

    // 调用后端API更新状态
    try {
      await updateNotificationState({
        notificationId: notification.notificationId, // 传递通知ID
      });
    } catch (error) {
      console.error('更新通知状态失败:', error);
      ElNotification({
        type: 'error',
        message: '更新通知状态失败，请重试。',
      });
    }
  }
};


// 关闭弹出框
const closeDialog = () => {
  dialogVisible.value = false; // 关闭弹出框
};

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const avatarResponse = await requireAvatar();
    const userResponse = await reqUserInfo();
    const courseResponse = await reqCourseList();
    userInfo.value = userResponse.data;
    courses.value = courseResponse.data;
    userInfo.value.avatarUrl = avatarResponse.data;
    localStorage.setItem('userName', userInfo.value.userName);
    localStorage.setItem('userId', userInfo.value.userId)
  } catch (error) {
    userInfo.value = mockDataUser;
    console.log(error)
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
    console.log(error)
    ElNotification({
      type: 'error',
      message: '获取课程信息失败',
    });
  }
};
</script>


<style>
.main {
  height: 600px;
}

.head {
  background-color: #005bac;

}

.backmain1 {
  height: 700px;
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
  width: 360px;
  height: 650px;
  background-color: #eaf6ff;
}

.backright {
  width: 290px;
  height: 650px;
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
  height: 350px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.whiteback3 {
  /* margin-top: 20px; */
  border-radius: 4px;
  height: 650px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.scrollable {
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

.notification-scrollable {
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 360px;
  overflow-y: auto;
}

.homework-scrollable {
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 300px;
  overflow-y: auto;
}

.notification-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #ffffff;
  transition: background 0.3s ease;
}

.homework-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #ffffff;
  transition: background 0.3s ease;
}

.notification-bar:hover {
  background: #f5f7fa;
}

.homework-bar:hover {
  background: #f5f7fa;
}

.notification-info {
  display: flex;
  flex-direction: column;
}

.notification-title {
  font-size: 14px;
  font-weight: normal;
}

.notification-lesson {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.notification-time {
  color: #999;
  font-size: 12px;
  margin-top: 10px;
}

.homework-info {
  display: flex;
  flex-direction: column;
}

.homework-title {
  font-size: 14px;
  font-weight: normal;
}

.homework-lesson {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.homework-time {
  color: #999;
  font-size: 12px;
  margin-top: 10px;
}

/* 弹出框样式 */
.notification-dialog .dialog-content {
  padding: 15px;
}

.dialog-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.dialog-info {
  font-size: 16px;
  line-height: 1.6;
  color: #666;
}

.dialog-footer {
  text-align: right;
}
</style>