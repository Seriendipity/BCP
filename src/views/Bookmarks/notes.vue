<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <el-container>
    <el-header class="head">
      <el-row :gutter="20">
        <el-col :span="17">
          <div class="grid-content">
            <router-link to="/firstpage" style="text-decoration: none; ">
              <h1 class="zitihead">智慧课程平台</h1>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content" style="text-align: right;">
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
          <div class="grid-content">
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

    <el-main class="backmain2">
      <el-container>
        <el-aside class="backleft">
          <div class="whiteback" :data="userInfo">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
            <div style="margin-top: 20px;text-align: center;"><el-avatar :size="100"
                :src="userInfo.avatarUrl"></el-avatar>
            </div>
            <h1 class="ziti01">学生</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">姓名：{{ userInfo.userName }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学号：{{ userInfo.userId }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">学院：{{ userInfo.dept }}</h1>
            <h1 class="ziti02" style="text-align: left;padding-left: 15px;">邮箱：{{ userInfo.email }}</h1>
          </div>
        </el-aside>

        <el-main height="600px">
          <el-row>
            <el-col :span="3">
              <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;">我的笔记</el-button>
            </el-col>
            <el-col :span="21">
              <router-link to="/othersnotes" style="text-decoration: none;">
                <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;"
                  plain>浏览笔记</el-button>
              </router-link>
            </el-col>
            <div style="margin-left: 87%;">
              <el-button type="primary" round @click="dialogVisible = true"
                style="font-size: large; font-weight: bold;">新建笔记</el-button>
            </div>
          </el-row>
          <div class="grid-content bg-white" style="height: 75px;" v-for="note in notes" :key="note.noteNo">
            <el-row :gutter="20">
              <el-col :span="13">
                <h1 class="ziti03" style="margin-top: 5px;">{{ note.noteInfo }}</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ note.uploadDate }}上传</h1>
              </el-col>
              <el-col :span="4">
                <h1 class="ziti04" style="color: gray;margin-top: 28px;text-align: right;">是否公开</h1>
              </el-col>
              <el-col :span="2">
                <el-switch style="margin-top: 20px;" v-model="note.authority" active-color="#13ce66"
                  inactive-color="#ff4949" @click="updateNoteStatus(note)">
                </el-switch>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 20px;" plain @click="updateNote(note)">更新</el-button>
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

  <el-dialog v-model="dialogVisible" title="上传笔记">
    <el-form :model="form">
      <el-form-item label="主题" prop="title">
        <el-input v-model="newNoteInfo" placeholder="请输入主题"></el-input>
      </el-form-item>
      <div class="file-input-container">
        <input type="file" id="fileInput" @change="handleChange" />
      </div>
      <template v-slot:tip>
        <div class="el-upload__tip" type="success">支持扩展名：.pdf/.doc/.docx</div>
      </template>
    </el-form>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadFile">确 定</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogUpdateVisible" title="更新笔记">
    <el-form :model="form">
      <el-form-item label="主题" prop="title">
        <el-input v-model="nowNoteInfo" placeholder="请输入主题"></el-input>
      </el-form-item>
      <div class="file-input-container">
        <input type="file" id="fileInput" @change="handleChange" />
      </div>
      <template v-slot:tip>
        <div class="el-upload__tip" type="success">支持扩展名：.pdf/.doc/.docx</div>
      </template>
    </el-form>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogUpdateVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadNewFile">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="previewVisible" title="预览" width="800px">
    <iframe class="calendar" :src="noteSrc" width="100%" height="400px" style="border:none;"></iframe>
    <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="previewVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="js" setup>
import { ref, onMounted } from 'vue';
import { requireAvatar, reqUserInfo, requireMyNote, reqAddNote, reqUpdateVisible, reqUpdateNote } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';

const props = defineProps({
  previewSrc: {
    type: String,
    required: false,
    default: () => 'https://book.yunzhan365.com/tnhkz/uvaj/mobile/index.html'
  }
});
const noteSrc = ref(props.previewSrc)

