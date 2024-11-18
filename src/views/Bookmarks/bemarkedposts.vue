<template>
    <el-container>
      <el-header class="head">
        <el-row :gutter="20">
            <el-col :span="18">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; " >
            <h1 class="zitihead">智慧课程平台</h1>
          </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/posts" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">笔记
              </h1>
            </router-link>
          </div>
        </el-col>
          <el-col :span="2">
            <div class="grid-content ">
              <router-link to="/firstpage" style="text-decoration: none;">
                <h1 style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">收藏夹
                </h1>
              </router-link>
            </div>
          </el-col>
          <el-col :span="1">
            <div class="grid-content ">
              <router-link to="/myinformation" style="text-decoration: none;">
                <div style="margin-top: 10px"><el-avatar :size="40" :src="circleUrl"></el-avatar></div>
              </router-link>
            </div>
          </el-col>
          <el-col :span="2">
            <router-link to="/myinformation" style="text-decoration: none;">
              <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">爱学习
              </h1>
            </router-link>
          </el-col>
        </el-row>
      </el-header>
  
      <el-main class="backmain1">
        <el-container>
          <el-aside class="backleft">
            <div class="whiteback" :data="student">
              <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
              <div style="margin-top: 20px;text-align: center; "><el-avatar :size="100" :src="circleUrl"></el-avatar></div>
              <h1 class="ziti01">学生</h1>
              <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ student.studentName }}</h1>
              <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ student.studentNo }}</h1>
              <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ student.dept }}</h1>
              <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ student.email }}</h1>
            </div>
            <div class="whiteback2">
              <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">课程提醒</h1>
            </div>
          </el-aside>
  
          <el-main height="600px">
            <el-row>
            <el-col :span="3">
              <router-link to="/bookmarks" style="text-decoration: none;">
              <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;width: 100px;" plain>收藏夹</el-button>
              </router-link>
            </el-col>
            <el-col :span="21">
              <router-link to="/othersposts" style="text-decoration: none;">
                <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;width: 100px;" >帖子</el-button>
              </router-link>
            </el-col>
          </el-row>
            <!-- <div class="scrollable"> -->
                <div class="grid-content bg-white" style="padding-left: 10px;" v-for="post in posts" :key="post.postNo">
            <el-row :gutter="20">
              <el-col :span="21">
                <h1 class="ziti03" style="margin-top: 5px;">{{ post.postLesson }}  </h1>
                <h1 class="ziti04" style="line-height: 1.5"> {{ post.postInfo }} </h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;margin-bottom: 15px;">{{ post.uploadDate }}   {{ post.postAuthor }}  上传 </h1>
              </el-col>
              
              <el-col :span="3">
                <el-button type="primary" style="margin-top: 10px;" @click="updatepost(post)">取消收藏</el-button>
              </el-col>
            </el-row>
          </div>
        
          </el-main>
  
        </el-container>
      </el-main>
    </el-container>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
import { reqUpdateVisible } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElNotification } from 'element-plus';
import { reqUserInfo ,reqPostList} from '@/api/api';

