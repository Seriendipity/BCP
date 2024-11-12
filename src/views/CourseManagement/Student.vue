<template>
  <div class="Intro">
    <el-table class="stutable" :data="studentListData" height="100%" width="90%">
      <el-table-column prop="classNo" label="课程号" width="150px"></el-table-column>
      <el-table-column prop="className" label="课程名称" width="150px"></el-table-column>
      <el-table-column prop="studentNo" label="学号" width="150px"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="150px"></el-table-column>
      <el-table-column prop="grade" label="成绩"></el-table-column>
    </el-table>
  </div>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="exportStudentList()">导出学生名单</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqStudentData, requireStudentList } from '@/api/api';
import { ElNotification } from 'element-plus';

const studentListData = ref([]); // 保存学生数据的数组

onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqStudentData(storedCourseId); // 请求后端学生数据
    if (response.code === 0) {
      // 将后端数据转为数组格式并赋值给 tableData
      studentListData.value = Object.values(response.data).map(student => ({
        studentName: student.studentName,
        grade: student.grade,
        classNo: student.classNo,
        index: student.index,
        studentNo: student.studentNo,
        className: student.className
      }));
    } else {
      ElNotification({
        type: 'error',
        message: response.message || '获取学生数据失败'
      });
    }
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取学生数据失败'
    });
  }
});

const exportStudentList = async () => {
  alert('导出名单为.xlsx格式')
  const courseId = localStorage.getItem('courseId')
  try {
    const response = await requireStudentList(courseId);
    const link = document.createElement('a');
    const url = window.URL.createObjectURL(response.blob());
    link.href = url;
    link.download = `student_list_${courseId}.xlsx`
    link.click();
    window.URL.revokeObjectURL(url);
  } catch (error) {
    ElNotification({
      message: '导出名单失败，请重试',
      type: 'error',
    });
  }
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
