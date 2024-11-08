<template>
  <div class="Intro">
    <el-button type="primary" round style="padding: 17px;font-size: large;font-weight: bold;float: right;margin-right: 40px;margin-top: 15px;">新建作业</el-button>
    <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      class="homeworkTable">
      <el-table-column label="是否发布" v-slot="scope">
        <el-switch
          v-model="scope.row.value"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-table-column>
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
      <el-table-column label="批改" v-slot="scope" width="100px" >
        <el-button
          size="mini"
          :type="scope.row.check === '已完成' ? 'success' : 'danger'"
          @click="handleDelete(scope.$index, scope.row)" >
          {{ scope.row.check }}
        </el-button>
      </el-table-column>
      <el-table-column label="基本信息" v-slot="scope" width="150px">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
      </el-table-column>
      <!-- <el-table-column label="操作" v-slot="scope" width="240px"  >
        <el-button
          size="mini"
          type="primary"
          @click="handlePush(scope.$index, scope.row)">发布</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDeletePush(scope.$index, scope.row)">取消发布</el-button>

      </el-table-column> -->

      </el-table>

    
    
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
          number: 45,
          submitted:3,
          check:'未完成',
          value:false,
        }, {
          name: '感知机2',
          starttime: '2024-05-02-10:00',
          endtime: '2024-05-02-10:00',
          number: 45,
          submitted:45,
          value:true,
          check:'未完成',
        },
        { name: '感知机3',
          starttime: '2024-05-02 10:00:00',
          endtime: '2024-05-02 10:00:00',
          number: 45,
          submitted:44,
          check:'已完成',
          value:true,
        }, {
          name: '感知机4',
          starttime: '2024-05-02-10:00',
          endtime: '2024-05-02-10:00',
          number: 45,
          submitted:45,
          check:'已完成',
          value: false,
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