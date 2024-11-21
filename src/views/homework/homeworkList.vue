<template>
  <div class="Intro">
    <el-table :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      class="homeworkTable">
      <el-table-column prop="name" label="作业名称">
      </el-table-column>
      <el-table-column prop="homeworkNO" label="作业ID" width="130px">
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
          <el-tag size="small" :type="scope.row.ifsubmit === '已提交' ? 'success' : 'danger'">{{ scope.row.ifsubmit
            }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" v-slot="scope" width="240px">
        <el-button size="mini" type="primary" @click="handlePreview(scope.$index, scope.row)">预览</el-button>
        <el-button size="mini" type="danger" @click="handleUpload(scope.$index, scope.row)"
          style="margin-left: 10px;">上传</el-button>
        <el-button size="mini" type="success" @click="checkBystu(scope.$index, scope.row)"
          style="margin-left: 10px;">互评</el-button>
      </el-table-column>

    </el-table>
  </div>

  <el-dialog v-model="dialogVisible" title="上传作业">
    <el-form :model="form">
      <el-form-item label="作业内容" prop="content">
        <el-input class="homeworkContent no-border" v-model="homeworkAnswer" placeholder="请输入作业答案"></el-input>
      </el-form-item>
      <div class="file-input-container">
        <input type="file" id="fileInput" @change="handleChange" />
      </div>
      <template v-slot:tip>
        <div class="el-upload__tip" type="success">支持扩展名：.pdf/.doc/.docx</div>
      </template>
    </el-form>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadFile">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { requireStudentHomework, reqUploadHomework } from '@/api/api';
import { ref, onMounted } from 'vue';
import { ElMessage, ElNotification,ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router';


const homeworkList = {
  setup() {
    let $router = useRouter()
    const homeworkListData = ref([
      {
        name: '感知机1',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 44,
        submitted: 3,
        grade: null,
        ifsubmit: '未完成',
        checkbystuStatus: false
      },
      {
        name: '感知机2',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 45,
        grade: 88,
        ifsubmit: '已完成',
        checkbystuStatus: false
      },
      {
        name: '感知机3',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 44,
        grade: 65,
        ifsubmit: '已完成',
        checkbystuStatus: true
      },
      {
        name: '感知机4',
        starttime: '2024-05-02 10:00:00',
        endtime: '2024-05-02 10:00:00',
        number: 45,
        submitted: 45,
        grade: 90,
        ifsubmit: '已完成',
        checkbystuStatus: false
      }
    ]);

    const search = ref('');
    const dialogVisible = ref(false);
    const homeworkAnswer = ref('');
    const selectedFile = ref(null);
    const selectedHomeworkNo = ref();
    const router = useRouter()

    const handlePreview = (index, row) => {
      if (row.ifsubmit === '未提交') {
        ElMessage.error('作业未提交，不可预览')
      } else {
        localStorage.setItem('homeworkNO', row.homeworkNO)
        localStorage.setItem('homeworkPath', row.homeworkPath)
        $router.push({ name: 'homeworkPreview' });
      }

    };

    const handleChange = (event) => {
      const target = event.target;
      if (target.files && target.files.length > 0) {
        selectedFile.value = target.files[0]; // 存储选中的文件
      }
      console.log(selectedFile.value)
    };

    const uploadFile = async () => {
      if (!selectedFile.value) {
        ElNotification({
          message: '请选择一个文件',
          type: 'error',
        });
        return;
      }
      console.log(selectedHomeworkNo.value)
      const formData = new FormData();
      const studentNo = localStorage.getItem('userId')
      const submitTime = getFormattedDate()
      const comment = ref();
      formData.append('file', selectedFile.value); // 传递文件
      formData.append('submitDescription', homeworkAnswer.value);
      formData.append('homeworkNo', selectedHomeworkNo.value);
      formData.append('studentNo', studentNo);
      formData.append('submitTime', submitTime);
      formData.append('comment', comment);
      try {
        const response = await reqUploadHomework(formData); // 连接后端上传文件
        selectedFile.value = null; // 上传后清空选择的文件
        selectedHomeworkNo.value = null;
        if (response.code === 0) {
          ElNotification({
            message: "作业上传成功",
            type: 'success',
          });
          window.location.reload();
        }
      } catch (error) {
        console.log(error);
        ElNotification({
          message: '上传作业失败，请重试',
          type: 'error',
        });
      }
      homeworkAnswer.value = '';
      selectedFile.value = '';
      dialogVisible.value = false;
    };

    const handleUpload = (index, row) => {
      console.log(row.homeworkNO)
      selectedHomeworkNo.value = row.homeworkNO
      console.log(selectedHomeworkNo.value)
      dialogVisible.value = true;
      //TODO: 文件上传
    };

    const checkBystu = (index, row) => {
      console.log(row.homeworkNO)
      // selectedHomeworkNo.value = row.homeworkNO
      // console.log(selectedHomeworkNo.value)
      // dialogVisible.value = true;
      //TODO: 互评传输作业数据并跳转
      if (row.checkbystuStatus == true){
        router.push('/homework/hwCheckBystuInfo');
      }else{
        // 使用ElMessageBox来弹出提醒
    ElMessageBox.alert('这次作业不需互评', '提醒', {
      confirmButtonText: '确定',
      type: 'warning',
    });
      }
      
    };

    onMounted(async () => {
      try {
        const storedCourseId = localStorage.getItem('courseId');
        const response = await requireStudentHomework(storedCourseId); // 请求后端作业数据
        if (response.code === 0) {
          // 将后端数据转为数组格式并赋值给 tableData
          homeworkListData.value = Object.values(response.data).map(homework => ({
            homeworkNO:homework.homeworkNO,
            name: homework.homeworkDescription,
            starttime: homework.homeworkStartTime,
            endtime: homework.homeworkEndTime,
            submitted: homework.submitNumbers,
            number: homework.studentNumbers,
            grade: homework.grade,
            ifsubmit: homework.status,
            homeworkPath:homework.homeworkPath,
          }));
        } else {
          ElNotification({
            type: 'error',
            message: response.message || '获取作业数据失败'
          });
        }
      } catch (error) {
        console.log(error)
        ElNotification({
          type: 'error',
          message: '获取作业数据失败'
        });
      }
    });

    function getFormattedDate() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要+1
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    return {
      tableData: homeworkListData,
      search,
      handlePreview,
      handleUpload,
      handleChange,
      uploadFile,
      getFormattedDate,
      checkBystu,
      dialogVisible,
      homeworkAnswer,


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