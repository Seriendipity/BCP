<template>
  <div class="logoimg" @click="goToFirstPage">
    <img src="../../assets/images/logo.gif" alt="">
    <p v-show="!LayOutSettingStore.fold">智慧课程平台</p>
  </div>
</template>

<script setup lang='ts'>
import { useRouter } from 'vue-router';
import useLayOutSettingStore from '@/store/modules/setting';

const router = useRouter();
let LayOutSettingStore = useLayOutSettingStore();

const goToFirstPage = () => {
  router.push('/firstpage');
};
</script>

<script lang="ts">
export default {
  name: "Logo"
}
</script>

<style scoped lang="scss">
$logo_height: 50px;

.logoimg {
  width: 100%;
  height: $logo_height;
  display: flex;
  align-items: center;
  padding: 5px;
  cursor: pointer;
  /* 添加指针光标样式 */

  img {
    width: 40px;
    height: 40px;
  }

  p {
    color: white;
    font-size: 22px;
  }
}
</style>
