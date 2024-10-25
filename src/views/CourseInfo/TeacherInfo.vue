<template>
  <div class="teacherInfo">
    <el-descriptions direction="vertical" border style="margin-top: 20px">
      <el-descriptions-item :rowspan="2" :width="140" align="center">
        <el-image style="width: 100px; height: 100px" :src="teacher.head_portait || defaultPicture" />
      </el-descriptions-item>
      <el-descriptions-item label="教师姓名">{{ teacher.teacherName || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{ teacher.teacherSex || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="职称">{{ teacher.teacherTitle || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="生日">{{ teacher.teacherBirthday || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="办公电话">{{ teacher.teacherPhone || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="办公地点">{{ teacher.teacherStation || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ teacher.teacherEmail || '未提供' }}</el-descriptions-item>
      <el-descriptions-item label="学院">
        <el-tag size="small">{{ teacher.establishCollege || '未提供' }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="简介">
        {{ teacher.teacherInfo || '未提供' }}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqTeacherInfo } from '@/api/api'; // 确保你有这个API接口
import { ElNotification } from 'element-plus';

const teacher = ref({
  teacherName: '未提供',
  teacherTitle: '未提供',
  teacherPhone: '未提供',
  teacherStation: '未提供',
  teacherEmail: '未提供',
  teacherSex: '未提供',
  teacherBirthday: '未提供',
  establishCollege: '未提供',
  teacherInfo: '未提供',
  head_portait: '', // 默认头像将会使用 defaultPicture
});
const defaultPicture = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'; // 默认头像

onMounted(async () => {
  try {
    const response = await reqTeacherInfo(); // 获取教师信息
    teacher.value = { ...teacher.value, ...response.data }; // 合并后端数据
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取教师信息失败',
    });
  }
});
</script>

<style scoped>
.teacherInfo {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  width: 100%;
  height: auto;
  border: groove;
  margin: 0 auto;
  padding: 20px;
}
</style>
