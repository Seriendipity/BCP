<template>
  <div class="common-layout">
    <el-container style="height: 100vh;">
      <el-header class="header">
        <el-row>
          <el-col>
            <span>1</span>
          </el-col>
        </el-row>
        <el-row :gutter="40">
          <el-col :span="20">
            <!-- 使用传递过来的课程名 -->
            <span class="lesson-name">{{ courseName }}</span>
          </el-col>
          <el-col :span="4">
            <!-- 使用传递过来的学院名 -->
            <span class="school-text">{{ establishCollege }}</span>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="16">
            <div class="teacherName-number">
              <!-- 使用传递过来的教师名、课程编号和课序号 -->
              <span>主讲教师：{{ teacherName }}</span>
              <el-divider direction="vertical" border-style="dashed" />
              <span>课程编号: {{ courseNo }}</span>
              <el-divider direction="vertical" border-style="dashed" />
              <span>课序号：{{ cid }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="semester">
              <!-- 使用传递过来的学期和教学周 -->
              <span>学期：{{ semester }}</span>
              <span>教学周：{{ week }}</span>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="200px" style="height: calc(100vh - 64px);">
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
        <el-main>
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

let LayOutSettingStore = useLayOutSettingStore();
let $route = useRoute();
let userStore = useUserStore();

function calculateTeachingWeek(startDate: any, currentDate = new Date()) {
  // 将开学日期转为 Date 对象
  const start = new Date(startDate);

  // 计算当前日期与开学日期之间的天数差
  const timeDiff = currentDate.getTime() - start.getTime();
  const dayDiff = Math.floor(timeDiff / (1000 * 3600 * 24)); // 将毫秒数转为天数

  // 计算教学周数，dayDiff / 7 表示当前处于第几周，向上取整
  const weekNumber = Math.ceil(dayDiff / 7);

  // 如果当前日期早于开学日期，返回第 0 周
  return weekNumber > 0 ? weekNumber : 0;
}

// 示例使用
const startDate = '2024-09-09';  // 开学日期为2024年9月9日
const currentDate = new Date();  // 当前日期为今天
const currentWeek = calculateTeachingWeek(startDate, currentDate);

// 获取从 URL 传递的 query 参数
let courseName = $route.query.courseName || '默认课程名称';
let establishCollege = $route.query.establishCollege || '默认学院名称';
let teacherName = $route.query.teacherName || '默认教师';
let courseNo = $route.query.courseNo || '默认课程编号';
let cid = $route.query.cid || '默认课序号';
let semester = $route.query.semester || '默认学期';
let week = currentWeek || '默认教学周';
</script>

<script lang="ts">
export default {
  name: "Layout"
}
</script>

<style scoped>
.header {
  position: relative;
  background: url(head-background.png) no-repeat;
  background-size: cover;
  box-shadow: --el-box-shadow-light;
}

.common-layout .el-aside {
  color: var(--el-text-color-primary);
  background-color: #005bac;
  height: calc(100vh - 64px);
  /* 假设header高度为64px */
}

.common-layout .el-menu {
  border-right: none;
}

.common-layout .el-main {
  padding: 20px;
  background-color: #E5EAF3;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  /* 添加阴影 */
  border-radius: 10px;
  /* 圆角边框 */
}

.layout_tabbar {
  width: 1240px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #ffffff;
  padding: 10px;
  border-radius: 10px;
  /* 圆角边框 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  /* 轻微阴影 */

}

.layout_main {
  padding: 16px;
  /* 根据需要调整内边距 */

}

.scrollbar {
  height: 100%;
  /* 确保滚动条占满空间 */
}

.layout_slidder {
  display: flex;
  flex-direction: column;
  height: 100%;
  font-weight: bold;
}

.lesson-name {
  font-family: "宋体";
  color: #fafafa;
}

.school-text {
  font-family: "宋体";
  color: #fafafa
}

.teacherName-number {
  font-family: "宋体";
  color: #fafafa
}

.semester {
  font-family: "宋体";
  color: #fafafa
}
</style>
