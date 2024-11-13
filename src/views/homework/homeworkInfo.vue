<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="作业信息" :column="3" :size="size">
      <el-descriptions-item label="作业名称">{{ currentData.name }}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{ currentData.starttime }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">{{ currentData.endtime }}</el-descriptions-item>
      <el-descriptions-item label="提交人数">{{ currentData.submitted }}/{{ currentData.number }}</el-descriptions-item>

      <el-descriptions-item label="状态">
        <el-tag size="small" :type="currentData.check === '已完成' ? 'success' : 'danger'">{{ currentData.check }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>



    <el-row :gutter="20">
      <el-col :span="2">
        <!-- 为空格空出来的 -->
      </el-col>
      <el-col :span="10">
        <router-link to="/homeworkPreview" style="text-decoration: none;">
          <el-button type="primary" @click="previewAttachment" style="margin-left: 10px;" plain>预览附件</el-button>
        </router-link>
      </el-col>
      <el-col :span="12">
        <el-button type="primary" @click="saveData" round style="font-size: medium;font-weight:bold;">上传作业</el-button>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';

export default {
  setup() {
    const tableData = ref([
      { name: '感知机1', starttime: '2024-05-02 10:00:00', endtime: '2024-05-02 10:00:00', number: 45, submitted: 3, check: '未完成', value: false },
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

    onMounted(() => {
      // 初始化时设置当前数据
      currentData.value = tableData.value[0];
      formData.name = currentData.value.name; // 初始化名称
      formData.number = currentData.value.number; // 初始化应提交人数
    });

    const handleUpdateCheck = (status) => {
      formData.check = status;
    };

    const previewAttachment = () => {
      // 实现预览附件的逻辑
      console.log('预览附件');
    };

    const handleUpload = (response, file) => {
      // 处理上传成功的逻辑
      console.log('上传成功', response);
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
      // 假设 this.$message.success 可用
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