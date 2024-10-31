<template>
  <div class="file-layout">
    <el-row :gutter="20">
      <el-col :span="6" :offset="18">
        <el-upload class="upload" :action="uploadUrl" :on-change="handleChange">
          <el-button type="primary">Click to upload</el-button>
          <template #tip>
            <div class="el-upload__tip">一次只上传一个文件</div>
          </template>
        </el-upload>
      </el-col>
    </el-row>
    <el-table class="file" :data="fileList" style="width: 100%">
      <el-table-column fixed prop="name" label="Name" width="1080" />
      <el-table-column fixed="right" label="Operations">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="downloadFile(row.url)" round>
            <span>Download</span>
            <el-icon>
              <Download />
            </el-icon>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import type { UploadProps, UploadUserFile } from 'element-plus';
import { reqFileList, reqUploadFile } from '@/api/api'; // 引入 API

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
    fileList.value = defaultFiles; // 若获取失败，使用默认数据
  }
});

// 上传文件处理
const handleChange: UploadProps['onChange'] = async (uploadFile) => {
  // 假设上传成功后，我们将上传的文件添加到 fileList
  try {
    const response = await reqUploadFile(uploadFile); // 连接后端上传文件
    const newFile = {
      name: response.data.filename,
      url: response.data.path,
    };
    fileList.value.push(newFile); // 将新文件添加到 fileList
  } catch (error) {
    console.error('上传文件失败', error);
  }
};

// 下载文件
const downloadFile = (url: string) => {
  window.open(url); // 打开文件链接
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
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
}

.file {
  width: 100%;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
  border-collapse: separate;
  border-spacing: 0 10px;
}

.el-table th {
  background-color: #f2f6fc;
  color: #303133;
  font-weight: bold;
}

.el-table td {
  background-color: #fff;
  border-bottom: 1px solid #ebeef5;
}

.el-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}

.el-button:hover {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.fileList::-webkit-scrollbar {
  width: 8px;
}

.fileList::-webkit-scrollbar-thumb {
  background-color: #c1c1c1;
  border-radius: 10px;
}
</style>

<!-- 
{
  name: 'food.jpeg',
  url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
},
{
  name: 'food2.jpeg',
  url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
} -->
