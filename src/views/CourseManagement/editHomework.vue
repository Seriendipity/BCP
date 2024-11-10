<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="作业信息" :column="3" :size="size">
      <el-descriptions-item label="作业名称">{{ currentData.name }}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{ currentData.starttime }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">{{ currentData.endtime }}</el-descriptions-item>
      <el-descriptions-item label="应提交人数">{{ currentData.number }}</el-descriptions-item>
      <el-descriptions-item label="已提交人数">{{ currentData.submitted }}</el-descriptions-item>
      <el-descriptions-item label="批改状态">
        <el-tag size="small" :type="currentData.check === '已完成' ? 'success' : 'danger'">{{ currentData.check }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>

    <el-form :model="formData" class="form" label-width="100px">
      <el-form-item label="作业名称">
        <el-input v-model="formData.name" placeholder="输入作业名称" style="width: 50%;"></el-input>
      </el-form-item>
      <el-form-item label="应提交人数">
        <el-input v-model="formData.number" type="number" placeholder="输入应提交人数" style="width: 15%;"></el-input>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker v-model="formData.starttime" type="datetime" placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="formData.endtime" type="datetime" placeholder="选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="满分">
        <el-input v-model="formData.fullscore" type="number" placeholder="输入满分" style="width: 15%;"></el-input>
      </el-form-item>
      <el-form-item label="作业内容">
        <el-input v-model="formData.content" placeholder="输入作业内容" type="textarea" style="width: 60%;"></el-input>
      </el-form-item>
      <el-form-item label="批改">
        <el-button v-if="formData.check === '未完成'" type="success" @click="handleUpdateCheck('已完成')">已完成</el-button>
        <el-button v-else type="danger" @click="handleUpdateCheck('未完成')">未完成</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="20">
      <el-col :span="2">
        <!-- 为空格空出来的 -->
      </el-col>
      <el-col :span="3">
        <el-button type="primary" @click="previewAttachment" style="margin-left: 10px;" plain>预览附件</el-button>
      </el-col>
      <el-col :span="7">
        <el-upload class="upload-demo" action="#" :before-upload="handleUpload" :show-file-list="true">
          <el-button type="primary" plain>上传附件</el-button>
        </el-upload>
      </el-col>
      <el-col :span="12">
        <el-button type="primary" @click="saveData" round style="font-size: medium;font-weight:bold;">保存</el-button>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { reqUploadHomeworkFile } from '@/api/api';
import { ref, reactive, onMounted } from 'vue';

export default {
  setup() {
    const tableData = ref([
      {
        name: '感知机1',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 3,
        check: '未完成',
        value: false,
      },
    ]);

    const formData = reactive({
      name: '感知机1',
      starttime: '2024-05-02 10:00:00',
      endtime: '2024-05-02 10:00:00',
      fullscore: 100,
      number: 45,
      check: '未完成',
      content: '见附件',
    });

    const currentData = ref({});

    onMounted(async () => {
      // 初始化时设置当前数据
      try {

        const storedCourseId = localStorage.getItem('courseId');
        const response = await requireTeacherSendHomework(storedCourseId); // 请求后端老师布置作业数据
        if (response.code === 0) {
          currentData.value = response.value;
          formData.name = currentData.value.name; // 初始化名称
          formData.number = currentData.value.number; // 初始化应提交人数
        } else {

        }
      } catch (error) {
        currentData.value = tableData.value[0];
        ElNotification({
          type: 'error',
          message: '获取布置作业数据失败'
        });
      }
    });

    const handleUpdateCheck = (status) => {
      formData.check = status;
    };

    const previewAttachment = () => {
      // 实现预览附件的逻辑
      console.log('预览附件');
    };

    const handleUpload = async (file) => {
      const formData = new FormData();
      const storedCourseId = localStorage.getItem('courseId');
      formData.append('file', file.raw);
      formData.append('cid', storedCourseId); // 传递 cid
      try {
        const response = await reqUploadHomeworkFile(formData);
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

    const saveData = () => {
      // 更新 tableData 中对应的数据
      currentData.value.name = formData.name;
      currentData.value.starttime = formData.starttime;
      currentData.value.endtime = formData.endtime;
      currentData.value.number = formData.number;
      currentData.value.fullscore = formData.fullscore;
      currentData.value.check = formData.check;

      // 提示保存成功
      console.log('保存成功');
    };

    return {
      tableData,
      formData,
      currentData,
      handleUpdateCheck,
      previewAttachment,
      handleUpload,
      saveData,
    };
  },
};
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: auto;
  /* 自适应高度 */
  border: groove;
  overflow: hidden;
  max-width: 100%;
  /* 最大宽度100% */
  padding: 20px;
  /* 内边距 */
}

.form {
  margin-top: 20px;
}
</style>