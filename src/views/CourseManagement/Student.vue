<template>
  <div class="Intro">
    <el-table class="stutable" :data="studentListData" height="100%" width="90%">
      <el-table-column prop="classNo" label="课程号" width="150px"></el-table-column>
      <el-table-column prop="className" label="课程名称" width="150px"></el-table-column>
      <el-table-column prop="studentNo" label="学号" width="150px"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="150px"></el-table-column>
    </el-table>
  </div>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="exportStudentList()">导出学生名单</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqStudentData, requireStudentList } from '@/api/api';
import { ElMessage, ElNotification } from 'element-plus';
import axios from 'axios';

const studentListData = ref([]); // 保存学生数据的数组

onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqStudentData(storedCourseId); // 请求后端学生数据
    if (response.code === 0) {
      // 将后端数据转为数组格式并赋值给 tableData
      studentListData.value = Object.values(response.data).map(student => ({
        studentName: student.studentName,
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

// const exportStudentList = async () => {
//   alert('导出名单为.xlsx格式')
//  const courseId = localStorage.getItem('courseId')

// try {
//   const response = await requireStudentList(courseId, { responseType: 'blob' });
//   console.log(response); // 查看整个 response 对象
//   console.log(response.data); // 查看 data 属性
// // 检查 response.data 是否是 Blob
// // 创建 Blob 对象并下载
//   const blob = new Blob([response], {
//         type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
//       });
// if (blob instanceof Blob) {
//   const link = document.createElement('a');
//   const url = window.URL.createObjectURL(blob);  // response.data 是 Blob
//   link.href = url;

//    // 使用时间戳生成文件名，例如：student_list_时间戳.xlsx
//   const timestamp = new Date().toISOString().replace(/[-:.]/g, "");  // 格式化时间戳，去掉不合法字符
//   const filename = `student_list_${timestamp}.xlsx`;  // 自定义文件名

//   console.log(filename);  // 打印文件名
//   link.download = filename;  // 使用后端返回的文件名
//   // 模拟点击下载
//   link.click();
  
//   // 释放对象 URL
//   window.URL.revokeObjectURL(url);
//   ElMessage.success('导出成功')
// } else {
//   console.error('Expected Blob, but received:', response);
// }
// } catch (error) {
//   console.log(error);
//   ElNotification({
//     message: '导出名单失败，请重试',
//     type: 'error',
//   });
// }

// }
const exportStudentList = async () => {
  alert('导出名单为.xlsx格式');
  const courseId = localStorage.getItem('courseId');
  console.log(courseId)

  try {
    const response = await axios.get(`http://localhost:8081/StudentCourse/exportStudentList?cid=${courseId}`, {
      responseType: 'blob',  // 确保接收到二进制流（Blob 数据）
      withCredentials: false
    });

    console.log(response)
    // 检查 response.data 是不是 Blob 对象
    const blob = response.data;  // Blob 数据
    const url = window.URL.createObjectURL(blob);  // 创建 URL
    const link = document.createElement('a');
    link.href = url;

    const timestamp = new Date().toISOString().replace(/[-:.]/g, "");
    const filename = `student_list_${timestamp}.xlsx`;

    link.download = filename;  // 设置文件下载名
    link.click();  // 模拟点击下载

    window.URL.revokeObjectURL(url);  // 释放 URL 对象

    ElMessage.success('导出成功');
  } catch (error) {
    console.error('导出失败', error);
    ElNotification({
      message: '导出名单失败，请重试',
      type: 'error',
    });
  }
};

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
