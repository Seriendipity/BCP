<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable @typescript-eslint/no-unused-vars -->
<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
  <el-container>
    <el-header class="teacher-head" v-if="isTeacher()">
      <el-row :gutter="20">
        <el-col :span="17">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; ">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content " style="text-align: right;">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1
                style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
                笔记
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content ">
            <router-link to="/bookmarks" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">收藏夹
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
        <el-col :span="17">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; ">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content " style="text-align: right;">
            <router-link to="/firstpage" style="text-decoration: none;">
              <h1
                style="font-size:x-large;margin-top: 17px;color: white;text-shadow: 2px 3px rgba(0, 0, 0, 0.5);font-weight: 550;">
                笔记
              </h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content ">
            <router-link to="/bookmarks" style="text-decoration: none;">
              <h1 style="font-size:larger;margin-top: 21px;color: aliceblue;font-weight: 550;">收藏夹
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
            <h1 class="ziti01">{{ userInfo.userIdentity }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ userInfo.userName }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ userInfo.userId }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ userInfo.dept }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ userInfo.email }}</h1>
          </div>
        </el-aside>

        <el-main height="600px">
          <el-row>
            <el-col :span="3">
              <router-link to="/notes" style="text-decoration: none;">
                <!-- <h1 style="text-align: left; font-weight: bold;font-size: large;margin-top:-12px;"> 我的笔记</h1> -->
                <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;"
                  plain>我的笔记</el-button>
              </router-link>
            </el-col>
            <el-col :span="21">
              <!-- <h1 style="text-align: left; font-weight: bold;font-size: large;margin-top:-12px;"> 我的笔记</h1> -->
              <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;">所有笔记</el-button>
            </el-col>
            <!-- <div style="margin-left: 87%;">
                <el-button type="primary" round style="width: 80px;font-size: large; font-weight: bold;margin-bottom: -5px;">上传</el-button>
                </div> -->
          </el-row>
          <!-- <div class="scrollable"> -->
          <div class="grid-content bg-white" style="height: 75px;" v-for="note in notes" :key="note.noteNo">
            <el-row :gutter="20">
              <el-col :span="18">
                <h1 class="ziti03">{{ note.noteInfo }}</h1>
                <h1 class="ziti04" style="margin-top: 5px;">{{ note.username }}</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 5px;">{{ note.uploadDate }}上传</h1>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-top: 20px;" plain @click="updateLikeStatus(note)">收藏</el-button>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-top: 20px;" plain @click="previewNote(note)">查看</el-button>
              </el-col>
            </el-row>
          </div>
        </el-main>
      </el-container>
    </el-main>
  </el-container>

  <el-dialog v-model="previewVisible" title="预览" width="800px">
    <iframe class="calendar" :src="noteSrc" width="100%" height="400px" style="border:none;"></iframe>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="previewVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { requireAllNote, reqUpdateLike, reqUserInfo, requireAvatar } from '@/api/api';
import { ElNotification } from 'element-plus';

const userInfo = ref([]);
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const dialogVisible = ref(false);
const previewVisible = ref(false);
const mockData = {
  userName: '张三',
  userIdentity:'学生',
  userId: 'T20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});
const noteSrc = ref(props.previewSrc)

const notes = ref([
  { noteInfo: '数据结构第一次课程笔记', uploadDate: '2024年10月5日', username: 'jty', favorite: true },
  { noteInfo: '算法第3次作业答案', uploadDate: '2023年9月5日', username: 'mhb', favorite: false },
  { noteInfo: '软件测试小测原题', uploadDate: '2022年8月5日', username: 'zxc', favorite: false }
]);

const previewNote = (note) => {//TODO:跳转到一个专门的预览界面
  noteSrc.value = note.notePath.split('/').pop();
  console.log(noteSrc)
  previewVisible.value = true;
};

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const response = await requireAvatar();
    const userResponse = await reqUserInfo();
    const noteResponse = await requireAllNote();
    userInfo.value = userResponse.data;
    notes.value = noteResponse.data;
    userInfo.value.avatarUrl = response.data;
  } catch (error) {
    console.log(error)
    userInfo.value = mockData;
    notes.value = notes;
    ElNotification({
      type: 'error',
      message: '获取信息失败',
    });
  }
});


const isTeacher = () => {
  const currentUserId = userInfo.value.userId || 'S001'
  console.log(currentUserId)
  return currentUserId.startsWith('T')

}
const updateLikeStatus = async (note) => {
  //更新收藏状态
  try {
    await reqUpdateLike({
      favoriteInformationNo: note.noteNo,
      favoriteTitle: note.noteInfo,
    });
    ElNotification({
      type: 'success',
      message: '收藏成功。',
    });
  } catch (error) {
    console.error('更新收藏状态失败:', error);
    ElNotification({
      type: 'error',
      message: '更新收藏状态失败，请重试。',
    });
  }
}


</script>


<style>
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


.scrollable {
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
  height: 250px;
  margin-bottom: 10px;

}

body>.el-container {
  margin-bottom: 40px;
}

.favorite {
  color: gold;
}
</style>