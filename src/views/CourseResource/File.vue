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
import { reqFileList, reqUploadFile } from '@/api/api'; // 引入 API
import { ElMessage, ElNotification } from 'element-plus';

const fileList = ref<any[]>([]);
const selectedFile = ref<File | null>(null); // 存储选中的文件


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

// 处理文件选择
const handleChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files && target.files.length > 0) {
    selectedFile.value = target.files[0]; // 存储选中的文件
  }
};

// 上传文件处理
const handleSubmit = async () => {
  if (!selectedFile.value) {
    ElNotification({
      message: '请选择一个文件',
      type: 'error',
    });
    return;
  }
  const formData = new FormData();
  const storedCourseId = localStorage.getItem('courseId');

  if (storedCourseId) {
    formData.append('cid', storedCourseId); // 传递 cid
    formData.append('file', selectedFile.value); // 传递文件
  } else {
    ElNotification({
      message: '课程ID无效，请重试',
      type: 'error',
    });
    return;
  }

  try {
    const response = await reqUploadFile(formData); // 连接后端上传文件
    selectedFile.value = null; // 上传后清空选择的文件
    if (response.code === 0) {
      ElMessage.success("文件上传成功");
    }
  } catch (error) {
    console.error('上传文件失败', error);
    ElNotification({
      message: '上传文件失败，请重试',
      type: 'error',
    });
  }
};

// 下载文件
const downloadFile = (resource: any) => {
  try {
    window.open(resource.url); // 打开文件链接
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


input[type="file"] {
  margin-right: 10px;
  /* 增加输入框和按钮之间的间距 */
  border: 2px dashed #409eff;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.upload-button {
  background: linear-gradient(90deg, #66b1ff, #409eff);
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  writing-mode: horizontal-tb;
  /* 横向显示文字 */
}

.upload-button:hover {
  background: linear-gradient(90deg, #409eff, #66b1ff);
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.download-button {
  background: linear-gradient(90deg, #66b1ff, #409eff);
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.download-button:hover {
  background: linear-gradient(90deg, #409eff, #66b1ff);
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
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
</style>
