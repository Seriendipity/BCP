<template>
  <iframe class="Homework" :src="HomeworkSrc" width="100%" height="100%" style="border:none;"></iframe>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="returnHomeworkList()">返回</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqHomework } from '@/api/api';
import { ElNotification } from 'element-plus';
import { useRouter } from 'vue-router';

const $router = useRouter()
const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});

const HomeworkSrc = ref(props.previewSrc); // 使用ref保存课程大纲的URL

const returnHomeworkList = () => {
  $router.push({ name: 'homeworkList' });
}
onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqHomework(storedCourseId); // 获取后端课程大纲URL
    console.log(response)
    HomeworkSrc.value = response.data.homeworkPath
    console.log(HomeworkSrc)
  } catch (error) {
    console.log(error)
    ElNotification({
      type: 'error',
      message: '获取课程大纲失败',
    });
  }
});
</script>

<style>
.Homework {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 580px;
  border: groove;
}
</style>