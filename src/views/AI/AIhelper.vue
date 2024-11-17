<template>
  <el-container style="height: 100%" ref="bodyform">
    <div class="el_main_content">
      <div class="chat-container">
        <div v-for="(message, index) in messages" :key="index"
          :class="message.align === 'left' ? 'message-left' : 'message-right'">
          <div class="name">
            <span>{{ message.name }} </span>
          </div>
          <div class="chat_message">
            {{ message.text }}
          </div>
        </div>
      </div>
      <div class="main_content_footer">
        <div class="input_box">
          <textarea class="chat-input no-border" v-model="question" />
        </div>
        <div class="btn_box">
          <el-button type="primary" class="btn" @click="askClick(question)">发送</el-button>
        </div>
      </div>
    </div>
  </el-container>
</template>

<script>
import { ref } from "vue";
import { ElMessage, ElNotification } from "element-plus";
import { ai_Helper } from "@/api/api";

export default {
  setup() {
    // 定义响应式数据
    const messages = ref([])
    const mockMessages = ref([
      { text: "你好！", align: "left", name: "王阳阳", time: "18:07" },
      { text: "你好！", align: "right", name: "丽丝", time: "19:21" },
      { text: "如何才能帮助您？", align: "left", name: "王阳阳", time: "21:26" },
      {
        text: "我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发",
        align: "right",
        name: "丽丝",
        time: "22:37",
      },
    ]);

    const question = ref(""); // 输入框的绑定值

    const askClick = async (val) => {
      if (val.trim()) {
        const userName = localStorage.getItem("userName") || "默认用户";
        messages.value.push({
          text: val, // 用户输入的消息
          name: userName, // 用户名
          align: "right",
        });
        question.value = ""; // 发送消息后清空输入框
      } else {
        ElMessage.warning("不能发送空白消息");
      }
      const formData = new FormData()
      formData.append('question', val)
      try {
        const response = await ai_Helper(formData)
        if (response.code === 0) {
          messages.value.push({
            text: response.data,
            name: "智慧课堂小助手",
            align: "left",
          });
        }
      } catch (error) {
        messages.value = mockMessages.value
        console.log(error)
        ElNotification({
          message: '提问失败，请重试',
          type: 'error',
        });
      }
    };

    return {
      messages,
      question,
      askClick,
    };
  },
};

</script>

<style scoped>
.el_main_content {
  width: 100%;
  height: 700px;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
  margin: auto;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 600px;
  background: #ffffff;
  border-radius: 8px 8px 0 0;
  padding: 16px;
  overflow-y: auto;
}

.name {
  font-family: "PingFangSC-Regular", sans-serif;
  font-weight: 400;
  font-size: 13px;
  color: #606266;
  margin-bottom: 6px;
}

.chat_message {
  padding: 10px 14px;
  background: #f5f5f5;
  border-radius: 12px;
  margin-bottom: 15px;
  word-wrap: break-word;
  font-weight: 400;
  font-size: 14px;
  color: #333;
  line-height: 1.5;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.message-left {
  max-width: 70%;
  align-self: flex-start;

  .chat_message {
    background-color: #f5f5f5;
  }

  .name {
    align-self: flex-start;
  }
}

.message-right {
  max-width: 70%;
  align-self: flex-end;
  display: flex;
  flex-direction: column;

  .chat_message {
    background-color: #e0f7fa;
  }

  .name {
    align-self: flex-end;
  }
}

.main_content_footer {
  width: 100%;
  height: 150px;
  background-color: #f5f5f5;
  border-top: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10px 16px;
}

.input_box {
  flex: 1;
  display: flex;
  align-items: center;


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

  .chat-input:focus {
    border-color: #409eff;
    box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
    outline: none;
  }
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
