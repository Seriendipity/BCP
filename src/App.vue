<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script setup lang='ts'>
import { onMounted } from 'vue';
import { reqLogin } from './api/api';
onMounted(() => {
  reqLogin({ username: 'admin', password: '123456' })
})
</script>

<style></style>