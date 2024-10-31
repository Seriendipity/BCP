<template>
  <div class="file-layout">
    <el-row :gutter="20">
      <el-col :span="6" :offset="18" class="upload-col">
        <form id="uploadForm" @submit.prevent="handleSubmit">
          <div class="file-input-container">
            <input type="file" id="fileInput" @change="handleChange" />
            <button type="submit" class="upload-button">上传文件</button>
          </div>
        </form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24" v-for="(resource, index) in fileList" :key="index">
        <div class="file-row">
          <div class="file-info">
            <h2 class="file-name">{{ resource.name }}</h2>
          </div>
          <el-button class="download-button" type="primary" size="small" @click="() => {
            if (resource) {
              downloadFile(resource);
            } else {
              ElNotification({
                message: '文件链接无效',
                type: 'error',
              });
            }
          }">
            下载
            <el-icon>
              <Download />
            </el-icon>
          </el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import type { UploadProps, UploadUserFile } from 'element-plus';
import { reqFileDownload, reqFileList, reqUploadFile } from '@/api/api'; // 引入 API
import { ElNotification } from 'element-plus';

const fileList = ref<UploadUserFile[]>([]);
const uploadUrl = 'https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15'; // 上传接口

// 默认模拟数据
const defaultFiles = [
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'food2.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
];

onMounted(async () => {
  // 获取初始文件列表
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqFileList(storedCourseId);
    fileList.value = response.data || defaultFiles; // 使用后端数据或默认数据
  } catch (error) {
    console.error('获取文件列表失败', error);
    ElNotification({
      message: '获取文件列表失败，请重试',
      type: 'error',
    });
    fileList.value = defaultFiles; // 若获取失败，使用默认数据
  }
});

// 上传文件处理
const handleChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files && target.files.length > 0) {
    // 处理文件选择
  }
};

const handleSubmit = async () => {
  // 处理上传逻辑
};

const downloadFile = async (resource: any) => {
  try {
    const response = await reqFileDownload(resource.name);
    window.open(response.url); // 打开文件链接
  } catch (error) {
    ElNotification({
      message: '下载文件失败，请重试',
      type: 'error',
    });
  }
};
</script>

<style scoped>
.upload {
  margin-bottom: 20px;
  text-align: right;
}

.file-layout {
  height: 580px;
  border: 1px solid #dcdfe6;
  background-color: #f5f7fa;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
}

.upload-col {
  display: flex;
  justify-content: center;
  align-items: center;
}

.file-input-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

input[type="file"] {
  margin-bottom: 10px;
  border: 2px dashed #409eff;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  max-width: 250px;
}

.upload-button {
  background-color: #409eff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.upload-button:hover {
  background-color: #66b1ff;
}

.file-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ebeef5;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.file-info {
  flex-grow: 1;
}

.file-name {
  font-size: 16px;
  color: #333;
  margin: 0;
}

.download-button {
  transition: background-color 0.3s, color 0.3s;
}

.download-button:hover {
  background-color: #409eff;
  color: #fff;
}
</style>
