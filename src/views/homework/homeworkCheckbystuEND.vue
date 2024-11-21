<template>
    <div class="bg-end">
        <el-result  title="互评完成" icon="success">

      </el-result>
      
      <el-button @click="goBack" type="primary" style="float: right; margin-right: 20px;">返回作业列表</el-button>

    </div>
</template>

<script setup>
 import { useRouter } from 'vue-router'
 const props = defineProps(['score'])
  const score = props.score
  console.log(score)
  const router = useRouter()
const goBack = () => {
    router.push('/homework/homeworkList')
  }
</script>

<style>
.bg-end {
  border-radius: 4px;
  min-height: 36px;
  height: 250px;
  margin-bottom: 10px;
  background-color: white;
}
</style>