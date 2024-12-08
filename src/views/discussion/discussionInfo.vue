<template>

  <div class="grid-content bg-white" style="padding: 10px;">
    <el-row :gutter="24">
      <el-col :span="22">
        <h1 class="ziti03"> {{ posts.username }} </h1>
        <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ posts.Information }} </h1>
        <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ posts.PostingTime }} 上传</h1>
        <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 15px;">{{ posts.commentTimes }}条回复 {{
          posts.starTimes }}次收藏
        </h1>
      </el-col>
      <el-col :span="2" style="display: flex; flex-direction: column; justify-content: flex-start;">
        <el-button type="primary" style="margin-top: 10px;" @click="starPost()">收藏</el-button>
        <el-button v-if="showDeleteDiscussionButton(posts)" type="danger" @click="deleteDiscussion(posts)"
          style="margin-top: 90%;">
          删除
        </el-button>
      </el-col>
    </el-row>
  </div>
  <el-scrollbar max-height=300px>
    <div class="grid-content bg-white" style="padding: 10px;">
      <div v-for="reply in comments" :key="reply.index">
        <h1 class="ziti04" style="margin-top: 5px;font-weight: bold;"> {{ reply.commentUsername }} </h1>
        <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ reply.Information }} </h1>
        <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 5px;">{{ reply.PostingTime }}
          <el-button v-if="showDeleteButton(reply)" type="danger" class="btn" @click="deleteReply(reply)"
            style="float: right;">删除</el-button>
        </h1>

        <el-divider></el-divider>
      </div>
    </div>
  </el-scrollbar>
  <div class="main_content_footer">
    <div class="input_box" width="100%">
      <el-mention class="discuss-chat-input no-border":loading="loading" v-model="newMessage" type="textarea" :options="options"@search="handleSearch"
        placeholder="Please input" />
    </div>
    <div class="btn_box">
      <el-button type="primary" class="btn" @click="returnDiscussion(newMessage)" style="float: right;">返回</el-button>
      <el-button type="primary" class="btn" @click="addOneComment(newMessage)" style="float: right;">发送</el-button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted ,onBeforeMount} from 'vue';
import { useRouter } from 'vue-router';
import { reqAllComment, reqOneDiscussion, addComment, addLikeDiscussion, reqDeleteComment, reqDeleteDiscussion, reqStudentName } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElNotification, ElMessage} from 'element-plus';


