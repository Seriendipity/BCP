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
          <div class="whiteback" :data="student">
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
              <el-button type="primary"
                style="text-align: left; font-weight: bold;font-size: large;width: 100px;">笔记</el-button>
            </el-col>
            <el-col :span="3">
              <router-link to="/bemarkedposts" style="text-decoration: none;">
                <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;width: 100px;"
                  plain>帖子</el-button>
              </router-link>
            </el-col>
          </el-row>
          <!-- <div class="scrollable"> -->
          <div class="grid-content bg-white" style="height: 75px;" v-for="favor in likeNotes" :key="favor.favorNo">
            <el-row :gutter="20">
              <el-col :span="17">
                <h1 class="ziti03" style="margin-top: 5px;">{{ favor.favoriteTitle }}</h1>
                <h1 class="ziti04">{{ favor.fromUsername }}</h1>
                <h1 class="ziti04" style="color: gray;">{{ favor.notePt }} 上传 </h1>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 20px;" plain
                  @click="previewNote(favor)">预览</el-button></el-col>
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 20px;" plain
                  @click="downloadNote(favor)">下载</el-button></el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-top: 20px;" plain
                  @click="deleteNoteStar(favor)">取消收藏</el-button></el-col>
            </el-row>
          </div>

        </el-main>

      </el-container>
    </el-main>
  </el-container>

  <el-dialog v-model="previewVisible" title="预览" width="800px">
    <iframe class="notes" :src="noteSrc" width="100%" height="400px" style="border:none;"></iframe>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="previewVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { reqUserInfo, requireAvatar, reqLikeNote, deleteStar } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';

const likeNotes = ref([]);
const userInfo = ref([]);

const mockData = {
  userName: '张三',
  userIdentity:'学生',
  userId: 'T20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};

const mockNotes = ref([
  {
    favorNo: 1,
    favoriteTitle: '实训',
    fromUsername: '姜天亦',
    notePt: '2024-11-13',
  },
  {
    favorNo: 2,
    favoriteTitle: '实训',
    fromUsername: '姜天亦',
    notePt: '2024-11-13',
  },
  {
    favorNo: 3,
    favoriteTitle: '实训',
    fromUsername: '姜天亦',
    notePt: '2024-11-13',
  },
  {
    favorNo: 4,
    favoriteTitle: '实训',
    fromUsername: '姜天亦',
    notePt: '2024-11-13',
  },
  {
    favorNo: 5,
    favoriteTitle: '实训',
    fromUsername: '姜天亦',
    notePt: '2024-11-13',
  },
]);

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const userResponse = await reqUserInfo();
    const notesResponse = await reqLikeNote();
    const avatarResponse = await requireAvatar()
    userInfo.value = userResponse.data;
    likeNotes.value = notesResponse.data;
    userInfo.value.avatarUrl = avatarResponse.data;
  } catch (error) {
    console.log(error)
    userInfo.value = mockData;
    likeNotes.value = mockNotes.value;
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
const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});
const noteSrc = ref(props.previewSrc)
const previewVisible = ref(false);

const previewNote = (note) => {//TODO:跳转到一个专门的预览界面
  console.log(note)
  noteSrc.value = note.notePath.split('/').pop();
  console.log(noteSrc)
  previewVisible.value = true;
};

const downloadNote = (resource) => {
  try {
    window.open(resource.notePath); // 打开文件链接
  } catch (error) {
    console.log(error)
    ElNotification({
      message: '下载文件失败，请重试',
      type: 'error',
    });
  }
};

const deleteNoteStar = async (note) => {
  // 取消收藏
  const formData = new FormData()
  formData.append('favoriteNo', note.favoriteNo)
  formData.append('favoriteInformationNo', note.noteNo)
  try {
    const response = await deleteStar(formData)
    if (response.code === 0) {
      ElMessage.success('取消收藏成功')
      window.location.reload();
    }
  } catch (error) {
    console.error('取消收藏失败:', error);
    ElNotification({
      type: 'error',
      message: '取消收藏失败，请重试。',
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
</style>