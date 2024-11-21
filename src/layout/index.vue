<template>
  <div class="common-layout">
    <el-container style="height: 100vh;">
      <el-header class="out_header" style="height: 8%;">
        <div class="row1">
          <el-row :gutter="40">
            <el-col :span="20">
              <span class="lesson-name">{{ courseName }}</span>
            </el-col>
            <el-col :span="4" class="right-align">
              <span class="school-text">{{ establishCollege }}</span>
            </el-col>
          </el-row>
        </div>
        <div class="row2">
          <el-row :gutter="20">
            <el-col :span="16">
              <div class="teacherName-number">
                <span>主讲教师：{{ teacherName }}</span>
                <el-divider direction="vertical" border-style="dashed" />
                <span>课程编号: {{ courseNo }}</span>
                <el-divider direction="vertical" border-style="dashed" />
                <span>课序号：{{ cid }}</span>
              </div>
            </el-col>
            <el-col :span="8" class="right-align">
              <div class="semester">
                <span>学期：{{ semester }}</span>
                <span style="margin-left: 10px;">教学周：第{{ week }}周</span>
              </div>
            </el-col>
          </el-row>
        </div>

      </el-header>
      <el-container>
        <el-aside width="20%" style="height: 100%;">
          <div>
            <div class="layout_slidder" :class="{ fold: LayOutSettingStore.fold }" background-color="#005bac">
              <logo></logo>
              <el-scrollbar class="scrollbar">
                <el-menu :collapse="LayOutSettingStore.fold" text-color="white" background-color="#005bac"
                  :default-active="$route.path">
                  <Menu :menuList="userStore.menuRoutes"></Menu>
                </el-menu>
              </el-scrollbar>
            </div>
          </div>
        </el-aside>
        <el-main style="width: 100%;">
          <div class="layout_tabbar" :class="{ fold: LayOutSettingStore.fold }">
            <Tabbar></Tabbar>
          </div>
          <div class="layout_main" :class="{ fold: LayOutSettingStore.fold }">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import logo from './logo/index.vue';
import Menu from './menu/index.vue';
import Tabbar from './tabbar/index.vue';
import useUserStore from '@/store/modules/user';
import { useRoute } from 'vue-router';
import useLayOutSettingStore from '@/store/modules/setting';
import { ElNotification } from 'element-plus';
import { reqCourseIntro } from '@/api/api';
import { ref } from 'vue';
import { onMounted } from 'vue';

let LayOutSettingStore = useLayOutSettingStore();
let $route = useRoute();
let userStore = useUserStore();

// 计算教学周的函数
function calculateTeachingWeek(startDate: any, currentDate = new Date()) {
  const start = new Date(startDate);
  const timeDiff = currentDate.getTime() - start.getTime();
  const dayDiff = Math.floor(timeDiff / (1000 * 3600 * 24));
  const weekNumber = Math.ceil(dayDiff / 7);
  return weekNumber > 0 ? weekNumber : 0;
}

const startDate = '2024-09-09';
const currentDate = new Date();
const currentWeek = calculateTeachingWeek(startDate, currentDate);
// 获取从 URL 传递的 query 参数
const courseName = ref('默认课程名称');
const establishCollege = ref( '默认学院名称');
const teacherName = ref('默认教师');
const courseNo = ref('默认课程编号');
const cid = ref('默认课序号');
const semester = ref('默认学期');
const week = ref(currentWeek || '默认教学周');
onMounted(async () => {
try {
  const courseId = localStorage.getItem('courseId')
  const response = await reqCourseIntro(courseId);
  if(response.code===0){
    courseName.value = response.data.courseName;
    establishCollege.value = response.data.establishCollege;
    teacherName.value = response.data.teacherName ;
    courseNo.value = response.data.courseNo;
    cid.value = response.data.cid;
    semester.value = response.data.semester;
  }
}catch(error){
  console.log(error)
    ElNotification({
      type: 'error',
      message: '获取课程信息失败',
    });
}
});

</script>

<script lang="ts">
export default {
  name: "Layout"
}
</script>

<style scoped>
.row1 {
  margin-top: 15px;
  margin-bottom: 10px;
}

.row2 {
  margin-top: 10px;
}

.out_header {
  position: relative;
  background: url(head-background.png) no-repeat;
  background-size: cover;
  box-shadow: --el-box-shadow-light;
}

.common-layout .el-aside {
  color: var(--el-text-color-primary);
  background-color: #005bac;
  height: 90%;
  /* 高度百分比 */
}

.common-layout .el-menu {
  border-right: none;
}

.common-layout .el-main {
  padding: 20px;
  background-color: #E5EAF3;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.layout_tabbar {
  width: 100%;
  /* 100%宽度 */
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #ffffff;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.layout_main {
  padding: 16px;
}

.scrollbar {
  height: 90%;
}

.layout_slidder {
  display: flex;
  flex-direction: column;
  font-weight: bold;
}

.lesson-name {
  font-family: "宋体";
  color: #fafafa;
}

.school-text {
  font-family: "宋体";
  color: #fafafa;
}

.teacherName-number {
  font-family: "宋体";
  color: #fafafa;
}

.semester {
  font-family: "宋体";
  color: #fafafa;
}

.right-align {
  text-align: right;
  /* 确保内容右对齐 */
}

.empty-row {
  height: 10px;
  /* 可以根据需要调整空行的高度 */
}
</style>