export default {
  setup() {
    // 使用ref创建响应式数据
    const mockPost =
    {
      postNo: 1,
      postLesson: '人工智能基础',
      Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
      username: '郑宇煊',
      PostingTime: '2024-11-11',
      commentTimes: 5,
      starTimes: 4,
    };

    const mockReply = ref([
      {
        index: 1,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUsername: '郑宇煊',
        PostingTime: '2024-11-11',
      },
      {
        index: 2,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUsername: 'zxc',
        PostingTime: '2024-11-12',
      },
      {
        index: 3,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUsername: '爱学习',
        PostingTime: '2024-11-14',
      },
      {
        index: 4,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUsername: '爱学习',
        PostingTime: '2024-11-14',
      },
      {
        index: 5,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUsername: '爱学习',
        PostingTime: '2024-11-14',
      },
    ]);

    const MockOptions = ref([
      {
        label: 'Fuphoenixes',
        value: 'Fuphoenixes',
      },
      {
        label: 'kooriookami',
        value: 'kooriookami',
      },
      {
        label: 'Jeremy',
        value: 'Jeremy',
      },
      {
        label: 'btea',
        value: 'btea',
      },
    ])
    const loading = ref(false)
    const options = ref([])
    const posts = ref([]);//界面展示的
    const $router = useRouter(); // 获取路由实例
    const comments = ref([]);//界面展示的
    const newMessage = ref(""); // 输入框的绑定值
    const title = ref('测试1')
    let timer = null;

  
const handleSearch =  (pattern) => {
  if (timer) clearTimeout(timer)
  loading.value = true
  timer = setTimeout(async() => {
    const courseId = localStorage.getItem('courseId')
    const commentUser = await reqStudentName(courseId)
    options.value = commentUser.map(
      (item) => ({
        label: pattern + item,
        value: pattern + item,
      })
    )
    loading.value = false
  }, 1500)
};


    const addOneComment = async (val) => {
      console.log(val)
      const discussionId = localStorage.getItem('discussionId')
      const formData = new FormData()
      formData.append('discussionId', discussionId)
      formData.append('commentInfo', val)
      //TODO:添加评论图片和@其他人
      formData.append('imgUrl', null)
      formData.append('mentionedUser', null)
      try {
        if (val.trim()) {
          const response = await addComment(formData)
          if (response.code === 0) {
            ElMessage.success('评论成功')
            newMessage.value = ""; // 发送消息后清空输入框
            window.location.reload();
          }
        } else {
          ElMessage.warning("不能发送空白消息");
        }
      } catch (error) {
        console.log(error)
        ElNotification({
          message: '发送失败，请重试',
          type: 'error',
        });
      }
    };

    const showDeleteButton = (reply) => {
      const userName = localStorage.getItem('userName') || '郑宇煊'
      return userName === reply.commentUsername
    }

    const deleteReply = async (reply) => {
      try {
        const formData = new FormData()
        console.log(reply.commentId)
        formData.append('commentId', reply.commentId)
        const response = await reqDeleteComment(formData); // 连接后端删除文件
        if (response.code === 0) {
          ElMessage.success("评论删除成功");
          window.location.reload();
        }
      } catch (error) {
        console.error('删除评论失败', error);
        ElNotification({
          message: '删除评论失败，请重试',
          type: 'error',
        });
      }
    }

    const showDeleteDiscussionButton = (discussion) => {
      const userName = localStorage.getItem('userName') || '郑宇煊'
      return userName === discussion.username
    }

    const deleteDiscussion = async (post) => {
      try {
        const formData = new FormData()
        formData.append('discussionId', post.discussionId)
        const response = await reqDeleteDiscussion(formData); // 连接后端删除文件
        if (response.code === 0) {
          ElMessage.success("帖子删除成功");
          $router.push({ name: 'discussionArea' })
        }
      } catch (error) {
        console.error('删除帖子失败', error);
        ElNotification({
          message: '删除帖子失败，请重试',
          type: 'error',
        });
      }
    };


    onMounted(async () => {
      try {
        const discussionId = localStorage.getItem('discussionId')
        const courseId = localStorage.getItem('courseId')
        const postResponse = await reqOneDiscussion(discussionId);
        const commentResponse = await reqAllComment(discussionId);
        
        posts.value = postResponse.data;
        comments.value = commentResponse.data;
      } catch (error) {
        console.log(error)
        posts.value = mockPost;
        comments.value = mockReply.value;
        ElNotification({
          type: 'error',
          message: '获取帖子详细信息失败',
        });
      }
    });

    const starPost = async () => {
      const formData = new FormData()
      const discussionId = localStorage.getItem('discussionId')
      formData.append('favoriteInformationNo', discussionId)
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

    const returnDiscussion = () => {
      $router.push({ name: 'discussionArea' })
    }

    return {
      posts,
      newMessage,
      comments,
      starPost,
      addOneComment,
      returnDiscussion,
      showDeleteButton,
      deleteReply,
      showDeleteDiscussionButton,
      deleteDiscussion,
      options,
      handleSearch,
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
  width: 100%
}

.discuss-chat-input {
  width: 100%;
  height: 80px;
  padding: 12px 16px;
  border-radius: 8px;
  resize: none;
  font-size: 14px;
  color: #606266;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: border-color 0.3s, box-shadow 0.3s;
  margin-top: 10px;
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

.btn_box {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;

  .btn {
    background-color: #409eff;
    color: #ffffff;
    border-radius: 4px;
    padding: 6px 20px;
    font-size: 14px;
    transition: background-color 0.3s;
  }

  .btn:hover {
    background-color: #66b1ff;
  }
}
</style>