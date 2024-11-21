<template>

  <div class="DiscussionTitle" style="margin-bottom: 30px;">{{ courseName }}讨论区
    <el-button type="primary" round
      style="padding: 15px;font-size: large;font-weight: bold;float: right;margin-right: 20px;"
      @click="newDiscussion()">新建帖子</el-button>
  </div>

  <el-scrollbar max-height="615px">
    <div class="grid-content bg-white" style="padding-left: 10px;" v-for="post in posts" :key="post.index">
      <el-row :gutter="20">
        <el-col :span="22" @click="goToDiscussionInfo(post.DiscussionId)">
          <h1 class="ziti03" style="margin-top: 10px;"> {{ post.username }} </h1>
          <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ post.Information }} </h1>
          <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ post.PostingTime }} 上传</h1>
          <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 15px;">{{ post.commentTimes }}条回复 {{
            post.starTimes }}次收藏</h1>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" style="margin-top: 10px;" @click="starPost(post)">收藏</el-button>
        </el-col>
      </el-row>
    </div>
  </el-scrollbar>
</template>

<script>
//TODO:添加主题，添加@其他人
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { addLikeDiscussion } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElMessage, ElNotification } from 'element-plus';
import { reqDiscussionList } from '@/api/api';

export default {
  setup() {
    const courseName = localStorage.getItem('courseName');
    // 使用ref创建响应式数据
    const title = ref('测试1')
    const mockPost = ref([
      {
        index: 1,
        postLesson: '实训',
        Information: '帖子内容是真不错真不错真不错真不戳真不戳真不戳',
        username: '姜天亦',
        PostingTime: '2024-11-13',
        commentTimes: 0,
        starTimes: 0,
      },
      {
        index: 2,
        postLesson: '科技论文写作',
        Information: '老师好，我负责的内容是项目内容，首先项目内容分为以下三个方面，一用户面部特征信息录入与人脸匹配模块。二跨摄像头目标跟踪模块。三校园人员晕倒行为检测与告警模块。以下我对方针对这三个主要内容进行详细讲解。一用户名不特征信息录入与人脸匹配模块。在使用本系统前，系统可对新用户进行人脸录入，这主要是指在校园安全系统使用之初，我们将对校园已存在的老师以及已在校的同学进行人脸录入，以确保老师和同学具有相对应的出入校权限。',
        username: '马海博',
        PostingTime: '2024-11-12',
        commentTimes: 9,
        starTimes: 4,
      },
      {
        index: 3,
        postLesson: 'UI前端',
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        username: '张学琛',
        PostingTime: '2024-11-11',
        commentTimes: 0,
        starTimes: 50,
      },
      {
        index: 4,
        postLesson: '人工智能基础',
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        username: '郑宇煊',
        PostingTime: '2024-11-11',
        commentTimes: 0,
        starTimes: 4,
      },
      {
        index: 5,
        postLesson: '机器学习',
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        username: '刘艺凡',
        PostingTime: '2024-11-11',
        commentTimes: 0,
        starTimes: 9,
      },
    ]);
    const mocklessonData = {
      lessonName: '课程实训',
    };
    const posts = ref([]);//界面展示的
    const lesson = ref([]);
    const $router = useRouter(); // 获取路由实例

    // 定义方法
    //TODO


    const starPost = async (post) => {
      const formData = new FormData()
      formData.append('favoriteInformationNo', post.DiscussionId)
      formData.append('favoriteTitle', title.value)
      try {
        const response = await addLikeDiscussion(formData)
        if (response.code === 0) {
          ElMessage.success('收藏成功')
        }
      } catch (error) {
        console.error('收藏失败:', error);
        ElNotification({
          type: 'error',
          message: '收藏失败，请重试。',
        });
      }


      // 假设更新操作成功，可以在这里更新界面显示或者状态
    };


    onMounted(async () => {
      try {
        const courseId = localStorage.getItem('courseId')
        const postResponse = await reqDiscussionList(courseId);
        posts.value = postResponse.data;
      } catch (error) {
        console.log(error)
        posts.value = mockPost.value;
        ElNotification({
          type: 'error',
          message: '获取帖子失败',
        });
      }
    });

    const goToDiscussionInfo = async (discussionId) => {
      localStorage.setItem('discussionId', discussionId)
      $router.push({ name: 'discussionInfo' })
    }

    const newDiscussion = () => {
      $router.push({ name: 'createDiscussion' });
    }

    return {
      posts,
      starPost,
      mocklessonData,
      lesson,
      courseName,
      goToDiscussionInfo,
      newDiscussion,
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

.DiscussionTitle {
  text-align: left;
  font-weight: bold;
  font-size: 20px;
  margin-left: 10px;
  padding-top: 10px;
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