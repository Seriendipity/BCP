<template>
  <el-main height="600px">
    <h1 style="text-align: left; font-weight: bold; margin-bottom: 10px; font-size: large; margin-top: -12px;">通知列表</h1>
    <div class="notification-scrollable">
      <el-row :gutter="20">
        <el-col :span="24" v-for="(notification, index) in notifications" :key="index">
          <div class="notification-bar">
            <div class="notification-info">
              <h2 class="notification-title">{{ notification.notificationTitle }}</h2>
              <p class="notification-time">发布时间: {{ notification.notificationPostingTime }}</p>
            </div>
            <el-tag :type="notification.NotificationState === '已读' ? 'success' : 'warning'">
              {{ notification.NotificationState }}
            </el-tag>
            <el-button class="view-button" type="primary" size="small"
              @click="viewNotification(notification)">查看通知</el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 查看通知的弹出框 -->
    <el-dialog v-model="dialogVisible" title="查看通知" width="600px" class="notification-dialog">
      <div class="dialog-content">
        <p class="dialog-title"><strong>主题:</strong> {{ currentNotification.notificationTitle }}</p>
        <p class="dialog-info"><strong>内容:</strong> {{ currentNotification.notificationInfo }}</p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog" type="primary">关闭</el-button>
      </span>
    </el-dialog>
  </el-main>
</template>

<script>
import { ref, onMounted } from 'vue';
import { reqNotificationStudent, updateNotificationState } from '@/api/api'; // 确保有这个API接口
import { ElNotification } from 'element-plus';

export default {
  setup() {
    const dialogVisible = ref(false);
    const currentNotification = ref({});
    const notifications = ref([]);

    // 模拟数据
    const mockData = [
      {
        notificationTitle: '课程调整通知',
        notificationInfo: '由于特殊情况，本周的课程时间将调整，请查看具体时间安排。',
        notificationPostingTime: '2024-10-28 14:00',
        NotificationState: '未读',
      },
      {
        notificationTitle: '期末考试安排',
        notificationInfo: '请注意，期末考试将在12月1日举行，请提前做好复习准备。',
        notificationPostingTime: '2024-10-29 09:00',
        NotificationState: '已读',
      },
      {
        notificationTitle: '社团活动通知',
        notificationInfo: '下周五有社团活动，欢迎大家参加！',
        notificationPostingTime: '2024-10-30 11:00',
        NotificationState: '未读',
      },
    ];

    // 获取通知数据
    const fetchNotifications = async () => {
      try {
        const storedCourseId = localStorage.getItem('courseId');
        const response = await reqNotificationStudent(storedCourseId); // 获取通知数据
        notifications.value = response.data || []; // 更新通知数据
      } catch (err) {
        // 捕获错误并使用模拟数据
        notifications.value = mockData;
        ElNotification({
          message: '获取通知失败',
          type: 'error',
        });
      }
    };

    onMounted(() => {
      fetchNotifications(); // 组件挂载时获取通知数据
    });

    // 查看通知详情
    const viewNotification = (notification) => {
      currentNotification.value = notification; // 设置当前通知
      dialogVisible.value = true; // 打开弹出框
      if (notification.NotificationState === '未读') {
        notification.NotificationState = '已读';
        notifications.value[index] = { ...notification }; // 更新本地数据
        updateNotificationState(notification); // 调用后端API更新状态
      }
    };

    // 关闭弹出框
    const closeDialog = () => {
      dialogVisible.value = false; // 关闭弹出框
    };

    return {
      dialogVisible,
      currentNotification,
      notifications,
      viewNotification,
      closeDialog,
    };
  },
};
</script>

<style scoped>
.notification-scrollable {
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 500px;
  overflow-y: auto;
}

.notification-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #ffffff;
  transition: background 0.3s ease;
}

.notification-bar:hover {
  background: #f5f7fa;
}

.notification-info {
  display: flex;
  flex-direction: column;
}

.notification-title {
  font-size: 16px;
  font-weight: bold;
}

.notification-time {
  color: #999;
}

.view-button {
  margin-left: 10px;
}

/* 弹出框样式 */
.notification-dialog .dialog-content {
  padding: 15px;
}

.dialog-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.dialog-info {
  font-size: 16px;
  line-height: 1.6;
  color: #666;
}

.dialog-footer {
  text-align: right;
}
</style>
