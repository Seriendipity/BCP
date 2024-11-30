<template>
  <div class="Intro">
    <div class="notification-form">
      <el-form :model="form" label-width="80px">
        <!-- 主题输入 -->
        <el-form-item label="通知主题">
          <el-input v-model="form.notificationTitle" placeholder="请输入通知主题"></el-input>
        </el-form-item>
        <!-- 内容输入 -->
        <el-form-item label="通知内容">
          <el-input type="textarea" v-model="form.notificationInformation" placeholder="请输入通知内容" rows="10"></el-input>
        </el-form-item>
        <!-- 发布按钮 -->
        <el-form-item>
          <el-button type="primary" @click="publishNotification" size="large">发布通知</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { postNotification } from "@/api/api";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      form: {
        cid: "",                  //课程序号
        notificationTitle: "",        // 通知主题
        notificationInformation: "",      // 通知内容
        users: "all"
      },
    };
  },
  methods: {
    // 发布通知
    async publishNotification() {
      if (!this.form.notificationTitle || !this.form.notificationInformation) {
        ElMessage.error("请填写通知的主题和内容");
        return;
      }


      try {
        // 发送通知到后端
        const response = await postNotification(this.form);
        if (response.code === 0) {
          ElMessage.success("通知发布成功");
          // 清空表单
          this.form.notificationTitle = "";
          this.form.notificationInformation = "";
          this.form.cid = "";
        } else {
          ElMessage.error("通知发布失败，请重试");
        }
      } catch (error) {
        console.error(error);
        ElMessage.error("发布失败，请检查网络连接");
      }
    },
  },
  mounted() {
    // 在组件加载时尝试获取发布课程序号
    const cid = localStorage.getItem('courseId');
    if (cid) {
      this.form.cid = cid
    }
  },
};
</script>

<style scoped>
/* 父容器 */
.Intro {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  padding: 20px;
}

/* 表单容器 */
.notification-form {
  height: 550px;
  width: 1200px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 40px;
}

/* 表单项 */
.el-form-item {
  margin-bottom: 20px;
}

/* 输入框 */
.el-input {
  width: 100%;
}

/* 发布按钮 */
.el-button {
  width: 100%;
  border-radius: 10px;
  font-size: 18px;
}
</style>
