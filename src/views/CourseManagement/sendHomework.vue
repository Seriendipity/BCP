<template>
  <div class="Intro">
    <el-button type="primary" round
      style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
      @click="createHomework()">新建作业</el-button>
    <el-table :data="SendHomeworkData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      class="homeworkTable">
      <el-table-column label="是否发布" v-slot="scope">
        <el-switch v-model="scope.row.sendStatus" active-color="#13ce66" inactive-color="#ff4949">
        </el-switch>
      </el-table-column>
      <el-table-column prop="name" label="作业名称">
      </el-table-column>
      <el-table-column prop="starttime" label="作业开始时间" width="180px">
      </el-table-column>
      <el-table-column prop="endtime" label="作业结束时间" width="180px">
      </el-table-column>
      <el-table-column label="提交人数" width="100px">
        <template v-slot="scope">
          {{ scope.row.submitted }}/{{ scope.row.number }}
        </template>
      </el-table-column>
      <el-table-column label="批改" v-slot="scope" width="100px">
        <el-button size="mini" :type="scope.row.judge === '已完成' ? 'success' : 'danger'"
          @click="handleDelete(scope.$index, scope.row)">
          {{ scope.row.judge }}
        </el-button>
      </el-table-column>
      <el-table-column label="基本信息" v-slot="scope" width="150px">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
      </el-table-column>


    </el-table>



  </div>
</template>

<script>
import { ref, onMounted, reactive } from 'vue';
import { ElNotification } from 'element-plus';
import { requireTeacherSendHomework } from '@/api/api';
import { useRouter } from 'vue-router';

const sendHomework = {
  setup() {
    let $router = useRouter()
    const SendHomeworkData = reactive([
      { homeworkNO: 'H001', sendStatus: false, name: '感知机1', starttime: '2024-05-02 10:00:00', endtime: '2024-05-02 10:00:00', number: 44, submitted: 3, judge: '未完成' },
      { homeworkNO: 'H002', sendStatus: false, name: '感知机2', starttime: '2024-05-02 10:00:00', endtime: '2024-05-02 10:00:00', number: 45, submitted: 45, judge: '已完成' },
      { homeworkNO: 'H003', sendStatus: false, name: '感知机3', starttime: '2024-05-02 10:00:00', endtime: '2024-05-02 10:00:00', number: 45, submitted: 44, judge: '已完成' },
      { homeworkNO: 'H004', sendStatus: true, name: '感知机4', starttime: '2024-05-02 10:00:00', endtime: '2024-05-02 10:00:00', number: 45, submitted: 45, judge: '已完成' }
    ]);
    onMounted(async () => {
      try {
        const storedCourseId = localStorage.getItem('courseId');
        const response = await requireTeacherSendHomework(storedCourseId); // 请求后端老师布置作业数据
        if (response.code === 0) {
          // 将后端数据转为数组格式并赋值给 SendHomeworkData
          SendHomeworkData.value = Object.values(response.data).map(homework => ({
            sendStatus: homework.postStatus,
            name: homework.homeworkDescription,
            starttime: homework.homeworkStartTime,
            endtime: homework.homeworkEndTime,
            submitted: homework.submitNumbers,
            number: homework.studentNumbers,
            judge: homework.correctStatus,
          }));
        } else {
          ElNotification({
            type: 'error',
            message: response.message || '获取布置作业数据失败'
          });
        }
      } catch (error) {
        ElNotification({
          type: 'error',
          message: '获取布置作业数据失败'
        });
      }
    });

    const search = ref('');

    const handleEdit = (index, row) => {
      // 跳转到编辑页面，传递该作业的详细信息
      localStorage.setItem('homeworkNO', row.homeworkNO)
      $router.push({ name: 'editHomework' });
    };
    return {
      SendHomeworkData,
      search,
      handleEdit,
    };
  }
};

export default sendHomework;

</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 580px;
  border: groove;
  overflow: hidden;
  max-width: 100%;
  /* 最大宽度100% */

}

.homeworkTable {
  table-layout: auto;
  padding: 10px;
  border-radius: 20px;
}
</style>