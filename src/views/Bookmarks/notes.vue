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
              <div style="margin-top: 10px"><el-avatar :size="40" :src="circleUrl"></el-avatar></div>
            </router-link>
          </div>
        </el-col>
        <el-col :span="2">
          <router-link to="/myinformation" style="text-decoration: none;">
            <h1 style="font-size: medium;margin-top: 21px;color: aliceblue;font-weight: 550;">爱学习</h1>
          </router-link>
        </el-col>
      </el-row>
    </el-header>

    <el-main class="backmain1">
      <el-container>
        <el-aside class="backleft">
          <div class="whiteback" :data="student">
            <h1 style="text-align: left; font-weight: bold;margin-bottom: 5px;">个人信息</h1>
            <div style="margin-top: 20px;text-align: center;"><el-avatar :size="100" :src="circleUrl"></el-avatar></div>
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
              <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;">我的笔记</el-button>
            </el-col>
            <el-col :span="21">
              <router-link to="/othersnotes" style="text-decoration: none;">
                <el-button type="primary" style="text-align: left; font-weight: bold;font-size: large;" plain>浏览笔记</el-button>
              </router-link>
            </el-col>
            <div style="margin-left: 87%;">
              <el-button type="primary" round @click="dialogVisible = true" style="font-size: large; font-weight: bold;">新建笔记</el-button>
            </div>
          </el-row>
          <div class="grid-content bg-white" style="height: 75px;" v-for="note in notes" :key="note.title">
            <el-row :gutter="20">
              <el-col :span="13">
                <h1 class="ziti03" style="margin-top: 5px;">{{ note.title }}</h1>
                <h1 class="ziti04" style="color: gray;margin-top: 15px;">{{ note.uploadDate }}上传</h1>
              </el-col>
              <el-col :span="4">
                <h1 class="ziti04" style="color: gray;margin-top: 28px;text-align: right;">是否公开</h1>
              </el-col>
              <el-col :span="2">
                <el-switch
                  style="margin-top: 20px;"
                  v-model="note.isPublic"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" style="margin-top: 20px;" plain @click="updateNote(note)">更新</el-button>
              </el-col>
              <el-col :span="3">
                <el-button type="primary" style="margin-top: 20px;" plain @click="previewNote(note)">查看</el-button>
              </el-col>
              <!-- <el-col :span="3">
                <el-button type="primary" style="margin-top: 20px;" plain @click="downloadNote(note)">下载</el-button>
              </el-col> -->
            </el-row>
          </div>
        </el-main>

      
      </el-container>
    </el-main>
  </el-container>

  <el-dialog :visible.sync="dialogVisible" title="上传文件" @close="resetForm">
    <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      :on-change="handleChange"
      :on-success="handleSuccess"
      :show-file-list="false">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip" type="success">支持扩展名：.pdf/.doc/.docx</div>
    </el-upload>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="uploadFile">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { reqUserInfo, reqCourseList, reqCourseIntro, reqNotificationAll } from '@/api/api';
import { ElNotification } from 'element-plus';

const courses = ref([]);
const student = ref([]);
const router = useRouter();
const dialogVisible = ref(false);
const currentNotification = ref({});
const notifications = ref([]);

// const student = {
//   studentName: '张三',
//   studentNo: '20220001',
//   dept: '计算机科学与技术',
//   email: 'zhangsan@example.com'
// };

const circleUrl = 'https://example.com/avatar.jpg';

const notes = ref([
  { title: '数据结构第一次课程笔记', uploadDate: '2024年10月5日', isPublic: true },
  { title: '算法第3次作业答案', uploadDate: '2023年9月5日', isPublic: false },
  { title: '软件测试小测原题', uploadDate: '2022年8月5日', isPublic: true }
]);

const uploadFile = () => {
  // 文件上传逻辑
  dialogVisible.value = false;
};

const previewNote = (note) => {//TODO
  // 预览逻辑，可以使用 window.open 或者其他方式展示文件
  // alert(`预览: ${note.title}`);
  ElMessageBox.alert(`预览文件: ${note.title}`, "预览", { confirmButtonText: "确定" });
};


const updateNote = (note) => {//TODO
  alert('更新后将会覆盖历史文件');
  // 更新逻辑
};



const resetForm = () => {
  // 重置上传表单
};

// 获取用户信息和课程列表
onMounted(async () => {
  try {
    //TODO
    const userResponse = await reqUserInfo();
    const noteResponse = await reqNoteList();
    student.value = userResponse.data;
    notes.value = notesResponse.data;
    localStorage.setItem('userName', student.value.studentName);
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取信息失败',
    });
  }
});

const goToNoteInfo = async (noteNo) => {//TODO
  try {
    const response = await reqNoteIntro(courseId);
    if (response && response.data) {
      const currentQuery = router.currentRoute.value.query; // 获取当前查询参数
      localStorage.setItem('courseId', courseId);
      router.push({
        path: '/CourseInfo',
        query: {
          ...currentQuery, // 保留当前查询参数
          courseId,
          courseIntro: response.data.courseInfo,
          courseName: response.data.courseName,
          courseNo: response.data.courseNo,
          teacherName: response.data.teacherName,
          establishCollege: response.data.establishCollege,
          semester: response.data.semester,
        },
      });
    }
  } catch (error) {
    ElNotification({
      type: 'error',
      message: '获取课程信息失败',
    });
  }
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
  height: 250px;
  margin-bottom: 10px;

}

body>.el-container {
  margin-bottom: 40px;
}
</style>