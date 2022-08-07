<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow"></Aside>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">

        <div style="flex: 1; font-size: 20px">

          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>

          <el-select class="ml-5" v-model="value1" filterable placeholder="选择项目">
            <el-option
                v-for="item in options"
                :key="item.subjectId"
                :label="item.subjectName"
                :value="item.subjectId">
            </el-option>
          </el-select>
          <el-select class="ml-5" v-model="value" placeholder="请选择">
            <el-option
                v-for="item in options1"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
          <el-input class="ml-5" v-model="input" style="width: 200px;" placeholder="请输入内容"></el-input>
          <el-button :plain="true" class="ml-5" @click="find">执行</el-button>
        </div>
        <el-dropdown style="width: 70px; cursor: pointer">
          <span>tovd</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
            <el-dropdown-item style="font-size: 14px; padding: 5px 0" @click.native="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";

export default {
  name: 'Home',
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
        value: '邮箱',
        label: '邮箱'
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





      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg'
    }
  },
  created() {
    this.loadSubject()
    // this.load()
  },
  methods: {
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("token")
      this.$message.success("退出成功")
    },
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
            }).then(res=>{
              console.log(res.data)
            })
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
        if (this.getModuleName()==="代码"){
          if (this.input!=""){
            this.$axios({
              method: 'get',
              url: 'http://localhost:9090/git/insert',
              params:{
                subjectName:this.getSubjectName(),
                search:this.input
              }
            })
            this.$message("任务已创建，执行中")
          }else {
            this.$message('关键词')
          }
        }
        if (this.getModuleName()==='邮箱'){
          if (this.input!=""){
            this.$axios({
              method: 'get',
              url: 'http://localhost:9090/email/insert',
              params:{
                subjectName:this.getSubjectName(),
                email:this.input
              }
            }).then(res=>{
              this.$message(res.data.message)
            })
          }else {
            this.$message('关键词')
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
      //this.loadCompany()
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
    // getCompanyName() {
      // var list1 = this.options1
      //
      // for (var i = 0; i < list1.length; i++) {
      //
      //   if (list1[i].id === this.value2) {
      //
      //     return this.questionForm.subjectName = list1[i].companyName
      //   }
      // }
    // },
    // load() {
    //   this.$axios({
    //     method: 'get',
    //     url: 'http://localhost:9090/company/page',
    //     params: {
    //       pageNum: this.pageNum,
    //       pageSize: this.pageSize
    //     }
    //   }).then(res => {
    //     this.tableData = res.data.data
    //     this.total = res.data.total
    //   })
    // },
    // handleSizeChange(pageSize) {
    //   this.pageSize = pageSize
    //   this.load()
    //
    // },
    // handleCurrentChange(pageNum) {
    //   this.pageNum = pageNum
    //   this.load()
    // },
    // handleDelete(index, row) {
    //   this.$axios({
    //     method: 'get',
    //     url: 'http://localhost:9090/company/delete',
    //     params: {
    //       id: row.id
    //     }
    //   })
    //   this.load()
    // },
    // toHost(){
    //   if(this.$route.name!=="host")
    //   {
    //     this.$router.push({path:"/host"})
    //   }
    // },











    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee !important;
}
</style>