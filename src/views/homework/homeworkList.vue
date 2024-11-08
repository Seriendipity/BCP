<template>
  <div class="Intro">
    <router-link to="/nomeworkInfo" style="text-decoration: none;">
    <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      class="homeworkTable">
      <el-table-column
        prop="name"
        label="作业名称"
        >
      </el-table-column>
      <el-table-column
        prop="starttime"
        label="作业开始时间"
        width="180px">
      </el-table-column>
      <el-table-column
        prop="endtime"
        label="作业结束时间"
        width="180px">
      </el-table-column>
      <el-table-column label="提交人数" width="100px">
        <template v-slot="scope">
          {{ scope.row.submitted }}/{{ scope.row.number }}
        </template>
      </el-table-column>
      <el-table-column
        prop="grade"
        label="成绩"
        width="100px">
      </el-table-column>
      <el-table-column
        label="状态"
        width="100px">
        <template v-slot="scope">
          <el-tag size="small" :type="scope.row.ifsubmit === '已完成' ? 'success' : 'danger'">{{ scope.row.ifsubmit }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" v-slot="scope" width="240px"  >
        <router-link to="/homeworkPreview" style="text-decoration: none;">
        <el-button
          size="mini"
          type="primary"
          @click="handlePush(scope.$index, scope.row)">预览</el-button>
        </router-link>
        <el-button
          size="mini"
          type="danger"
          @click="handleDeletePush(scope.$index, scope.row)" style="margin-left: 10px;">上传</el-button>

      </el-table-column>

      </el-table></router-link>

    
    
  </div>
</template>

<script>
export default {
    data() {
      return {
        tableData: [{
          name: '感知机1',
          starttime: '2024-05-02 10:00:00',
          endtime: '2024-05-02 10:00:00',
          number: 44,
          submitted:3,
          grade:null,
          ifsubmit:'未完成',
        }, {
          name: '感知机2',
          starttime: '2024-05-02-10:00',
          endtime: '2024-05-02-10:00',
          number: 45,
          submitted:45,
          grade:88,
          ifsubmit:'已完成',
        },
        { name: '感知机3',
          starttime: '2024-05-02 10:00:00',
          endtime: '2024-05-02 10:00:00',
          number: 45,
          submitted:44,
          grade:65,
          ifsubmit:'已完成',
        }, {
          name: '感知机4',
          starttime: '2024-05-02-10:00',
          endtime: '2024-05-02-10:00',
          number: 45,
          submitted:45,
          grade:90,
          ifsubmit:'已完成',
        }],
        search: ''
      }
    },
    methods: {
      handleEdit(index, row) {
        this.$router.push({ name: 'editHomework', params: { homework: row } });
      },
      handlePush(index, row) {
        console.log(index, row);
      },
      handleDeletePush(index, row) {
        console.log(index, row);
      }
    }
  }
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 580px;
  border: groove;
  overflow: hidden;
  max-width: 100%; /* 最大宽度100% */

}
.homeworkTable{
  table-layout: auto;
  padding: 10px;
  border-radius: 20px;
}
</style>