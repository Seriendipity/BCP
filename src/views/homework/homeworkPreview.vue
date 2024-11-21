<template>
  <iframe class="Homework" :src="HomeworkSrc" width="100%" height="100%" style="border:none;"></iframe>
  <el-button type="primary" round
    style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
    @click="returnHomeworkList()">返回</el-button>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElNotification } from 'element-plus';
import { useRouter } from 'vue-router';

let $router = useRouter()

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});

const HomeworkSrc = ref(props.previewSrc);

onMounted(async () => {
  try {
    const homeworkPath = localStorage.getItem('homeworkPath');

    HomeworkSrc.value = homeworkPath.split('/').pop();// 更新URL或保持默认
    console.log(HomeworkSrc)
  } catch (error) {
    console.log(error)
    ElNotification({
      type: 'error',
      message: '获取作业预览失败',
    });
  }
});

const returnHomeworkList = () => {
  $router.push({ name: 'homeworkList' });
}
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