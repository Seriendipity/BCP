<template>
  <div class="Intro">
    <el-descriptions class="margin-top" title="帖子信息" :column="3" :size="size">
    </el-descriptions>
    <el-form :model="changedData" class="form" label-width="100px">

      <el-form-item label="帖子内容">
        <el-input v-model="changedData.information" placeholder="输入帖子内容" type="textarea" style="width: 60%;"></el-input>
      </el-form-item>
      <el-form-item label="帖子主题">
        <el-input v-model="changedData.tag" type="studentNumbers" placeholder="输入主题" style="width: 15%;"></el-input>
      </el-form-item>
    </el-form>

    <el-row :gutter="20">
      <el-col :span="2">
        <!-- 为空格空出来的 -->
      </el-col>
      <el-col :span="12">
        <el-button type="primary" @click="saveData" round
          style="font-size: medium;font-weight:bold;margin-right: 40px;">发布</el-button>
        <el-button type="primary" @click="goBack" round style="font-size: medium;font-weight:bold;">返回</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { addDiscussion } from '@/api/api';
import { ElNotification, ElMessage } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    // const selectedFile = ref(null); // 存储选中的文件
    let $router = useRouter();
    const changedData = ref({});

    const currentData = ref({});
    // const handleChange = (event) => {
    //   const target = event.target;
    //   if (target.files && target.files.length > 0) {
    //     selectedFile.value = target.files[0]; // 存储选中的文件
    //   }
    //   console.log(selectedFile.value)
    // };
    const saveData = async () => {
      // 更新 tableData 中对应的数据
      const formData = new FormData()
      const storedCourseId = localStorage.getItem('courseId');
      formData.append('cid', storedCourseId)
      formData.append('information', changedData.value.information)
      //TODO:添加@其他人和评论图片
      formData.append('mentionedUser', null)
      formData.append('tag', changedData.value.tag)
      formData.append('imgUrl', null)
      try {
        const response = await addDiscussion(formData); // 连接后端更新文件
        if (response.code === 0) {
          ElMessage.success("发帖成功");
        }
        $router.push({ name: 'discussionArea' });
      } catch (error) {
        console.log(error)
        ElNotification({
          message: '发帖失败，请重试',
          type: 'error',
        });
      }

    };

    const goBack = () => {
      $router.push({ name: 'discussionArea' });
    }

    return {

      changedData,
      currentData,
      saveData,
      goBack,
    };
  },
};
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: auto;
  /* 自适应高度 */
  border: groove;
  overflow: hidden;
  max-width: 100%;
  /* 最大宽度100% */
  padding: 20px;
  /* 内边距 */
}

.form {
  margin-top: 20px;
}
</style>