<template>
  <div class="file-layout">
    <el-row :gutter="20">
      <el-col :span="6" :offset="18">
        <el-upload class="upload" :action="uploadUrl" :on-change="handleChange" :file-list="fileList">
          <el-button type="primary">点击上传文件</el-button>
          <template #tip>
            <div class="el-upload__tip">一次只上传一个文件</div>
          </template>
        </el-upload>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24" v-for="(resource, index) in fileList" :key="index">
        <div class="file-row">
          <div class="file-info">
            <h2 class="file-name">{{ resource.name }}</h2>
          </div>
          <el-button class="download-button" type="primary" size="small" @click="() => downloadFile(resource)">
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
import type { UploadProps } from 'element-plus';
import { reqFileDownload, reqFileList, reqUploadFile } from '@/api/api'; // 引入 API
import { ElNotification } from 'element-plus';

// 定义 UploadUserFile 类型
interface UploadUserFile {
  name: string;
  url: string;
}

const fileList = ref<UploadUserFile[]>([]);
const uploadUrl = 'https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15'; // 上传接口

const defaultFiles: UploadUserFile[] = [
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
  try {
    const storedCourseId = localStorage.getItem('courseId');
    const response = await reqFileList(storedCourseId);
    fileList.value = response.data.map((file: any) => ({
      name: file.filename,
      url: file.path || '', // 确保 url 至少为一个空字符串
    })) || defaultFiles;
  } catch (error) {
    console.error('获取文件列表失败', error);
    ElNotification({
      message: '获取文件列表失败，请重试',
      type: 'error',
    });
    fileList.value = defaultFiles;
  }
});

// 上传文件处理
const handleChange: UploadProps['onChange'] = async (uploadFile) => {
  try {
    const response = await reqUploadFile(uploadFile);
    const newFile: UploadUserFile = {
      name: response.data.filename,
      url: response.data.path || '', // 确保 url 至少为一个空字符串
    };
    fileList.value.push(newFile);
  } catch (error) {
    console.error('上传文件失败', error);
    ElNotification({
      message: '上传文件失败，请重试',
      type: 'error',
    });
  }
};

// 下载文件
const downloadFile = async (resource: UploadUserFile) => {
  try {
    const response = await reqFileDownload(resource.name);
    window.open(response.url);
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

.el-upload__tip {
  font-size: 12px;
  color: #999;
}

.el-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}
</style>
