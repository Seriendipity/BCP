<template>
  <div class="Intro">
    <el-button type="primary" round
      style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;"
      @click="createHomework()">新建作业</el-button>
    <el-table :data="SendHomeworkData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      class="homeworkTable">
      <el-table-column label="是否发布" v-slot="scope" width="100px">
        <el-switch v-model="scope.row.sendStatus" active-color="#13ce66" inactive-color="#ff4949"@click="handleSend(scope.$index,scope.row)">
        </el-switch>
      </el-table-column>
      <el-table-column prop="homeworkNO" label="作业ID" width="130px">
      </el-table-column>
      <el-table-column prop="name" label="作业名称" width="130px">
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
        <el-button size="mini" :type="scope.row.judge === '已批改' ? 'success' : 'danger'"
          @click="handleDelete(scope.$index, scope.row)">
          {{ scope.row.judge==='true'?'已批改':'未批改' }}
        </el-button>
      </el-table-column>
      <el-table-column label="基本信息" v-slot="scope" width="100px">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
      </el-table-column>
      <el-table-column label="成绩" v-slot="scope" width="100px">
        <el-button size="mini" @click="handleGrade(scope.$index, scope.row)">获取</el-button>
      </el-table-column>
      <el-table-column label="附件" v-slot="scope" width="100px">
        <el-button size="mini" @click="handleFile(scope.$index, scope.row)">预览</el-button>
      </el-table-column>

    </el-table>



  </div>
</template>

<script>
import { ref, onMounted} from 'vue';
import { ElNotification,ElMessage } from 'element-plus';
import { requireTeacherSendHomework, updateHomeworkStatus } from '@/api/api';
import { useRouter } from 'vue-router';

const sendHomework = {
  setup() {
    let $router = useRouter()
    const SendHomeworkData = ref([
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
            homeworkNO:homework.homeworkNO,
            sendStatus: homework.postStatus,
            name: homework.homeworkDescription,
            starttime: homework.homeworkStartTime,
            endtime: homework.homeworkEndTime,
            submitted: homework.submitNumbers,
            number: homework.studentNumbers,
            judge: homework.correctStatus,
            file: homework.file,
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
      console.log(row)
      localStorage.setItem('homeworkNO', row.homeworkNO)
      $router.push({ name: 'editHomework' });
    };
    const handleGrade = (index, row) => {
      // 跳转到成绩页面，传递成绩的详细信息
      localStorage.setItem('homeworkNO', row.homeworkNO)
      $router.push({ name: 'getHomeworkFinalGrade' });
    };
    const handleFile = (index, row) => {
      // 跳转到附件预览页面
      localStorage.setItem('homeworkNO', row.homeworkNO)
      localStorage.setItem('fileURL', row.file)
      $router.push({ name: 'accessoryPreview' });
    };
    const handleSend = async (index, row) => {
      // 发布作业
      const formData = new FormData()
      formData.append('homeworkNo',row.homeworkNO)
      try{    
        const response = await updateHomeworkStatus(formData)
        if (response.code === 0) {
          ElMessage.success("作业发布状态更新成功");
        }
      }catch(error){
        
        ElNotification({
          type: 'error',
          message: '更新作业发布状态失败'
        });
      }
  


    };
    return {
      SendHomeworkData,
      search,
      handleEdit,
      handleGrade,
      handleFile,
      handleSend
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