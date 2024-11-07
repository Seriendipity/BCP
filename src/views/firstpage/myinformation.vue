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
            <router-link to="/firstpage" style="text-decoration: none;">
              <div style="margin-top: 10px">
                <el-avatar v-if="userInfo.avatarUrl" :size="40" :src="userInfo.avatarUrl"></el-avatar>
              </div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/firstpage" style="text-decoration: none;">
            <h1
              style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
              {{ userInfo.userName }}</h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <!-- 学生信息 -->
      <div class="whiteback5">
        <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px; font-size: larger;">个人信息</h1>
        <el-row :gutter="20">
          <el-col :span="12">
            <div>
              <div style="margin-top: 80px;text-align: center;">
                <el-avatar v-if="userInfo.avatarUrl" :size="200" :src="userInfo.avatarUrl"></el-avatar>
              </div>
              <el-button type="big" style="margin-top: 15px;margin-left: 43%;" @click="openDialog">更改头像</el-button>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content" style="margin-top: 15%;">
              <h1 class="ziti02" style="text-align: left;">身份：{{ userInfo.identity }}</h1>
              <h1 class="ziti02" style="text-align: left;">姓名：{{ userInfo.userName }}</h1>
              <h1 class="ziti02" style="text-align: left;">学号：{{ userInfo.userId }}</h1>
              <h1 class="ziti02" style="text-align: left;">学院：{{ userInfo.dept }}</h1>
              <h1 class="ziti02" style="text-align: left;">邮箱：{{ userInfo.email }}</h1>
            </div>
          </el-col>
        </el-row>
        <router-link to="/changepwd" style="text-decoration: none;">
          <el-button type="primary" style="margin-left: 55%;">修改密码</el-button>
        </router-link>
      </div>

      <!-- 上传文件的对话框 -->
      <el-dialog title="更改头像" v-model="dialogVisible" width="30%" :before-close="handleClose">
        <el-row :gutter="20">
          <el-col :span="18">
            <el-upload class="avatar-uploader" :before-upload="beforeAvatarUpload" :on-change="handleAvatarChange"
              show-file-list="false">
              <el-button size="normal" type="primary">点击上传</el-button>
            </el-upload>
            <div v-if="previewImage" class="image-preview">
              <img :src="previewImage" alt="预览图" />
            </div>
          </el-col>
          <el-col :span="6">
            <el-button @click="confirmUpload" type="primary">确认上传</el-button>
            <el-button @click="dialogVisible = false">取 消</el-button>
          </el-col>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件 且不超过500kb</div>
        </el-row>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script lang="js" setup>
import { ref, onMounted } from 'vue';
import { reqUserInfo, reqUpdateAvatar, requireAvatar } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';

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

const dialogVisible = ref(false); // 定义对话框的可见性
const previewImage = ref(''); // 预览图像的URL
const selectedFile = ref(null); // 存储选中的文件

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    const response = await reqUserInfo();
    const responseAvatar = await requireAvatar();
    userInfo.value = response.data; // 从后端获取用户信息
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

// 打开上传头像对话框
const openDialog = () => {
  dialogVisible.value = true; // 打开弹出框
  previewImage.value = ''; // 重置预览图像
  selectedFile.value = null; // 重置选中的文件
};

// 处理文件选择变化
const handleAvatarChange = (file) => {
  console.log(file.raw.type); // 使用 file.raw 获取类型
  const isJPG = file.raw.type === 'image/jpeg';
  const isPNG = file.raw.type === 'image/png';

  if (isJPG || isPNG) {
    previewImage.value = URL.createObjectURL(file.raw); // 预览图像
    selectedFile.value = file.raw; // 保存选中的文件
  } else {
    ElNotification.error('上传头像图片只能是 JPG/PNG 格式!');
    previewImage.value = ''; // 重置预览图像
    selectedFile.value = null; // 重置选中的文件
  }
};


// 确认上传
const confirmUpload = async () => {
  if (!selectedFile.value) {
    ElNotification.error('请先选择一个文件!');
    return;
  }

  try {
    const formData = new FormData();
    formData.append('file', selectedFile.value); // 添加文件到 FormData

    const response = await reqUpdateAvatar(formData); // 上传文件
    if (response.code === 0) {
      userInfo.value.avatarUrl = previewImage.value; // 更新用户头像
      ElMessage.success("头像修改成功");
    }
  } catch (error) {
    console.error('头像修改失败', error);
    ElNotification({
      message: '头像修改失败，请重试',
      type: 'error',
    });
  }

  dialogVisible.value = false; // 关闭对话框
};

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false; // 关闭对话框
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

.ziti02 {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 10px;
  margin-top: 10px;
}

.whiteback5 {
  border-radius: 4px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding-bottom: 10px;
  align-items: center;
  height: 550px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  margin-bottom: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.image-preview {
  margin-top: 10px;
  text-align: center;
}

.image-preview img {
  max-width: 100%;
  max-height: 200px;
  border-radius: 4px;
}
</style>
