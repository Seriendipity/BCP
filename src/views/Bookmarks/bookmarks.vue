<template>
  <el-container>
    <el-header class="head">
      <el-row :gutter="20">
        <el-col :span="18">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <!-- 其他顶部导航代码... -->
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <el-container>
        <el-aside class="backleft">
          <!-- 个人信息和课程提醒代码... -->
        </el-aside>

        <el-main height="600px">
          <h1 style="text-align: left; font-weight: bold; margin-bottom: 10px; font-size: large; margin-top: -12px;">收藏夹
          </h1>
          <div class="scrollable">
            <div v-for="item in favorites" :key="item.id" class="grid-content bg-white" style="height: 90px;">
              <el-row :gutter="20">
                <el-col :span="17">
                  <h1 class="ziti03" style="margin-top: 5px;">{{ item.title }}</h1>
                  <h1 class="ziti04">{{ item.author }} - 上传时间: {{ item.uploadTime }}</h1>
                </el-col>
                <el-col :span="2">
                  <el-button type="primary" style="margin-top: 20px;" plain @click="preview(item)">预览</el-button>
                </el-col>
                <el-col :span="3">
                  <el-button type="primary" style="margin-top: 20px;" plain @click="download(item)">下载</el-button>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-main>

        <el-aside class="backright">
          <!-- 通知公告代码... -->
        </el-aside>
      </el-container>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqFavorites } from '@/api/api';
import { ElNotification } from 'element-plus';

const favorites = ref([]); // 收藏夹数据

// 获取收藏夹数据
onMounted(async () => {
  try {
    const response = await reqFavorites();
    favorites.value = response.data || [];
  } catch (error) {
    // 请求失败时设置默认模拟数据
    favorites.value = [
      { id: 1, title: '数据结构第一次课程笔记', author: '马海博', uploadTime: '2024-10-10 14:00' },
      { id: 2, title: '算法第3次作业答案', author: '张学琛', uploadTime: '2024-10-11 15:30' },
      { id: 3, title: '软件测试小测原题', author: '张胤麟', uploadTime: '2024-10-12 16:45' },
    ];
    ElNotification({
      type: 'warning',
      message: '获取收藏夹数据失败，已加载默认数据',
    });
  }
});

// 示例函数：预览和下载操作
const preview = (item) => {
  // 预览功能逻辑
};

const download = (item) => {
  // 下载功能逻辑
};
</script>


<style>
.head {
  background-color: #005bac;

}

.backmain1 {
  height: 770px;
  background-color: #eaf6ff;
}

.zitihead {
  text-align: left;
  font-weight: bold;
  font-size: 24px;
  margin-top: 15px;
  color: #ffffff;
}

.ziti01 {
  text-align: center;
  font-weight: 400;
  font-size: 14px;
  margin-bottom: 25px;
  margin-top: 10px;
}

.ziti02 {
  text-align: center;
  font-weight: bold;
  font-size: 16px;
}

.ziti03 {
  text-align: left;
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 16px;
  margin-left: 10px;
  padding-top: 10px;
}

.ziti04 {
  text-align: left;
  font-weight: 400;
  margin-bottom: 2px;
  font-size: 14px;
  margin-left: 10px;
}

.backleft {
  width: "350px";
  max-height: "550px";
  background-color: #eaf6ff;
}

.backright {
  width: "350px";
  height: "600px";
  background-color: #eaf6ff;
}


.whiteback {
  border-radius: 4px;
  max-height: 350px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

}

.whiteback2 {
  margin-top: 20px;
  border-radius: 4px;
  height: 200px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.whiteback3 {
  /* margin-top: 20px; */
  border-radius: 4px;
  height: 400px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.scrollable {
  height: 690px;
  /* 固定高度 */
  overflow-y: auto;
  /* 垂直方向上的滚动条 */
  overflow-x: hidden;
  padding-right: 10px;
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.bg-white {
  background: #ffffff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 30px
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  height: 250px;
  margin-bottom: 10px;

}

body>.el-container {
  margin-bottom: 40px;
}
</style>