<template>
  <div class="chat-container">
    <div v-for="(message, index) in messages" :key="index"
      :class="message.align === 'left' ? 'message-left' : 'message-right'">
      <div class="name">
        <span>{{ message.name }} {{ message.time }} </span>
      </div>
      <div class="chat_message">
        {{ message.text }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatBox",
  data() {
    return {
      messages: [
        { text: "你好！", align: "left", name: "王阳阳", time: "18:07" },
        { text: "你好！", align: "right", name: "丽丝", time: "19:21" },
        { text: "如何才能帮助您？", align: "left", name: "王阳阳", time: "21:26" },
        {
          text: "我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发",
          align: "right",
          name: "丽丝",
          time: "22:37"
        }
      ]
    }
  }
}
</script>

<style scoped>
.chat-container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 400px;
  background: #ffffff;
  border: 1px solid #e4e7ed;
  border-radius: 3px;
  padding: 16px;

  .name {
    font-family: PingFangSC-Regular;
    font-weight: 400;
    font-size: 12px;
    color: #909399;
    margin-bottom: 6px;
  }
}

.chat_message {
  padding: 6px 12px;
  background: #f8f8f9;
  border-radius: 4px;
  margin-bottom: 15px;
  word-wrap: break-word;
  font-weight: 400;
  font-size: 14px;
  color: #303133;
}

.message-left {
  max-width: 418px;
  align-self: flex-start;

  .chat_message {
    background-color: #f8f8f9;
  }

  .name {
    align-self: flex-start;
  }
}

.message-right {
  max-width: 418px;
  align-self: flex-end;
  display: flex;
  flex-direction: column;

  .chat_message {
    background-color: #ebf3ff;
  }

  .name {
    align-self: flex-end;
  }
}
</style>