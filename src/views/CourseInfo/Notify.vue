<template>
  <div class="notification-container">
    <el-table :data="notifications" style="width: 150vh;" border>
      <!-- 动态生成表格列 -->
      <el-table-column v-for="(column, index) in tableColumns" :key="index" :prop="column.prop" :label="column.label"
        :width="column.width">
        <template v-if="column.slot" #default="{ row }">
          <!-- 状态列的自定义内容 -->
          <el-tag :type="row.NotificationState === '已读' ? 'success' : 'warning'">
            {{ row.NotificationState }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作" width="100%">
        <template #default="{ row }">
          <el-button type="primary" @click="viewNotification(row)" size="small">查看通知</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 查看通知的弹出框 -->
    <el-dialog :v-model="dialogVisible" title="查看通知" width="600px" @close="closeDialog">
      <p><strong>主题:</strong> {{ currentNotification.notificationTitle }}</p>
      <p><strong>内容:</strong> {{ currentNotification.notificationInfo }}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { reqNotificationStudent } from '@/api/api';
import { ElNotification } from 'element-plus';

export default {
  setup() {
    const dialogVisible = ref(false); // 弹出框是否可见
    const currentNotification = ref({}); // 当前查看的通知
    const notifications = ref([]); // 存储通知数据

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

    // 动态生成表格列配置
    const tableColumns = ref([
      { prop: 'notificationTitle', label: '通知主题', width: '650%' },
      { prop: 'notificationPostingTime', label: '发布时间', width: '200%' },
      { label: '状态', width: '100%', slot: true }, // 状态列包含自定义内容
    ]);

    // 获取通知数据
    const fetchNotifications = async () => {
      try {
        const storedCourseId = localStorage.getItem('courseId');
        const response = await reqNotificationStudent(storedCourseId);
        notifications.value = response.data || mockData; // 使用模拟数据作为后备
      } catch (error) {
        ElNotification({
          type: 'error',
          message: '获取通知失败，使用默认数据。',
        });
        notifications.value = mockData; // 使用模拟数据
      }
    };

    onMounted(() => {
      fetchNotifications();
    });

    // 查看通知详情
    const viewNotification = (notification) => {
      currentNotification = notification;
      dialogVisible = true;
      notification.viewed = true; // 将通知标记为已查看
    };

    // 关闭弹出框
    const closeDialog = () => {
      dialogVisible.value = false;
    };

    return {
      dialogVisible,
      currentNotification,
      notifications,
      tableColumns,
      viewNotification,
      closeDialog,
    };
  },
};
</script>

<style scoped>
.notification-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  margin: 0 auto;
}

.el-table {
  margin-bottom: 20px;
  width: 100%;
}

.el-dialog {
  width: 800px;
}
</style>
