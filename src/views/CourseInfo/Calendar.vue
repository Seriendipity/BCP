<template>
  <iframe class="calendar" :src="calendarSrc" width="100%" height="100%" style="border:none;"></iframe>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqCalendar } from '@/api/api'; // 确保你有这个API接口
import { ElNotification } from 'element-plus';

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});

const calendarSrc = ref(props.previewSrc); // 使用ref保存日历的URL

onMounted(async () => {
  try {
    const response = await reqCalendar(); // 获取后端日历URL
    calendarSrc.value = response.data.url || calendarSrc.value; // 更新URL或保持默认
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取日历信息失败',
    });
  }
});
</script>

<style>
.calendar {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 570px;
  border: groove;
}
</style>
