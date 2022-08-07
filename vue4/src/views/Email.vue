<template>
  <div>
    <el-select class="mr-5 " v-model="subjectName" filterable placeholder="选择项目" >
      <el-option
          v-for="item in options"
          :key="item.subjectId"
          :label="item.subjectName"
          :value="item.subjectId">
      </el-option>
    </el-select>
    <el-input class="mr-5" v-model="inputEmail" style="width: 200px" placeholder="邮箱"></el-input>
    <el-input class="mr-5" v-model="inputFrom" style="width: 200px" placeholder="数据源"></el-input>
    <el-button class="mr-5" type="waring" @click="EmailFind" >搜索</el-button>
    <el-button class="mr-5" type="info" @click="EmailDel" >删除</el-button>
    <el-button class="mr-5" type="danger" @click="EmailExport" >导出</el-button>
    <el-table
        class="mt-10"
        border
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          prop="emailTime"
          label="更新日期"
          width="300">
      </el-table-column>
      <el-table-column
          prop="subjectName"
          label="项目名称"
          width="100">
      </el-table-column>
      <el-table-column
          prop="emailName"
          label="邮箱后缀"
          width="200">
      </el-table-column>
      <el-table-column
          prop="emailAll"
          label="邮箱"
          width="200">
      </el-table-column>
      <el-table-column
          prop="emailFrom"
          label="数据源">
      </el-table-column>
      <!--    <el-table-column label="操作" width="100">-->

      <!--      <template slot-scope="scope">-->
      <!--        &lt;!&ndash;              <el-button&ndash;&gt;-->
      <!--        &lt;!&ndash;                  size="mini"&ndash;&gt;-->
      <!--        &lt;!&ndash;                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>&ndash;&gt;-->
      <!--        <el-button-->
      <!--            size="mini"-->
      <!--            type="danger"-->
      <!--            @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
      <!--      </template>-->
      <!--    </el-table-column>-->
    </el-table>
    <div class="block">
      <span class="demonstration"></span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 15, 30, 45]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "Email",

  data(){
    return{
      tableData:[],
      total:0,
      pageSize:15,
      pageNum:1,
      options:[],
      subjectName:"",
      inputFrom: "",
      inputEmail:""
    }

  },

  created() {
    this.load()
    this.loadSubject()
  },
  methods:{
    load(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/email/load',
        params: {
          subjectName:this.getSubjectName(),
          emailName:this.inputEmail,
          emailFrom:this.inputFrom,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res=>{
        this.total=res.data.total
        this.tableData = res.data.data
      })
    },

    loadSubject() {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/subject/all'
      }).then(res => {
        this.options = res.data.data
      })
    },
    getSubjectName() {
      var list1 = this.options
      for (var i = 0; i < list1.length; i++) {
        if (list1[i].subjectId === this.subjectName) {
          return list1[i].subjectName
        }
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleDelete(index, row){

    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
    },
    EmailFind(){
      this.load()
    },
    EmailDel(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/email/del',
        params: {
          subjectName:this.getSubjectName(),
          emailName:this.inputEmail,
          emailFrom:this.inputFrom,
        }
      }).then(res=>{
        if (res.data.code=="200"){
          this.$message("删除成功")
        }else if(res.data.code=="201"){
          this.$message("选择条件")
        }
      })
    },
    EmailExport(){
      this.$axios({
        method: 'get',
        responseType: 'blob',
        url: 'http://localhost:9090/email/export',
        params: {
          subjectName:this.getSubjectName()
        }
      }).then((res)=>{
        if (res.request.status === 200) {
          const link = document.createElement('a')
          const blob = new Blob([res.data], {
            type: 'application/vnd.ms-excel'
          })
          link.href = URL.createObjectURL(blob)
          const fileName ='file.xlsx'
          link.download = fileName
          link.click()
        }
      })
    }
  }
}
</script>

<style scoped>

</style>