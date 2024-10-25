<template>
  <div class="notification-container">
    <el-table :data="notifications" style="width: 100%;" border>
      <!-- 通知主题列 -->
      <el-table-column prop="title" label="通知主题" width="55%"></el-table-column>

      <!-- 通知发布时间列 -->
      <el-table-column prop="timestamp" label="发布时间" width="25%"></el-table-column>

      <!-- 通知状态列 -->
      <el-table-column label="状态" width="10%">
        <template #default="{ row }">
          <el-tag :type="row.viewed ? 'success' : 'warning'">
            {{ row.viewed ? '已查看' : '未查看' }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作" width="10%">
        <template #default="{ row }">
          <el-button type="primary" @click="viewNotification(row)" size="small">查看通知</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 查看通知的弹出框 -->
    <el-dialog :visible="dialogVisible" title="查看通知" width="600px" @close="closeDialog">
      <p><strong>主题:</strong> {{ currentNotification.title }}</p>
      <p><strong>内容:</strong> {{ currentNotification.message }}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { reqNotifications } from '@/api/api'; // 确保有这个API接口
import { ElNotification } from 'element-plus';

export default {
  setup() {
    const dialogVisible = ref(false); // 弹出框是否可见
    const currentNotification = ref({}); // 当前查看的通知
    const notifications = ref([]); // 存储通知数据

    // 获取通知数据
    const fetchNotifications = async () => {
      try {
        const response = await reqNotifications(); // 获取通知数据
        notifications.value = response.data || []; // 更新通知数据
      } catch (error) {
        ElNotification({
          type: 'error',
          message: '获取通知失败',
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
      notification.viewed = true; // 将通知标记为已查看
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
  width: 100%; /* 设置表格宽度为100% */
}

.el-dialog {
  width: 800px;
}
</style>
