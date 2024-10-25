<template>
  <div class="Intro">
    <el-table class = "stutable"
      :data="tableData"
      height = 100%
      width = 90%>
      <el-table-column
        prop="classNo"
        label="课程号"
        width=150px>
      </el-table-column>
      <el-table-column
        prop="className"
        label="课程名称"
        width=150px>
      </el-table-column>
      <el-table-column
        prop="studentNo"
        label="学号"
        width=150px>
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="姓名"
        width=150px>
      </el-table-column>
      <el-table-column
        prop="grade"
        label="成绩"
        >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
    data() {
      return {
        tableData: [{
          studentName: '王五',
          grade: 75,
          classNo: 'C002',
          index:2,
          studentNo:'S003',
          className:'信息技术'
        }, {
          studentName: '张三',
          grade: 60,
          classNo: 'C001',
          index:1,
          studentNo:'S001',
          className:'计算机科学'
        }]
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
}
.stutable{
  border-radius: 20px;
  padding: 10px;
  
}
</style>