<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="作业信息" :column="1" :size="size">
      <el-descriptions-item label="作业名称">{{ currentData.homeworkDescription }}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{ currentData.homeworkStartTime }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">{{ currentData.homeworkEndTIme }}</el-descriptions-item>
      <el-descriptions-item label="作业详情">{{ currentData.homeworkInfo }}</el-descriptions-item>
      <el-descriptions-item label="满分：">{{ currentData.scores }}</el-descriptions-item>
    </el-descriptions>

    <el-row :gutter="20">
      <el-col :span="2">
        <!-- 为空格空出来的 -->
      </el-col>
      <el-col :span="10">
        <el-button type="primary" @click="previewAttachment()" style="margin-left: 10px;" plain>预览附件</el-button>
      </el-col>
      <el-col :span="10">
        <el-button type="primary" @click="goBackToList()" style="margin-left: 10px;" plain>返回</el-button>
      </el-col>
    </el-row>
  </div>

  <el-dialog v-model="previewVisible" title="预览" width="800px">
    <iframe class="notes" :src="homeworkSrc" width="100%" height="400px" style="border:none;"></iframe>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="previewVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus';
import { reqSingleHomework } from '@/api/api';
export default {
  setup() {
    let $router = useRouter()
    const homeworkSrc = ref('https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html')
    const tableData = ref([
      {
        homeworkDescription: '感知机1', homeworkStartTime: '2024-05-02 10:00:00', homeworkEndTIme: '2024-05-02 10:00:00', number: 45, submitted: 3, check: '未完成', value: false, homeworkInfo: '作业介绍',
        scores: 100,
      },
    ]);

    const previewVisible = ref(false);
    const currentData = ref({});

    onMounted(async () => {
      // 初始化时设置当前数据
      try {
        const homeworkNO = localStorage.getItem('homeworkNO');
        const response = await reqSingleHomework(homeworkNO); // 请求后端老师布置作业数据
        if (response.code === 0) {
          currentData.value = response.data;
          homeworkSrc.value = response.data.file;
        }
      } catch (error) {
        currentData.value = tableData.value[0];
        console.log(error)
        ElNotification({
          type: 'error',
          message: '获取布置作业数据失败'
        });
      }



    });


    const previewAttachment = () => {
      previewVisible.value = true;
    };

    const goBackToList = () => {
      $router.push({ name: 'homeworkList' })

    }




    return {
      tableData,
      currentData,
      previewAttachment,
      previewVisible,
      homeworkSrc,
      goBackToList,
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
  display: flex;
  flex-direction: column;
  /* 垂直排列 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;
  /* 垂直居中 */
  height: 500px;
  /* 占满整个视口高度 */
  /* 内边距 */
}

.form {
  margin-top: 20px;
}

.margin-top {
  margin-bottom: 20px;
  /* 调整标题与按钮之间的间距 */
}

.button-row {
  display: flex;
  /* 使按钮行也居中 */
  justify-content: center;
  /* 水平居中 */
}
</style>