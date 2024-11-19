<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="作业信息" :column="3" :size="size">
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
        <el-button type="primary" @click="saveData" round
          style="font-size: medium;font-weight:bold;margin-right: 40px;">保存</el-button>
        <el-button type="primary" @click="goBack" round style="font-size: medium;font-weight:bold;">返回</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { createHomework } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const selectedFile = ref(null); // 存储选中的文件
    let $router = useRouter();
    const changedData = ref({});

    const currentData = ref({});
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
      const storedCourseId = localStorage.getItem('courseId');
      formData.append('Cid', storedCourseId)
      formData.append('startTime', changedData.value.homeworkStartTime)
      formData.append('endTime', changedData.value.homeworkEndTime)
      formData.append('homeworkGrade', changedData.value.scores)
      formData.append('homeworkDescription', changedData.value.homeworkDescription)
      formData.append('homeworkInfo', changedData.value.homeworkInfo)
      formData.append('file', selectedFile.value)
      try {
        const response = await createHomework(formData); // 连接后端更新文件
        if (response.code === 0) {
          ElMessage.success("新建作业成功");
        }
        $router.push("/CourseManagement/sendHomework" + `?cid=${storedCourseId}`);
      } catch (error) {
        console.log(error)
        console.error('新建作业失败', error);
        ElNotification({
          message: '新建作业失败，请重试',
          type: 'error',
        });
      }
      // 提示保存成功
      console.log('新建作业成功');
    };

    const goBack = () => {
      const storedCourseId = localStorage.getItem('courseId');
      $router.push("/CourseManagement/sendHomework" + `?cid=${storedCourseId}`);
    }

    return {

      changedData: changedData,
      currentData,
      saveData,
      selectedFile,
      handleChange,
      goBack,
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