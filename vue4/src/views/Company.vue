<template>
<!--  <div>-->
<!--    <div class="main_input">-->
<!--      <el-select style="background-color: crimson" v-model="value1" filterable placeholder="选择项目" @change="loadCompany" >-->
<!--        <el-option-->
<!--            v-for="item in options"-->
<!--            :key="item.subjectId"-->
<!--            :label="item.subjectName"-->
<!--            :value="item.subjectId">-->
<!--        </el-option>-->
<!--      </el-select>-->
<!--      <el-select style="background-color: green" v-model="value" placeholder="请选择">-->
<!--        <el-option-->
<!--            v-for="item in options1"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--        </el-option>-->
<!--      </el-select>-->
<!--    <el-input  v-model="input" style="width: 200px;background-color: yellow" placeholder="请输入内容"></el-input>-->
<!--      <el-button :plain="true" @click="find">搜索</el-button>-->
<!--    </div>-->
<!--  </div>-->



</template>

<script>
import Aside from "@/components/Aside";
import MainTo from "@/components/MainTo";
export default {

  name: "Company",
  components: {Aside},
  data() {
    return {
      value:"",
      input:"",
      total:0,
      pageSize:5,
      pageNum:1,
      tableData: [],
      options1: [{
        value: '域名',
        label: '域名'
      }, {
        value: 'IP',
        label: 'IP'
      }, {
        value: '天眼查',
        label: '天眼查'
      }, {
        value: '文档',
        label: '文档'
      }, {
        value: '代码',
        label: '代码'
      },{
        value: '供应链',
        label: '供应链'
      },{
        value: 'All',
        label: 'All'
      }],
      options:[],
      value1: "",
      value2: [],
      questionForm: {
        companyName: "",
        subjectName: "",
      },
      dialogFormVisible: false,
    }
  },
  // created() {
  //   this.loadSubject()
  //   this.load()
  // },
  methods: {
    find(){
      if (this.getSubjectName()!=null) {
        if (this.getModuleName() === "域名") {
          if (this.input != "") {
            this.$axios({
              method: 'get',
              url: 'http://localhost:9090/domain/insert',
              params: {
                subjectName: this.getSubjectName(),
                domain: this.input
              }
            })
            this.$message("任务已创建，执行中")
          } else {
            this.$message('请输入域名')
          }
        }
        if (this.getModuleName() ==="天眼查"){
          if (this.input!=""){
            this.$axios({
              method: 'get',
              url: 'http://localhost:9090/company/insert',
              params:{
                subjectName:this.getSubjectName(),
                companyName:this.input
              }
            })
            this.$message("任务已创建，执行中")
          }else {
            this.$message('请输入公司名')
          }

        }
      }else {
        this.$message("请选择项目")
      }
    },
    handleCreate() {
      this.questionForm = {
        questionContent: "",
      };
      this.dialogFormVisible = true;
    },
    async createData() {

      /*后端传输数据save */
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/company/add',
        params: {
          companyName: this.questionForm.companyName,
          subjectName: this.getSubjectName()
        }
      })
      this.dialogFormVisible = false
      this.loadCompany()
    },
    loadSubject() {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/subject/all'
      }).then(res => {
        this.options = res.data.data
      })
    },
    loadCompany() {
      // this.$axios({
      //   method: 'get',
      //   url: 'http://localhost:9090/company/all',
      //   params: {
      //     subjectName: this.getSubjectName()
      //   }
      // }).then(res => {
      //   this.options1 = res.data.data
      // })
    },
    //获取下拉框选定的Subjectname的值。
    getSubjectName() {
      var list1 = this.options
      for (var i = 0; i < list1.length; i++) {
        if (list1[i].subjectId === this.value1) {
          return this.questionForm.subjectName = list1[i].subjectName
        }
      }
    },
    getModuleName(){
      var list1 = this.options1
      for (var i = 0; i < list1.length; i++) {
        if (list1[i].value === this.value) {
          return this.value
        }
      }
    },
    getCompanyName() {
      // var list1 = this.options1
      //
      // for (var i = 0; i < list1.length; i++) {
      //
      //   if (list1[i].id === this.value2) {
      //
      //     return this.questionForm.subjectName = list1[i].companyName
      //   }
      // }
    },
    load() {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/company/page',
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.data
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()

    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleDelete(index, row) {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/company/delete',
        params: {
          id: row.id
        }
      })
      this.load()
    },
    toHost(){
      if(this.$route.name!=="host")
      {
        this.$router.push({path:"/host"})
      }
    },
  }
}
</script>

<style scoped>

</style>