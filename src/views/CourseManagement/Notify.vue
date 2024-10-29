<template>
  <div class="Intro">
    <div class="notification-form">
      <el-form :model="form" label-width="80px">
        <!-- 主题输入 -->
        <el-form-item label="通知主题">
          <el-input v-model="form.title" placeholder="请输入通知主题"></el-input>
        </el-form-item>
        <!-- 内容输入 -->
        <el-form-item label="通知内容">
          <el-input type="textarea" v-model="form.message" placeholder="请输入通知内容" rows="10"></el-input>
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
import axios from "axios";
import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      form: {
        title: "",        // 通知主题
        message: "",      // 通知内容
        time: "",         // 通知发布时间
        publisherName: "" // 通知发布人姓名
      },
    };
  },
  methods: {
    // 发布通知
    async publishNotification() {
      if (!this.form.title || !this.form.message) {
        ElMessage.error("请填写通知的主题和内容");
        return;
      }

      // 获取当前时间作为发布时间
      this.form.time = new Date().toLocaleString();

      // 从 localStorage 获取发布人姓名
      const storedName = localStorage.getItem('publisherName');
      this.form.publisherName = storedName || "未知发布人"; // 如果为空则设置默认值

      try {
        // 发送通知到后端
        const response = await axios.post(postNotification, this.form);
        if (response.status === 200) {
          ElMessage.success("通知发布成功");
          // 清空表单
          this.form.title = "";
          this.form.message = "";
          this.form.time = "";
          this.form.publisherName = "";
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
    // 在组件加载时尝试获取发布人姓名
    const storedName = localStorage.getItem('userName');
    if (storedName) {
      this.form.publisherName = storedName;
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
