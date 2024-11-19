<template>
  
    <!-- <h1 class="ziti03" style="margin-bottom: 20px;">{{ lesson.lessonName }}讨论区  </h1> -->
    <div class="grid-content bg-white" style="padding: 10px;">
      <el-row :gutter="20">
              <el-col :span="22">
                <h1 class="ziti03" > {{ posts.postAuthor }}  </h1>
                <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ posts.postInfo }} </h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ posts.uploadDate }}   上传</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 15px;">{{ posts.replynum }}条回复 {{ posts.favornum }}次收藏</h1>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 10px;" @click="updatepost(post)">收藏</el-button>
              </el-col>
            </el-row>
          </div>
          <!-- <h1 class="ziti03" style="padding-right: 30px; text-align: right;margin-bottom: 10px;">共{{ posts.replynum }}条回复</h1> -->
          <!-- <el-button type="primary" round>新建回复</el-button> -->
            <el-scrollbar max-height="400px">
              <div class="grid-content bg-white" style="padding: 10px;">
<div v-for="reply in replies" :key="reply.replyNo">
  <h1 class="ziti04" style="margin-top: 5px;font-weight: bold;"> {{ reply.replyAuthor }}  </h1>
                <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ reply.replyInfo }} </h1>
                <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 5px;">{{ reply.uploadDate }}</h1>
                <el-divider></el-divider>
          </div>
        </div>
          </el-scrollbar>
          <div class="main_content_footer">
        <div class="input_box" width="100%">
          <textarea class="chat-input no-border" v-model="question" />
        </div>
        <div class="btn_box">
          <el-button type="primary" class="btn" @click="askClick(question)" style="">发送</el-button>
        </div>
      </div> 
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { reqFavoriteStatus, reqUpdateVisible } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElNotification,ElMessage } from 'element-plus';
import { reqUserInfo,reqCourseList } from '@/api/api';

export default {
setup() {
  // 使用ref创建响应式数据
  const mockPost = 
    {
      postNo: 1,
      postLesson: '人工智能基础',
      postInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      postAuthor: '郑宇煊',
      uploadDate: '2024-11-11',
      replynum: 5,
      favornum: 4,
    };
  const mocklessonData = {
lessonName:'课程实训',
};
const mockMessages = ref([
      {
        text: "我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发",
        align: "left",
        name: "丽丝",
        time: "22:37",
      },
    ]);
const mockReply = ref([
    {
      replyNo: 1,
      replyInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      replyAuthor: '郑宇煊',
      uploadDate: '2024-11-11',
    },
    {
      replyNo: 2,
      replyInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      replyAuthor: 'zxc',
      uploadDate: '2024-11-12',
    },
    {
      replyNo: 3,
      replyInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      replyAuthor: '爱学习',
      uploadDate: '2024-11-14',
    },
    {
      replyNo: 4,
      replyInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      replyAuthor: '爱学习',
      uploadDate: '2024-11-14',
    },
    {
      replyNo: 5,
      replyInfo: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      replyAuthor: '爱学习',
      uploadDate: '2024-11-14',
    },
  ]);
  const posts = ref([]);//界面展示的
  const lesson  = ref([]);
  const router = useRouter(); // 获取路由实例
  const replies = ref([]);//界面展示的
  const newmessage = ref(""); // 输入框的绑定值
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
   const askClick = async (val) => {
      if (val.trim()) {
        const userName = localStorage.getItem("userName") || "默认用户";
        messages.value.push({
          text: val, // 用户输入的消息
          name: userName, // 用户名
          align: "right",
        });
        newmessage.value = ""; // 发送消息后清空输入框
      } else {
        ElMessage.warning("不能发送空白消息");
      }
      const formData = new FormData()
      formData.append('question', val)
      try {
        //const response = await ai_Helper(formData)
        // if (response.code === 0) {
        //   messages.value.push({
        //     text: response.data,
        //     name: "智慧课堂小助手",
        //     align: "left",
        //   });
        // }
      } catch (error) {
        newmessage.value = mockMessages.value
        console.log(error)
        ElNotification({
          message: '发送失败，请重试',
          type: 'error',
        });
      }
    };
//TODO
// 如何传对应讨论内容的参数以达到打开对应讨论详情我还没写
  onMounted(async () => {
try {
  const classResponse = await reqClassInfo();
  const postResponse = await reqPostList();
  const replyResponse = await reqReplyList();
  lesson.value = classResponse.data;
  posts.value = postResponse.data;
  reply.value = replyResponse.data;
} catch (error) {
    lesson.value = mocklessonData;
    posts.value = mockPost;
    replies.value = mockReply.value;
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
    newmessage,
    mockMessages,
    replies,
  };
},
};
</script>



<style>
.head {
  background-color: #005bac;

}

.main_content_footer {
  width: 100%;
  height: 150px;
  background-color: #ffffff;
  border-top: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10px 16px;
  margin-top: 10px;
}

.input_box {
  flex: 1;
  display: flex;
  align-items: center;
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