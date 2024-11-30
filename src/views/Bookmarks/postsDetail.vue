<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <el-container>
    <el-header class="teacher-head" v-if="isTeacher()">
      <el-row :gutter="20">
        <el-col :span="18">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; ">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/notes" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">笔记
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content ">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1
                style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
                收藏夹
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/myinformation" style="text-decoration: none;">
              <div style="margin-top: 10px"><el-avatar :size="40" :src="userInfo.avatarUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">{{ userInfo.userName }}
            </h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>


    <el-header class="student-head" v-else>
      <el-row :gutter="20">
        <el-col :span="18">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; ">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/notes" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">笔记
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content ">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1
                style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
                收藏夹
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="1">
          <div class="grid-content ">
            <router-link to="/myinformation" style="text-decoration: none;">
              <div style="margin-top: 10px"><el-avatar :size="40" :src="userInfo.avatarUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">{{ userInfo.userName }}
            </h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <el-container>
        <el-aside class="backleft">
          <div class="whiteback" :data="userInfo">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
            <div style="margin-top: 20px;text-align: center; "><el-avatar :size="100"
                :src="userInfo.avatarUrl"></el-avatar>
            </div>
            <h1 class="ziti01">学生</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ userInfo.userName }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ userInfo.userId }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ userInfo.dept }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ userInfo.email }}</h1>
          </div>
        </el-aside>
        <el-main class="postDetailMain">
          <div class="grid-content bg-white" style="padding: 10px;">
            <el-row :gutter="20">
              <el-col :span="22">
                <h1 class="ziti03"> {{ posts.username }} </h1>
                <h1 class="ziti04" style="line-height: 1.5 ;margin-top: 5px;"> {{ posts.Information }} </h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ posts.PostingTime }} 上传</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 5px;margin-bottom: 15px;">{{ posts.commentTimes }}条回复
                  {{
                    posts.starTimes }}次收藏</h1>
              </el-col>
              <!-- <el-col :span="2">
                <el-button type="primary" style="margin-top: 10px;" @click="deletePostStar(posts)">取消收藏</el-button>
              </el-col> -->
            </el-row>
          </div>
          <el-scrollbar class="scrollbar-comment">
            <div class="like-grid-content" style="padding: 10px;">
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
              <textarea class="chat-input no-border" v-model="newMessage"></textarea>
            </div>
            <div class="btn_box">
              <el-button type="primary" class="btn" @click="returnDiscussion(newMessage)"
                style="float: right;">返回</el-button>
              <el-button type="primary" class="btn" @click="addOneComment(newMessage)"
                style="float: right;">发送</el-button>
            </div>
          </div>
        </el-main>

      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import { ref, onMounted } from 'vue';
import { reqUserInfo, addComment, reqOneDiscussion, reqAllComment, deleteStar, requireAvatar, reqDeleteComment } from '@/api/api'; // 假设这是更新帖子权限状态的API
import { ElNotification, ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

export default {
  setup() {
    // 使用ref创建响应式数据

    const mockData = {
      userName: '张三',
      userId: 'T20220001',
      dept: '计算机科学与技术',
      email: 'zhangsan@example.com',
      avatarUrl: 'src/assets/images/example.jpg'
    };

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
        commentUserName: '郑宇煊',
        PostingTime: '2024-11-11',
      },
      {
        index: 2,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUserName: 'zxc',
        PostingTime: '2024-11-12',
      },
      {
        index: 3,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUserName: '爱学习',
        PostingTime: '2024-11-14',
      },
      {
        index: 4,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUserName: '爱学习',
        PostingTime: '2024-11-14',
      },
      {
        index: 5,
        Information: 'line-height的值应该大于或等于文本的字体大小，以确保行间距生效。如果文本的字体大小小于line-height的值，行间距将不会显示出来。如果你的文本字体大小小于1.25或1.5，你可能需要调整line-height的值或字体大小以得到期望的行间距效果。',
        commentUserName: '爱学习',
        PostingTime: '2024-11-14',
      },
    ]);
    const userInfo = ref([]);

    // 定义方法
    const posts = ref([]);//界面展示的
    const $router = useRouter(); // 获取路由实例
    const comments = ref([]);//界面展示的
    const newMessage = ref(""); // 输入框的绑定值

    const isTeacher = () => {
      const currentUserId = userInfo.value.userId || 'S001'
      console.log(currentUserId)
      return currentUserId.startsWith('T')

    }
    const addOneComment = async (val) => {
      const discussionId = localStorage.getItem('discussionId')
      const formData = new FormData()
      formData.append('discussionId', discussionId)
      formData.append('commentInfo', val)
      //TODO:添加评论图片和@其他人
      formData.append('imgUrl', '1')
      formData.append('mentionedUser', '2')
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
    const returnDiscussion = () => {
      $router.push({ name: 'bemarkedposts' })
    }

    const deletePostStar = async (post) => {
      // 取消收藏
      const formData = new FormData()
      console.log(post.favoriteNo)
      formData.append('favoriteNo', post.favoriteNo)
      formData.append('favoriteInformationNo', post.DiscussionId)
      try {
        const response = await deleteStar(formData)
        if (response.code === 0) {
          ElMessage.success('取消收藏成功')
          $router.push({ name: 'bemarkedposts' })
        }
      } catch (error) {
        console.error('取消收藏失败:', error);
        ElNotification({
          type: 'error',
          message: '取消收藏失败，请重试。',
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

    onMounted(async () => {
      try {
        const discussionId = localStorage.getItem('discussionId')
        const userResponse = await reqUserInfo();
        const postResponse = await reqOneDiscussion(discussionId);
        const commentResponse = await reqAllComment(discussionId);
        const avatarResponse = await requireAvatar();
        userInfo.value = userResponse.data;
        posts.value = postResponse.data;
        comments.value = commentResponse.data;
        userInfo.value.avatarUrl = avatarResponse.data;
      } catch (error) {
        console.log(error)
        userInfo.value = mockData;
        posts.value = mockPost;
        comments.value = mockReply.value;
        ElNotification({
          type: 'error',
          message: '获取帖子详细信息失败',
        });
      }
    });




    return {
      posts,
      mockData,
      userInfo,
      mockPost,
      mockReply,
      addOneComment,
      comments,
      returnDiscussion,
      deletePostStar,
      showDeleteButton,
      deleteReply,
      isTeacher,
    };
  },
};
</script>

<style>
.postDetailMain {
  height: 660px;
}

.scrollbar-comment {
  height: 280px;
}

.student-head {
  background-color: #005bac;

}

.teacher-head {
  background-color: #7c0406;

}


.backmain1 {
  height: 705px;
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

.like-grid-content {
  border-radius: 4px;
  min-height: 36px;
  height: 100px;
  margin-bottom: 10px;

}

.backleft {
  width: "350px";
  max-height: "550px";
  background-color: #eaf6ff;
}

.whiteback {
  border-radius: 4px;
  max-height: 350px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

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

.chat-input {
  width: 100%;
  height: 80px;
  padding: 12px 16px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  resize: none;
  font-size: 14px;
  color: #606266;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: border-color 0.3s, box-shadow 0.3s;
  margin-top: 10px;
}


.scrollable {
  height: 290px;
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