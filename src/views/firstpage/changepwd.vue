<template>
  <el-container>
    <el-header class="head">
      <el-row :gutter="20">
        <el-col :span="21">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content">
            <router-link to="/myinformation" style="text-decoration: none;">
              <div style="margin-top: 10px"><el-avatar :size="40" :src="userInfo.avatarUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1
              style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
              {{ userInfo.userName }}</h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <!-- 修改密码 -->
      <div class="whiteback4">
        <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px; font-size: larger;">修改密码</h1>
        <el-row :gutter="20">
          <el-col :span="12">
            <div style="margin-top: 80px;text-align: center;"><el-avatar :size="200"
                :src="userInfo.avatarUrl"></el-avatar>
            </div>
            <h1 class="ziti03" style="text-align: center;margin-top: 20px;">{{ userInfo.userName }}</h1>
          </el-col>
          <el-col :span="12">
            <div class="grid-content" style="margin-top: 15%;">
              <h1 class="ziti03" style="text-align: left;margin-top: 20px;">请输入原密码：</h1>
              <el-input placeholder="请输入密码" v-model="currentPassword" show-password style="width: 50%;"></el-input>
              <h1 class="ziti03" style="text-align: left;margin-top: 20px;">请输入新密码：</h1>
              <el-input placeholder="请输入密码" v-model="newPassword" show-password style="width: 50%;"></el-input>
              <h1 class="ziti03" style="text-align: left;margin-top: 20px;">再次输入新密码：</h1>
              <el-input placeholder="请输入密码" v-model="confirmPassword" show-password style="width: 50%;"></el-input>
            </div>
            <el-button type="primary" style="margin-left: 20%;" @click="confirmChange">确认修改</el-button>
          </el-col>
        </el-row>
      </div>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElNotification,ElMessage } from 'element-plus';
import { reqUpdatePassword,reqUserInfo,requireAvatar } from '@/api/api';

const userInfo = ref({
  avatarUrl: '',
  userId: '',
  userName: '',
  identity: '',
  dept: '',
  email: '',
});

const mockData = {
  avatarUrl: 'src/assets/images/example.jpg', // 模拟头像URL
  userId: 22300000,
  userName: '张三',
  identity: '学生',
  dept: '软件学院',
  email: '22300000@bjtu.edu.cn',
};

const currentPassword = ref(''); // 原密码
const newPassword = ref(''); // 新密码
const confirmPassword = ref(''); // 确认新密码

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    const response = await reqUserInfo();
    userInfo.value = response.data; // 从后端获取用户信息
    const responseAvatar = await requireAvatar();
    userInfo.value.avatarUrl = responseAvatar.data;
    console.log('用户信息已从后端获取');
  } catch (error) {
    console.error('获取用户信息失败', error);
    userInfo.value = mockData; // 使用模拟数据
    ElNotification({
      type: 'error',
      message: '获取用户信息失败',
    });
  }
};

// 组件挂载时获取用户信息
onMounted(() => {
  fetchUserInfo();
});
const confirmChange = async () => {
  // 处理密码修改的逻辑
  console.log('原密码:', currentPassword.value);
  console.log('新密码:', newPassword.value);
  console.log('确认密码:', confirmPassword.value);

  // 简单的密码确认检查
  if (newPassword.value !== confirmPassword.value) {
    ElNotification.error('新密码与确认密码不一致');
    return;
  }

  try {
    const response = await reqUpdatePassword({
      currentPassword: currentPassword.value,
      newPassword: newPassword.value,
      confirmPassword: confirmPassword.value,
    });

    if (response.code === 0) {
      ElMessage.success('密码修改成功');
    } else {
      ElNotification.error(response.message || '密码修改失败');
    }
  } catch (error) {
    console.error('修改密码请求失败', error);
    ElNotification.error('修改密码失败，请重试');
  }
};

</script>

<style>
.head {
  background-color: #005bac;
}

.backmain1 {
  background-color: #eaf6ff;
  height: 774px;
}

.zitihead {
  text-align: left;
  font-weight: bold;
  font-size: 24px;
  margin-top: 15px;
  color: #ffffff;
}

.ziti03 {
  text-align: left;
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 16px;
  margin-left: 10px;
  padding-top: 10px;
}

.whiteback4 {
  border-radius: 4px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding-bottom: 10px;
  align-items: center;
  height: 550px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  height: 250px;
  margin-bottom: 10px;
}
</style>
