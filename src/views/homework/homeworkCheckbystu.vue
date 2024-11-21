<template>
    <el-container>
      <el-main>
        <iframe class="preview_back" :src="currenthomework" width="100%" height=650px style="margin-top: -20px;border:none;"></iframe>
      </el-main>
      <el-aside width="200px">
        
        <div class="bg-reply">
          <h1 class="word-reply">评语</h1>
          <textarea class="reply-input " v-model="currentreply" />
        <div>
          <el-button type="primary" style="margin-top: 5px; float: right;" @click="submitReply">保存</el-button>
        </div>
        </div>
        <div style="margin-top: 60px;"></div>
        <!-- 分隔 -->
        <div class="bg-inputgrade">
          <h1 class="word-reply">打分</h1>
          <textarea class="grade-input " v-model="currentgrade" />
        <div>
          <el-button type="primary" style="margin-top: 5px; float: right;" @click="submitGrades">打分</el-button>
        </div>
        </div>
<br><br><br><br><br>

        <div class="navigation">
        <el-button
          style="margin-left: 20px;"
          v-for="(homework, index) in homeworks"
          :key="index"
          :type="homework.homeworkSrc !== null ? 'success' : 'info'"
          @click="goTohomework(index)"
        >
          题目 {{ index + 1 }}
        </el-button>
      </div>

      </el-aside>
    </el-container>
  </template>

  
  
  <script setup>
  import { ref, onMounted } from 'vue';
  // import { useRouter } from 'vue-router';
  import { reqSendHomeworkbystu } from '@/api/api';
  import { ElNotification } from 'element-plus';
  import { ElMessage } from 'element-plus'
  import { useRouter } from 'vue-router';

  let $router = useRouter()
  const currenthomework = ref({});//显示的作业
  const homeworks = ref([]); 
  const currentreply = ref(''); // 存储当前的评语
  const currentgrade = ref(''); // 存储当前的答案
  //const grades = ref([]); // 存储用户的答案（{ homework_index, grade } 格式）
  const currentIndex = ref(0) // 当前题目索引
  const mockHomeworks = ref([
    {
      index :0,
      homeworkSrc:{
      type: String,
      required: false,
      default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
      },
      grade: '',
      reply: '',
    },
    {
      index :1,
      homeworkSrc:{
      type: String,
      required: false,
      default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
      },
      grade : '',
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
  
  // 初始化题目，并加载第一道题
  if (homeworks.value.length > 0) {
    //currentgrade.value = homeworks.value.map(q => ({ question_id: q.question_id, grade: null }))
      loadHomework()
    } else {
      ElMessage.error('没有加载到任何试题')
    }


  
    // 加载当前题目
    const loadHomework = () => {
      //TODO //应该是这个地方的问题，预览显示不出来
      currenthomework.value = homeworks.value[currentIndex.value].homeworkSrc;
  }

  // 跳转到指定题目
  const goTohomework = (index) => {
    if (index < 0 || index >= homeworks.value.length) return
    currentIndex.value = index
  }

  //   // 切换到下一题
  //   const nextHomework = () => {
  //   if (grades.value[currentIndex.value].grade === null) {
  //     ElMessage.warning('请选择一个答案')
  //     return
  //   }
  //   currentIndex.value++
  //   loadHomework()
  // }
  
  // 提交本题答案
  const submitGrades = async() => {
    homeworks.value[currentIndex.value].grade = currentgrade.value;
    //TODO //检测grade是否为空,明显现在还不行，原因未知
    if (homeworks.value[currentIndex.value].grade === null)
      ElMessage.warning('请选择一个答案');
    if (homeworks.value.grade(a => a.grade === null)) {
      return
    }else{
      $router.push({name:'homeworkCheckbystuEND'})
    }
  }

  // 提交本题评语
  const submitReply = async() => {
    homeworks.value[currentIndex.value].reply = currentreply.value;
  }
  

  onMounted(async () => {
    try {
      const homeworkbystuResponse = await reqSendHomeworkbystu();
      homeworks.value = homeworkbystuResponse.data;
      // homeworkSrc.value = response.data.Homework || homeworkSrc.value;
    } catch (error) {
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