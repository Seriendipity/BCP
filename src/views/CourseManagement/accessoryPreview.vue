<template>
  <iframe class="syllabus" :src="syllabusSrc" width="100%" height="100%" style="border:none;"></iframe>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="returnSendHomework()">返回</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqSyllabus } from '@/api/api';
import { ElNotification } from 'element-plus';
import { useRouter } from 'vue-router';

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});
const $router = useRouter()
const syllabusSrc = ref(props.previewSrc); // 使用ref保存课程大纲的URL

onMounted(async () => {
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqSyllabus(storedCourseId); // 获取后端课程大纲URL
    console.log(response)
    syllabusSrc.value = response.data.Syllabus || Syllabus.value; // 更新URL或保持默认
    console.log(syllabusSrc)
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取课程大纲失败',
    });
  }
});

const returnSendHomework = () => {
  $router.push({ name: 'sendHomework' });
}
</script>

<style>
.syllabus {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 560px;
  border: groove;
}
</style>