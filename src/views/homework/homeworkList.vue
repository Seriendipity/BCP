<template>
  <div class="Intro">
    <router-link to="/homeworkInfo" style="text-decoration: none;">
      <el-table :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        class="homeworkTable">
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
        <el-table-column prop="grade" label="成绩" width="100px">
        </el-table-column>
        <el-table-column label="状态" width="100px">
          <template v-slot="scope">
            <el-tag size="small" :type="scope.row.ifsubmit === '已完成' ? 'success' : 'danger'">{{ scope.row.ifsubmit
              }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" v-slot="scope" width="240px">
          <router-link to="/homeworkPreview" style="text-decoration: none;">
            <el-button size="mini" type="primary" @click="handlePreview(scope.$index, scope.row)">预览</el-button>
          </router-link>
          <el-button size="mini" type="danger" @click="handleUpload(scope.$index, scope.row)"
            style="margin-left: 10px;">上传</el-button>

        </el-table-column>

      </el-table></router-link>



  </div>
</template>

<script>
import { requireStudentHomework } from '@/api/api';
import { ref, onMounted } from 'vue';
import { ElNotification } from 'element-plus';
import { useRouter } from 'vue-router';

let $router = useRouter()
const homeworkList = {
  setup() {
    const homeworkListData = ref([
      {
        name: '感知机1',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 44,
        submitted: 3,
        grade: null,
        ifsubmit: '未完成'
      },
      {
        name: '感知机2',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 45,
        grade: 88,
        ifsubmit: '已完成'
      },
      {
        name: '感知机3',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 44,
        grade: 65,
        ifsubmit: '已完成'
      },
      {
        name: '感知机4',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 45,
        grade: 90,
        ifsubmit: '已完成'
      }
    ]);

    const search = ref('');


    const handlePreview = (index, row) => {
      $router.push({ name: 'homeworkPreview' });
    };

    const handleUpload = (index, row) => {
      TODO: 文件上传
    };
    onMounted(async () => {
      try {
        const storedCourseId = localStorage.getItem('courseId');
        const response = await requireStudentHomework(storedCourseId); // 请求后端作业数据
        if (response.code === 0) {
          // 将后端数据转为数组格式并赋值给 tableData
          homeworkListData.value = Object.values(response.data).map(homework => ({
            name: homework.homeworkDescription,
            starttime: homework.homeworkStartTime,
            endtime: homework.homeworkEndTime,
            submitted: homework.submitNumbers,
            number: homework.studentNumbers,
            grade: homework.grade,
            ifsubmit: homework.status,
          }));
        } else {
          ElNotification({
            type: 'error',
            message: response.message || '获取作业数据失败'
          });
        }
      } catch (error) {
        ElNotification({
          type: 'error',
          message: '获取作业数据失败'
        });
      }
    });

    return {
      tableData: homeworkListData,
      search,
      handlePreview,
      handleUpload,
    };
  }
};

export default homeworkList;

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