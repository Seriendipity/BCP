<template>
  <div class="Intro">
    <p class="content">{{ courseIntro }}</p>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { reqCourseIntro } from '@/api/api';
import { ElNotification } from 'element-plus';

// 创建一个响应式变量用于存储课程介绍
const courseIntro = ref('');

// 获取路由信息
const route = useRoute();

onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqCourseIntro(storedCourseId); // 获取教师信息
    courseIntro.value = response.data.courseInfo
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取课程介绍失败',
    });
  }
});
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 100%;
  /* 修改高度为百分比 */
  width: 100%;
  /* 修改宽度为百分比 */
  margin: 0 auto;
  /* 居中对齐 */
  border: groove;
  position: relative;
  /* 相对定位 */
  top: 25%;
  /* 从顶部占据25%的空间 */
}

.content {
  font-family: 'Helvetica', 'Arial', sans-serif;
  font-size: 18px;
  /* 保持原来的绝对值 */
  color: #333333;
  /* 文字颜色为深灰色，易于阅读 */
  line-height: 1.6;
  /* 增加行高，提高可读性 */
  text-align: justify;
  /* 使文字两端对齐，排版整齐 */
  padding: 20px;
  /* 添加内边距，避免文字紧贴边框 */
  letter-spacing: 0.5px;
  /* 增加字母间距，使文本更加舒适 */
  word-wrap: break-word;
  /* 允许长单词换行，避免溢出 */
  background-color: #f9f9f9;
  /* 背景颜色略微调亮，增加层次感 */
  border-radius: 10px;
  /* 增加圆角，保持一致性 */
}
</style>
