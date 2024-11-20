<template>
  <el-container>
    <el-aside width="200px">
      <div class="homework-scrollable">
        <el-row :gutter="20">
          <el-col :span="24" v-for="(homework, index) in homeworks" :key="index">
            <div class="homework-bar">
              <div class="homework-info" @click="viewhomework(homework)">
                <h2 class="homework-lesson">{{ homework.homeworkInfoName }}</h2>
                <h2 class="homework-stuId">{{ homework.homeworkstuID }}</h2>
                <p class="homework-time">{{ homework.homeworkUpdateTime }}</p>
              </div>
              <el-tag :type="homework.homeworkState === '已批改' ? 'success' : 'warning'">
                {{ homework.homeworkCheckState }}
              </el-tag>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-aside>
    <el-main>
      <iframe class="homework" :src="homeworkSrc" width="100%" height="100%" style="border:none;"></iframe>
    </el-main>
    <el-aside width="200px">
      <div class="inputagr_box" width="100%">
          <textarea class="agr-input no-border" v-model="question" />
        </div>
        <div class="btn_box">
          <el-button type="primary" class="btn" >保存</el-button>
        </div>
        <div class="inputagr_box" width="100%">
          <textarea class="agr-input no-border" v-model="question" />
        </div>
        <div class="btn_box">
          <el-button type="primary" class="btn" >打分</el-button>
        </div>
    </el-aside>
  </el-container>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { reqHomework ,reqCourseIntro, requireTeacherSendHomework } from '@/api/api';
import { ElNotification } from 'element-plus';
import { requireAvatar } from '../../api/api';

// const courses = ref([]);
const userInfo = ref([]);
const router = useRouter();
const dialogVisible = ref(false);
const currenthomework = ref({});
const homeworks = ref([]);
const homeworkSrc = ref(props.previewSrc); 

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});

// 模拟数据
const mockDataHomework = [
  {
    index: 1,
    homeworkstuName: '姜天亦',
    homeworkstuID: '22301149',
    homeworkGrade: '99',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    index: 2,
    homeworkstuName: '马海博',
    homeworkstuID: '22301157',
    homeworkGrade: '98',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    index: 3,
    homeworkstuName: '张学琛',
    homeworkstuID: '22301168',
    homeworkGrade: '100',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    index: 4,
    homeworkstuName: '郑宇煊',
    homeworkstuID: '22301170',
    homeworkGrade: ' ',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
  {
    index: 5,
    homeworkstuName: '刘艺凡',
    homeworkstuID: '22301153',
    homeworkGrade: ' ',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
];

const mockDataUser = {
  userName: '张三',
  userId: '20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};


const fetchhomeworks = async () => {
  try {
    const courseId = localStorage.getItem('courseId')
    const response = await requireTeacherSendHomework(courseId); // 获取通知数据
    homeworkSrc.value = response.data || []; // 更新通知数据
  } catch (err) {
    // 捕获错误并使用模拟数据
    homeworkSrc.value = props.previewSrc;
    ElNotification({
      message: '获取通知失败',
      type: 'error',
    });
  }
};

onMounted(() => {
  fetchhomeworks(); // 组件挂载时获取通知数据
});

// 查看通知详情
// const viewhomework = async (homework) => {
  //   currenthomework.value = homework; // 设置当前通知
  //   dialogVisible.value = true; // 打开弹出框

  //   if (homework.homeworkState === '未读') {
  //     // 将状态更新为已读
  //     homework.homeworkState = '已读';

  //     // 调用后端API更新状态
  //     try {
  //       await updatehomeworkState({
  //         homeworkId: homework.homeworkId, // 传递通知ID
  //       });
  //     } catch (error) {
  //       console.error('更新通知状态失败:', error);
  //       ElNotification({
  //         type: 'error',
  //         message: '更新通知状态失败，请重试。',
  //       });
  //     }
  //   }
// };


// 关闭弹出框
const closeDialog = () => {
  dialogVisible.value = false; // 关闭弹出框
};

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const homeworkResponse = await requireTeacherSendHomework();
    homeworks.value = homeworkResponse.data;
    localStorage.setItem('homeworkId');
    const storedHomeworkId = localStorage.getItem('homeworkId');
    const response = await reqHomework(storedHomeworkId); // 获取后端日历URL
    homeworkSrc.value = response.data.Homework || homeworkSrc.value; 
  } catch (error) {
    homeworks.value = mockDataHomework.data;
    // console.log(error)
    userInfo.value = mockDataUser;
    ElNotification({
      type: 'error',
      message: '获取信息失败',
    });
  }
});

// const goToCourseInfo = async (courseId) => {
//   try {
//     const response = await reqCourseIntro(courseId);
//     if (response && response.data) {
//       const currentQuery = router.currentRoute.value.query; // 获取当前查询参数
//       localStorage.setItem('courseId', courseId);
//       router.push({
//         path: '/CourseInfo',
//         query: {
//           ...currentQuery, // 保留当前查询参数
//           courseId,
//           courseIntro: response.data.courseInfo,
//           courseName: response.data.courseName,
//           courseNo: response.data.courseNo,
//           teacherName: response.data.teacherName,
//           establishCollege: response.data.establishCollege,
//           semester: response.data.semester,
//         },
//       });
//     }
//   } catch (error) {
//     ElNotification({
//       type: 'error',
//       message: '获取课程信息失败',
//     });
//   }
// };
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
  width: "350px";
  max-height: "550px";
  background-color: #eaf6ff;
}

.backright {
  width: "350px";
  max-height: "550px";
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

.homework-scrollable {
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 360px;
  overflow-y: auto;
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

.homework-bar:hover {
  background: #f5f7fa;
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
.homework-dialog .dialog-content {
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


.inputagr_box {
  flex: 1;
  display: flex;
  align-items: center;
}


.agr-input {
    width: 100%;
    height: 80px;
    padding: 12px 16px;
    border: 1px solid #dcdfe6;
    border-radius: 8px;
    resize: none;
    font-size: 14px;
    color: #606266;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
    transition: border-color 0.3s, box-shadow 0.3s;
    margin-top: 10px;
  }

  .btn_box {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  float: right;

  .btn {
    background-color: #409eff;
    color: #ffffff;
    border-radius: 4px;
    padding: 6px 20px;
    font-size: 14px;
    transition: background-color 0.3s;
  }

  .btn:hover {
    background-color: #66b1ff;
  }
}
</style>