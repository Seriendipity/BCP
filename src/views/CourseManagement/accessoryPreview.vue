<template>
  <iframe class="preview" :src="previewSrc" width="100%" height="100%" style="border:none;"></iframe>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="returnSendHomework()">返回</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { editSingleHomework } from '@/api/api';
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
const previewSrc = ref(props.previewSrc); // 使用ref保存课程大纲的URL

onMounted(async () => {
  try {
    const homeworkNO = localStorage.getItem('homeworkNO');
    const response = await editSingleHomework(homeworkNO); // 获取后端课程大纲URL
    console.log(response)
    previewSrc.value = response.data.file.split('/').pop() || previewSrc.value; // 更新URL或保持默认
    console.log(previewSrc)
  } catch (error) {
    console.log(error)
    ElNotification({
      type: 'error',
      message: '浏览作业失败',
    });
  }
});

const returnSendHomework = () => {
  $router.push({ name: 'sendHomework' });
}
</script>

<style>
.preview {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 560px;
  border: groove;
}
</style>