const userInfo = ref([]);
const dialogVisible = ref(false);
const previewVisible = ref(false);
const dialogUpdateVisible = ref(false);
const selectedFile = ref(null); // 存储选中的文件
const selectNoteNo = ref();
const newNoteInfo = ref('');
const nowNoteInfo = ref('');

const mockData = {
  userName: '张三',
  userId: '20220001',
  dept: '计算机科学与技术',
  email: 'zhangsan@example.com',
  avatarUrl: 'src/assets/images/example.jpg'
};





const notes = ref([
  { noteInfo: '数据结构第一次课程笔记', uploadDate: '2024年10月5日', authority: true },
  { noteInfo: '算法第3次作业答案', uploadDate: '2023年9月5日', authority: false },
  { noteInfo: '软件测试小测原题', uploadDate: '2022年8月5日', authority: true }
]);



const handleChange = (event) => {
  const target = event.target;
  if (target.files && target.files.length > 0) {
    selectedFile.value = target.files[0]; // 存储选中的文件
  }
  console.log(selectedFile.value)
};



const uploadFile = async () => {
  if (!selectedFile.value) {
    ElNotification({
      message: '请选择一个文件',
      type: 'error',
    });
    return;
  }
  const formData = new FormData();
  formData.append('file', selectedFile.value); // 传递文件
  formData.append('noteInfo', newNoteInfo.value);
  try {
    const response = await reqAddNote(formData); // 连接后端上传文件
    selectedFile.value = null; // 上传后清空选择的文件
    if (response.code === 0) {
      ElNotification({
        message: "笔记上传成功",
        type: 'success',
      });
      window.location.reload();
    }
  } catch (error) {
    console.error('上传笔记失败', error);
    ElNotification({
      message: '上传笔记失败，请重试',
      type: 'error',
    });
  }
  newNoteInfo.value = '';
  selectedFile.value = '';
  dialogVisible.value = false;
};

const previewNote = (note) => {//TODO:跳转到一个专门的预览界面
  noteSrc.value = note.notePath.split('/').pop();
  console.log(noteSrc)
  previewVisible.value = true;
};


const updateNote = (note) => {
  alert('更新后将会覆盖历史文件');
  dialogUpdateVisible.value = true;
  nowNoteInfo.value = note.noteInfo;
  selectNoteNo.value = note.noteNo;
  console.log(nowNoteInfo.value)

  // 更新逻辑
};

const uploadNewFile = async () => {
  if (!selectedFile.value) {
    ElNotification({
      message: '请选择一个文件',
      type: 'error',
    });
    return;
  }
  const formData = new FormData();
  formData.append('notePath', selectedFile.value); // 传递文件
  formData.append('noteInfo', nowNoteInfo.value);
  formData.append('noteNo', selectNoteNo.value);
  try {
    const response = await reqUpdateNote(formData); // 连接后端更新文件
    selectedFile.value = null; // 更新后清空选择的文件
    if (response.code === 0) {
      ElMessage.success("笔记更新成功");
    }
  } catch (error) {
    console.error('更新笔记失败', error);
    ElNotification({
      message: '更新笔记失败，请重试',
      type: 'error',
    });
  }
  nowNoteInfo.value = '';
  selectNoteNo.value = '';
  selectedFile.value = '';
  dialogUpdateVisible.value = false;
};


const updateNoteStatus = async (note) => {
  //更新公开状态
  try {
    await reqUpdateVisible({
      noteNo: note.noteNo,
    });
  } catch (error) {
    console.error('更新笔记浏览权限失败:', error);
    ElNotification({
      type: 'error',
      message: '更新笔记浏览权限失败，请重试。',
    });
  }
}

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    const avatarResponse = await requireAvatar();
    const userResponse = await reqUserInfo();
    const noteResponse = await requireMyNote();
    userInfo.value = userResponse.data;
    notes.value = noteResponse.data;
    userInfo.value.avatarUrl = avatarResponse.data;

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
</script>





<style>
.head {
  background-color: #005bac;

}

.backmain2 {
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