export default {
  setup() {
    // 使用ref创建响应式数据
    const mockPost = ref([
      {
        postNo: 1,
        postLesson: '实训',
        postInfo: '帖子内容是真不错真不错真不错真不戳真不戳真不戳',
        sendStatus: true,
        postAuthor: '姜天亦',
        uploadDate: '2024-11-13',
        authority: true,
      },
      {
        postNo: 2,
        postLesson: '科技论文写作',
        postInfo: '老师好，我负责的内容是项目内容，首先项目内容分为以下三个方面，一用户面部特征信息录入与人脸匹配模块。二跨摄像头目标跟踪模块。三校园人员晕倒行为检测与告警模块。以下我对方针对这三个主要内容进行详细讲解。一用户名不特征信息录入与人脸匹配模块。在使用本系统前，系统可对新用户进行人脸录入，这主要是指在校园安全系统使用之初，我们将对校园已存在的老师以及已在校的同学进行人脸录入，以确保老师和同学具有相对应的出入校权限。',
        sendStatus: false,
        postAuthor: '马海博',
        uploadDate: '2024-11-12',
        authority: false,
      },
      {
        postNo: 3,
        postLesson: 'UI前端',
        postInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        sendStatus: true ,
        postAuthor: '张学琛',
        uploadDate: '2024-11-11',
        authority: true,
      },
      // 更多帖子数据...
    ]);
    const mockData = {
      studentName: '张三',
      studentNo: '20220001',
      dept: '计算机科学与技术',
      email: 'zhangsan@example.com',
      avatarUrl: 'src/assets/images/example.jpg'
    };
    const posts = ref([]);//界面展示的
    const student = ref([]);
    

    // 定义方法
    //TODO
    const updatePostStatus = (post) => {
      console.log(`更新帖子${post.postNo}的权限状态为：${post.authority}`);
      // 假设更新操作成功，可以在这里更新界面显示或者状态
    };

    const updatePost = (post) => {
      console.log(`更新帖子${post.postNo}的内容`);
      // 假设更新操作成功，可以在这里更新界面显示或者状态
    };

    const previewPost = (post) => {
      console.log(`预览帖子${post.postNo}`);
      // 假设跳转操作成功，可以在这里更新界面显示或者状态
    };

    const updatePostFavStatus = async (post) => {
      // 更新收藏状态
      try {
         await reqUpdateVisible({
           postNo: post.postNo,
         });
       } catch (error) {
          console.error('取消收藏失败:', error);
          ElNotification({
            type: 'error',
            message: '取消收藏失败，请重试。',
         });
       }
     };

    onMounted(async () => {
  try {
    const userResponse = await reqUserInfo();
    const postResponse = await reqPostList();
    student.value = userResponse.data;
    posts.value = postResponse.data;
  } catch (error) {
    console.log(error);
    student.value = mockData;
    posts.value = mockPost.value;
    ElNotification({
      type: 'error',
      message: '获取信息失败',
    });
  }
});

    return {
      posts,
      updatePostStatus,
      updatePost,
      previewPost,
      updatePostFavStatus,
      mockData,
      student,
      posts,
    };
  },
};
  </script>

  
  
  <style>
  .head {
    background-color: #005bac;
  
  }
  
  .backmain1 {
    height: 770px;
    background-color: #eaf6ff;
  }
  
  .zitihead {
    text-align: left;
    font-weight: bold;
    font-size: 24px;
    margin-top: 15px;
    color: #ffffff;
  }
  
  .ziti01 {
    text-align: center;
    font-weight: 400;
    font-size: 14px;
    margin-bottom: 25px;
    margin-top: 10px;
  }
  
  .ziti02 {
    text-align: center;
    font-weight: bold;
    font-size: 16px;
  }
  
  .ziti03 {
    text-align: left;
    font-weight: bold;
    margin-bottom: 5px;
    font-size: 16px;
    margin-left: 10px;
    padding-top: 10px;
  }
  
  .ziti04 {
    text-align: left;
    font-weight: 400;
    margin-bottom: 2px;
    font-size: 14px;
    margin-left: 10px;
  }
  
  .backleft {
    width: "350px";
    max-height: "550px";
    background-color: #eaf6ff;
  }
  
  .backright {
    width: "350px";
    height: "600px";
    background-color: #eaf6ff;
  }
  
  
  .whiteback {
    border-radius: 4px;
    max-height: 350px;
    background-color: #ffffff;
    padding: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  
  }
  
  .whiteback2 {
    margin-top: 20px;
    border-radius: 4px;
    height: 200px;
    background-color: #ffffff;
    padding: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }
  
  .whiteback3 {
    /* margin-top: 20px; */
    border-radius: 4px;
    height: 400px;
    background-color: #ffffff;
    padding: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }
  
  .scrollable {
    height: 690px;
    /* 固定高度 */
    overflow-y: auto;
    /* 垂直方向上的滚动条 */
    overflow-x: hidden;
    padding-right: 10px;
  }
  
  .el-row {
    margin-bottom: 20px;
  
    &:last-child {
      margin-bottom: 0;
    }
  }
  
  .el-col {
    border-radius: 4px;
  }
  
  .bg-white {
    background: #ffffff;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    border-radius: 30px
  }
  
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    /* height: 250px; */
    margin-bottom: 10px;
  
  }
  
  body>.el-container {
    margin-bottom: 40px;
  }
  </style>