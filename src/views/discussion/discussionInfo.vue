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
      <el-mention class="discuss-chat-input no-border" :loading="loading" v-model="newMessage" type="textarea"
        :options="options" @search="handleSearch" placeholder="Please input" />
    </div>
    <div class="btn_box">
      <el-button type="primary" class="btn" @click="returnDiscussion(newMessage)" style="float: right;">返回</el-button>
      <el-button type="primary" class="btn" @click="addOneComment(newMessage)" style="float: right;">发送</el-button>
    </div>
  </div>
</template>
<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { reqAllComment, reqOneDiscussion, addComment, addLikeDiscussion, reqDeleteComment, reqDeleteDiscussion, reqStudentName } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';

export default {
  setup() {
    const mockPost = {
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
    ]);

    const loading = ref(false);
    const options = ref([]);
    const posts = ref([]);
    const $router = useRouter();
    const comments = ref([]);
    const newMessage = ref("");
    const title = ref('测试1');
    let timer = null;

    const handleSearch = async (pattern) => {
      if (timer) clearTimeout(timer);

      loading.value = true;
      timer = setTimeout(async () => {
        try {
          const courseId = localStorage.getItem('courseId');
          const commentUser = await reqStudentName(courseId);
          options.value = commentUser.map(
            (item) => ({
              label: pattern + item,
              value: pattern + item,
            })
          );
        } catch (error) {
          console.error("获取评论用户失败:", error);
          ElNotification({
            message: '获取评论用户失败，请重试。',
            type: 'error',
          });
        } finally {
          loading.value = false;
        }
      }, 1500);
    };

    // 获取评论和帖子内容
    onMounted(async () => {
      try {
        const discussionId = localStorage.getItem('discussionId');
        const postResponse = await reqOneDiscussion(discussionId);
        const commentResponse = await reqAllComment(discussionId);

        posts.value = postResponse.data;
        comments.value = commentResponse.data;
      } catch (error) {
        console.log(error);
        posts.value = mockPost;
        comments.value = mockReply.value;
        ElNotification({
          type: 'error',
          message: '获取帖子详细信息失败',
        });
      }
    });

    const addOneComment = async (val) => {
      const discussionId = localStorage.getItem('discussionId');
      const formData = new FormData();
      formData.append('discussionId', discussionId);
      formData.append('commentInfo', val);
      // TODO: 添加评论图片和 @ 其他人
      formData.append('imgUrl', null);
      formData.append('mentionedUser', null);

      try {
        if (val.trim()) {
          const response = await addComment(formData);
          if (response.code === 0) {
            ElMessage.success('评论成功');
            newMessage.value = ""; // 清空输入框
            window.location.reload();
          }
        } else {
          ElMessage.warning("不能发送空白消息");
        }
      } catch (error) {
        console.error(error);
        ElNotification({
          message: '发送失败，请重试',
          type: 'error',
        });
      }
    };

    const starPost = async () => {
      const formData = new FormData();
      const discussionId = localStorage.getItem('discussionId');
      formData.append('favoriteInformationNo', discussionId);
      formData.append('favoriteTitle', title.value);

      try {
        const response = await addLikeDiscussion(formData);
        if (response.code === 0) {
          ElMessage.success('收藏成功');
          window.location.reload();
        }
      } catch (error) {
        console.error(error);
        ElNotification({
          message: '收藏失败，请重试',
          type: 'error',
        });
      }
    };

    const deleteReply = async (reply) => {
      const replyId = reply.index;
      try {
        const response = await reqDeleteComment(replyId);
        if (response.code === 0) {
          ElMessage.success('删除评论成功');
          window.location.reload();
        }
      } catch (error) {
        console.error(error);
        ElNotification({
          message: '删除评论失败',
          type: 'error',
        });
      }
    };

    const deleteDiscussion = async (post) => {
      const discussionId = post.postNo;
      try {
        const response = await reqDeleteDiscussion(discussionId);
        if (response.code === 0) {
          ElMessage.success('删除帖子成功');
          $router.push('/discussion-list');
        }
      } catch (error) {
        console.error(error);
        ElNotification({
          message: '删除帖子失败',
          type: 'error',
        });
      }
    };

    const showDeleteDiscussionButton = (post) => {
      return post.username === '郑宇煊'; // 这里只是一个示例，根据需求调整
    };

    const showDeleteButton = (reply) => {
      return reply.commentUsername === '郑宇煊'; // 这里只是一个示例，根据需求调整
    };

    return {
      posts,
      comments,
      newMessage,
      loading,
      options,
      handleSearch,
      addOneComment,
      starPost,
      deleteReply,
      deleteDiscussion,
      showDeleteDiscussionButton,
      showDeleteButton,
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