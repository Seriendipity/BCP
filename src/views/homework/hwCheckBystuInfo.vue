<template>
    <div class="bg-info-content">
        <h1 class="info-title">互评详情</h1>
        <p class="info-content">{{ content }}</p>
        <el-button type="success" style="margin-top: 300px;width: 50%;margin-left: 25%; " @click="startCheck()">开始互评</el-button>
    </div>
    
</template>

<script setup>
// import { ref, onMounted } from 'vue';

import { useRouter } from 'vue-router';

let $router = useRouter()
const content = "数据未正确加载：确保homeworks数组在组件加载时被正确填充。检查requireTeacherSendHomework函数是否成功返回了数据，并且数据格式是否与你的模板中预期的格式相匹配。数据未正确加载：确保homeworks数组在组件加载时被正确填充。检查requireTeacherSendHomework函数是否成功返回了数据，并且数据格式是否与你的模板中预期的格式相匹配。";
const startCheck =() =>{
    $router.push({name:'homeworkCheckbystu'})
}
</script>

<style>

.bg-info-content {
  border-radius: 4px;
  min-height: 36px;
  height: 600px;
  margin-top: 15px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  background: #ffffff;
  padding: 40px;
}

.info-title{
    text-align: left;
    font-weight: bold;
    font-size: 16px;
    text-align: center;
}

.info-content{
    text-align: left;
    font-weight:500;
    font-size: 14px;
    line-height: 1.5;
    margin: 40px;
}
</style>