<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="作业信息" :column="3" :size="size">
      <el-descriptions-item label="作业名称">{{ currentData.homeworkDescription }}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{ currentData.homeworkStartTime }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">{{ currentData.homeworkEndTime }}</el-descriptions-item>
      <el-descriptions-item label="应提交人数">{{ currentData.studentNumbers }}</el-descriptions-item>
      <el-descriptions-item label="已提交人数">{{ currentData.submitNumbers }}</el-descriptions-item>
    </el-descriptions>

    <el-form :model="changedData" class="form" label-width="100px">
      <el-form-item label="作业名称">
        <el-input v-model="changedData.homeworkDescription" placeholder="输入作业名称" style="width: 50%;"></el-input>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker v-model="changedData.homeworkStartTime" type="datetime" placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="changedData.homeworkEndTime" type="datetime" placeholder="选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="满分">
        <el-input v-model="changedData.scores" type="studentNumbers" placeholder="输入满分" style="width: 15%;"></el-input>
      </el-form-item>
      <el-form-item label="作业内容">
        <el-input v-model="changedData.homeworkInfo" placeholder="输入作业内容" type="textarea"
          style="width: 60%;"></el-input>
      </el-form-item>
    </el-form>

    <el-row :gutter="20">
      <el-col :span="2">
        <!-- 为空格空出来的 -->
      </el-col>
      <el-col :span="7">
        <el-form :model="form">
        <div class="file-input-container">
        <input type="file" id="fileInput" @change="handleChange" />
      </div>
    </el-form>
      </el-col>
      <el-col :span="12">
        <el-button type="primary" @click="saveData" round style="font-size: medium;font-weight:bold;">保存</el-button>
      </el-col>
    </el-row>
  </div>

  <el-dialog v-model="previewVisible" title="预览" width="800px">
    <iframe class="homeworkFile" :src="homeworkSrc" width="100%" height="400px" style="border:none;"></iframe>
    <span slot="footer" class="dialog-footer">
      <el-button @click="previewVisible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { updateHomeworkSetting, editSingleHomework } from '@/api/api';
import { ElNotification,ElMessage } from 'element-plus';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const selectedFile = ref(null); // 存储选中的文件
    let $router = useRouter();
    const tableData = ref([
      {
        homeworkDescription: '感知机1',
        homeworkStartTime: '2024-05-02 10:00:00',
        homeworkEndTime: '2024-05-02 10:00:00',
        studentNumbers: 45,
        submitNumbers: 3,
        file: '',
      },
    ]);
    const changedData = ref({
      homeworkDescription: '感知机1',
      homeworkStartTime: '2024-05-02 10:00:00',
      homeworkEndTime: '2024-05-02 10:00:00',
      scores: 100,
      homeworkInfo: '见附件',
      file: '',
    });

    const currentData = ref({});
    const previewVisible = ref(false);
    onMounted(async () => {
      // 初始化时设置当前数据
      try {

        const homeworkNO = localStorage.getItem('homeworkNO');
        const response = await editSingleHomework(homeworkNO); // 请求后端老师布置作业数据
        if (response.code === 0) {
          currentData.value = response.data;
          changedData.value = response.data;
          selectedFile.value = response.data.file;
        } else {
          currentData.value = tableData.value[0];
        }
      } catch (error) {
        currentData.value = tableData.value[0];
        ElNotification({
          type: 'error',
          message: '获取布置作业数据失败'
        });
      }
    });

    const handleChange = (event) => {
  const target = event.target;
  if (target.files && target.files.length > 0) {
    selectedFile.value = target.files[0]; // 存储选中的文件
  }
  console.log(selectedFile.value)
};
    const saveData = async () => {
      // 更新 tableData 中对应的数据
      const formData = new FormData()
      const homeworkNO = localStorage.getItem('homeworkNO');
      formData.append('newStartTime', changedData.value.homeworkStartTime)
      formData.append('newEndTime', changedData.value.homeworkEndTime)
      formData.append('scores', changedData.value.scores)
      formData.append('homeworkNo', homeworkNO)
      formData.append('homeworkDesc', changedData.value.homeworkDescription)
      formData.append('homeworkInfo', changedData.value.homeworkInfo)
      formData.append('file', selectedFile.value)
      try {
        const response = await updateHomeworkSetting(formData); // 连接后端更新文件
        if (response.code === 0) {
          ElMessage.success("作业设置更新成功");
        }
        const storedCourseId = localStorage.getItem('courseId')
        $router.push("/CourseManagement/sendHomework" + `?cid=${storedCourseId}`);
      } catch (error) {
        console.log(error)
        console.error('更新作业设置失败', error);
        ElNotification({
          message: '更新作业设置失败，请重试',
          type: 'error',
        });
      }
      // 提示保存成功
      console.log('保存成功');
    };

    return {
      tableData,
      changedData: changedData,
      currentData,
      saveData,
      selectedFile,
      handleChange
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