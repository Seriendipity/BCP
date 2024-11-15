<template>
  
    <h1 class="ziti03" style="margin-bottom: 20px;">{{ lesson.lessonName }}讨论区  </h1>
    <el-scrollbar max-height="615px">
<div class="grid-content bg-white" style="padding-left: 10px;" v-for="post in posts" :key="post.postNo">
            <el-row :gutter="20">
              <el-col :span="22">
                <h1 class="ziti03" style="margin-top: 10px;"> {{ post.postAuthor }}  </h1>
                <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ post.postInfo }} </h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ post.uploadDate }}   上传</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 15px;">{{ post.replynum }}条回复 {{ post.favornum }}次收藏</h1>
              </el-col>
              
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 10px;" @click="updatepost(post)">收藏</el-button>
              </el-col>
            </el-row>
          </div></el-scrollbar>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { reqUpdateVisible } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElNotification } from 'element-plus';
import { reqUserInfo,reqCourseList } from '@/api/api';

export default {
setup() {
  // 使用ref创建响应式数据
  const mockPost = ref([
    {
      postNo: 1,
      postLesson: '实训',
      postInfo: '帖子内容是真不错真不错真不错真不戳真不戳真不戳',
      postAuthor: '姜天亦',
      uploadDate: '2024-11-13',
      replynum: 0,
    },
    {
      postNo: 2,
      postLesson: '科技论文写作',
      postInfo: '老师好，我负责的内容是项目内容，首先项目内容分为以下三个方面，一用户面部特征信息录入与人脸匹配模块。二跨摄像头目标跟踪模块。三校园人员晕倒行为检测与告警模块。以下我对方针对这三个主要内容进行详细讲解。一用户名不特征信息录入与人脸匹配模块。在使用本系统前，系统可对新用户进行人脸录入，这主要是指在校园安全系统使用之初，我们将对校园已存在的老师以及已在校的同学进行人脸录入，以确保老师和同学具有相对应的出入校权限。',
      postAuthor: '马海博',
      uploadDate: '2024-11-12',
      replynum: 9,
      favornum: 4,
    },
    {
      postNo: 3,
      postLesson: 'UI前端',
      postInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      postAuthor: '张学琛',
      uploadDate: '2024-11-11',
      replynum: 0,
      favornum: 50,
    },
    {
      postNo: 4,
      postLesson: '人工智能基础',
      postInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      postAuthor: '郑宇煊',
      uploadDate: '2024-11-11',
      replynum: 0,
      favornum: 4,
    },
    {
      postNo: 5,
      postLesson: '机器学习',
      postInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      postAuthor: '刘艺凡',
      uploadDate: '2024-11-11',
      replynum: 0,
      favornum: 9,
    },
  ]);
  const mocklessonData = {
    lessonName:'课程实训',
    };
  const posts = ref([]);//界面展示的
  const lesson  = ref([]);
  const router = useRouter(); // 获取路由实例

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
  const classResponse = await reqClassInfo();
  const postResponse = await reqPostList();
  lesson.value = classResponse.data;
  posts.value = postResponse.data;
} catch (error) {
    lesson.value = mocklessonData;
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
    mocklessonData,
    lesson,
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