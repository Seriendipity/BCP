<template>
  <div class="Intro">
    <el-table class="stutable" :data="studentListData" height="100%" width="90%">
      <el-table-column prop="studentNo" label="学号" width="150px"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="150px"></el-table-column>
      <el-table-column prop="grade" label="成绩" width="150px"></el-table-column>
    </el-table>
  </div>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="postFinalScore()">发布成绩</el-button>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="getFinalScore()">获取最终成绩</el-button>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="goBack()">返回</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqStudentData, getEvaluationEndTime, getFinalGrade, postScore } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const studentListData = ref([]); // 保存学生数据的数组
const $router = useRouter();
onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqStudentData(storedCourseId); // 请求后端学生数据
    if (response.code === 0) {
      // 将后端数据转为数组格式并赋值给 tableData
      studentListData.value = Object.values(response.data).map(student => ({
        studentName: student.studentName,
        studentNo: student.studentNo,
      }));
    } else {
      ElNotification({
        type: 'error',
        message: response.message || '获取学生数据失败'
      });
    }
  } catch (error) {
    console.log(error)
    ElNotification({
      type: 'error',
      message: '获取学生数据失败'
    });
  }
});

const getFinalScore = async () => {
  const homeworkNo = localStorage.getItem('homeworkNO')
  try {
    const response = await getEvaluationEndTime(homeworkNo)
    const currentTime = new Date()
    const backendTime = new Date(response.data.time);
    if (currentTime < backendTime) {
      ElNotification({
        message: '互评截止时间未到',
        type: 'error',
      });
    } else {
      try {
        const response = await getFinalGrade(homeworkNo)
        if (response.code === 0) {
          // 将后端数据转为数组格式并赋值给 tableData
          studentListData.value = Object.values(response.data).map(student => ({
            studentName: student.studentName,
            studentNo: student.studentNO,
            grade: student.grade,
          }));
        }
      } catch (error) {
        console.log(error)
        ElNotification({
          message: '获取成绩失败，请重试',
          type: 'error',
        });
      }
    }
  } catch (error) {
    console.log(error)
    ElNotification({
      message: '获取互评截止时间失败，请重试',
      type: 'error',
    });
  }
}

const postFinalScore = async () => {
  const formData = new FormData()
  const homeworkNo = localStorage.getItem('homeworkNO')
  formData.append('homeworkNo', homeworkNo)
  try {
    const response = await postScore(formData)
    if (response.code === 0) {
      ElMessage.success("发布成绩成功");
    }
  } catch (error) {
    console.log(error)
    ElNotification({
      message: '发布成绩失败，请重试',
      type: 'error',
    });
  }
  //TODO: 发布最终成绩
}

const goBack = () => {
  const storedCourseId = localStorage.getItem('courseId');
  $router.push("/CourseManagement/sendHomework" + `?cid=${storedCourseId}`);
}
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: var(--el-box-shadow-light);
  background-color: #FFFFFF;
  height: 560px;
  border: groove;
}

.stutable {
  border-radius: 20px;
  padding: 10px;
}
</style>
