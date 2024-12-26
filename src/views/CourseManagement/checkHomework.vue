<template>
  <el-container>
    <el-aside width="200px">
      <div class="homework-scrollable">
          <div  v-for="homework in homeworks" :key="homework.homeworkNo">
            <el-row :gutter="20">
          <el-col :span="24"@click="updateSrc(homework.studentNo)" >
                <h2 class="homework-nameid">{{ homework.studentName }} {{ homework.studentNo }}</h2>
                <p class="homework-time" style="margin-top: 10px;">{{ homework.submitTime }} 提交</p>
              <el-tag :type="homework.status === true ? 'success' : 'warning'" style="margin-top: 5px;float: right;">
                {{ homework.status === true ? '已批改' : '未批改' }}:{{ homework.grade }}
              </el-tag>
              <el-divider style="margin-top: 40px;"></el-divider>
          </el-col>
        </el-row>
      </div>
      </div>
    </el-aside>
    <el-main>
      <iframe class="preview_back" :src="homeworkSrc" width="100%" height=650px style="margin-top: -20px;border:none;"></iframe>
    </el-main>
    <el-aside width="200px">
      
      <div class="bg-reply">
        <h1 class="word-reply">评语</h1>
        <textarea class="reply-input " v-model="currentreply" />
      </div>
      <div style="margin-top: 60px;"></div>
      <!-- 分隔 -->
      <div class="bg-inputgrade">
        <h1 class="word-reply">打分</h1>
        <textarea class="grade-input " v-model="currentgrade" />
      </div>
      <el-button type="primary" style="margin-top: 5px; float: right;" @click="submit()">提交</el-button>
      <div>
      <el-button type="primary" style="margin-top: 5px; float: right;" @click="goBackToSendHomework()">返回</el-button>
    </div>
    </el-aside>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// import { useRouter } from 'vue-router';
import { reqSendHomeworkbystu,addGrade } from '@/api/api';
import { ElNotification,ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

// const courses = ref([]);
const userInfo = ref([]);
// const currenthomework = ref({});
const homeworks = ref([]); //显示的作业
const currentreply = ref(''); // 存储当前的评语
const currentgrade = ref(''); // 存储当前的成绩
const $router = useRouter()

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});

let homeworkSrc = ref(props.previewSrc);
let selectHomework = ref();
// 模拟数据
const mockDataHomework = ref([
  {
    homeworkNo: 1,
    c: '姜天亦',
    studentNo: '22301149',
    homeworkGrade: '99',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    homeworkNo: 2,
    studentName: '马海博',
    studentNo: '22301157',
    homeworkGrade: '98',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    homeworkNo: 3,
    studentName: '张学琛',
    studentNo: '22301168',
    homeworkGrade: '100',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
  {
    homeworkNo: 4,
    studentName: '郑宇煊',
    studentNo: '22301170',
    homeworkGrade: ' 0',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
  {
    homeworkNo: 5,
    studentName: '刘艺凡',
    studentNo: '22301153',
    homeworkGrade: '0 ',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
  {
    homeworkNo: 6,
    studentName: '张胤麟',
    studentNo: '22301153',
    homeworkGrade: ' 0',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
  {
    homeworkNo: 7,
    studentName: '赵纾熳',
    studentNo: '22301153',
    homeworkGrade: '0 ',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '未批改',
  },
  {
    homeworkNo: 8,
    studentName: '马仕承',
    studentNo: '22301153',
    homeworkGrade: '95',
    homeworkUpdateTime: '2024-10-28 14:00',
    homeworkCheckState: '已批改',
  },
]);

const mockDataUser = {
  userName: '张三',
  userId: '20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};

const goBackToSendHomework = () =>{
  $router.push({ name: 'sendHomework' })
}
// const fetchhomeworks = async () => {
//   try {
//     const courseId = localStorage.getItem('courseId')
//     const response = await requireTeacherSendHomework(courseId); // 获取通知数据
//     homeworkSrc.value = response.data || []; // 更新通知数据
//   } catch (err) {
//     // 捕获错误并使用模拟数据
//     homeworkSrc.value = props.previewSrc;
//     ElNotification({
//       message: '获取通知失败',
//       type: 'error',
//     });
//   }
// };

const submit = async () => {
  const formData = new FormData()
  const homeworkNo = localStorage.getItem('homeworkNO')
  formData.append('grade', currentgrade.value)
  formData.append('comment', currentreply.value)
  formData.append('homeworkNo', homeworkNo)
  console.log(selectHomework[0])
  formData.append('studentNo', selectHomework[0].studentNo)
  if (currentgrade.value === '')
    ElMessage.warning('请填写一个成绩');
  else {
    try{
      const response = await addGrade(formData)
      if(response.code===0){
        ElMessage.success('提交成功')

      }
    }catch(error){
      console.log(error)
      ElNotification({
      type: 'error',
      message: '提交失败',
    });
    }
    currentgrade.value = '';
    currentreply.value = '';
    

  }
}
const updateSrc=  (studentNo) =>{
  console.log('点击成功')
  selectHomework =  homeworks.value.filter(item => item.studentNo === studentNo)
  homeworkSrc.value =selectHomework[0].homeworkPath.split('/').pop();
  console.log(selectHomework[0].homeworkPath.split().pop())
} 

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const homeworkNo = localStorage.getItem('homeworkNO')
    const homeworkResponse = await reqSendHomeworkbystu(homeworkNo);
    console.log(homeworkResponse)
    if(homeworkResponse.code===0){
      homeworks.value = Object.values(homeworkResponse.data);
    }
  } catch (error) {
    // console.log(error)
    homeworks.value = mockDataHomework.value;
    // console.log(error)
    userInfo.value = mockDataUser;
    ElNotification({
      type: 'error',
      message: '获取批改作业列表失败',
    });
  }
});


</script>

<style>

.homework-nameid {
  text-align: left;
  font-weight: bold;
  font-size: 16px;
}

.homework-time {
  text-align: left;
  font-weight: 400;
  font-size: 12px;
  color: #424242;
}


.word-reply{
  text-align: left;
  font-weight: bold;
  font-size: 16px;
  padding-left: 10px;
  padding-top: 10px;
}

.preview_back {
  /* margin-top: 20px; */
  border-radius: 4px;
  background-color: #ffffff;
  padding-top: 10px;
  padding-bottom: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}


.bg-reply {
  background: #ffffff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 4px
}

.bg-inputgrade {
  background: #ffffff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-radius: 4px
}


.homework-scrollable {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  overflow-y: auto;
  height: 650px;
}

.homework-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: 4px solid #e4e7ed;
  background: #ffffff;
  transition: background 0.3s ease;
  height: 100%;
}


.reply-input {
  width: 95%;
  height: 180px;
  margin: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 0px;
  resize: none;
  font-size: 14px;
  color: #606266;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: border-color 0.3s, box-shadow 0.3s;
  
}

.grade-input {
  width: 95%;
  height: 30px;
  margin: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 0px;
  resize: none;
  font-size: 14px;
  color: #606266;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: border-color 0.3s, box-shadow 0.3s;
  
}


</style>