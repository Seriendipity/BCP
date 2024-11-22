<template>
  <el-container>
    <el-main>
      <iframe class="preview_back" :src="currenthomework" width="100%" height=650px
        style="margin-top: -20px;border:none;"></iframe>
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
      <br><br><br><br><br>

      <div class="navigation">
        <el-button style="margin-left: 20px;" v-for="(homework, index) in homeworks" :key="index"
          :type="homework.homeworkSrc !== null ? 'success' : 'info'" @click="goTohomework(index)">
          题目 {{ index + 1 }}
        </el-button>
      </div>
      <el-button type="primary" style="margin-top: 5px; float: right;" @click="submit()">提交</el-button>
    </el-aside>
  </el-container>
</template>



<script setup>
import { ref, onMounted } from 'vue';
// import { useRouter } from 'vue-router';
import { addPeerGrade, reqSendHomeworkbystu } from '@/api/api';
import { ElNotification } from 'element-plus';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';

let $router = useRouter()
const currenthomework = ref({});//显示的作业
const homeworks = ref([]);
const currentreply = ref(''); // 存储当前的评语
const currentgrade = ref(''); // 存储当前的答案
//const grades = ref([]); // 存储用户的答案（{ homework_index, grade } 格式）
let currentIndex = ref(0) // 当前题目索引
const mockHomeworks = ref([
  {
    index: 0,
    homeworkSrc: {
      type: String,
      required: false,
      default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
    },
    grade: '',
    reply: '',
  },
  {
    index: 1,
    homeworkSrc: {
      type: String,
      required: false,
      default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
    },
    grade: '',
    reply: '',
  },

]);

// const props = defineProps({
//   previewSrc: {
//     type: String,
//     required: false,
//     default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
//   }
// });





// 加载当前题目
const loadHomework = () => {
  //TODO //应该是这个地方的问题，预览显示不出来
  console.log(currentIndex.value)
  console.log(homeworks.value[currentIndex.value])
  currenthomework.value = homeworks.value[currentIndex.value].homeworkPath.split('/').pop();;
}

// 跳转到指定题目
const goTohomework = (index) => {
  if (index < 0 || index >= homeworks.value.length) return
  currentIndex.value = index
}





const submit = async () => {
  const formData = new FormData()
  const homeworkNo = localStorage.getItem('homeworkNO')
  formData.append('grade', currentgrade.value)
  formData.append('comment', currentreply)
  formData.append('homeworkNo', homeworkNo)
  formData.append('revieweeNo', homeworks.value[currentIndex.value].reviewee)
  if (currentgrade.value === '')
    ElMessage.warning('请填写一个成绩');
  else {
    try{
      const response = await addPeerGrade(formData)
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
    if (currentIndex.value === homeworks.value.length-1) {
      $router.push({ name: 'homeworkCheckbystuEND' })
    } else {
      currentIndex.value++
      loadHomework()
    
    }

  }
}


onMounted(async () => {
  try {
    const homeworkNo = localStorage.getItem('homeworkNO')
    console.log(homeworkNo)
    const homeworkbystuResponse = await reqSendHomeworkbystu(homeworkNo);
    console.log(homeworkbystuResponse.data)
    if (homeworkbystuResponse.code === 0) {
      homeworks.value = Object.values(homeworkbystuResponse.data);
      loadHomework()
    }
  } catch (error) {
    console.log(error)
    homeworks.value = mockHomeworks.value;
    ElNotification({
      type: 'error',
      message: '获取信息失败',
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


.word-reply {
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