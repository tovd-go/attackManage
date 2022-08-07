<template>
  <div class="mt-10">
    <el-select v-model="value1" filterable placeholder="选择项目" @change="loadCompany" >
      <el-option
          v-for="item in options"
          :key="item.subjectId"
          :label="item.subjectName"
          :value="item.subjectId">
      </el-option>
    </el-select>

    <el-select
        v-model="value2"
        filterable
        clearable
        collapse-tags
        style="margin-left: 20px;"
        placeholder="选择目标">
      <el-option
          v-for="item in options1"
          :key="item.id"
          :label="item.companyName"
          :value="item.id">
      </el-option>
    </el-select>
    <el-button class="ml-5" type="primary" @click="handleCreate" >新增目标</el-button>
    <el-dialog :visible.sync="dialogFormVisible">
      <el-form
          :model="questionForm"
          ref="dataForm"
          label-position="left"
          label-width="90px"
          style="width: 400px; margin-left:40px;">

        <el-form-item label="目标名称" prop="correctAnswer">
          <el-input v-model="questionForm.companyName" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createData()">确定</el-button>
      </div>
    </el-dialog>
    <el-input class ="ml-5" style="width: 200px" v-model="input" placeholder="请输入内容"></el-input>
    <el-button class="ml-5" type="danger" @click="exceRun()" >开始探测</el-button>
    <el-button class="ml-5" type="danger" @click="getResult()" >查看结果</el-button>

    <div class="ml-10">
      <el-button class="mt-10" type="danger" @click="toDoamin" >域名</el-button>
      <el-button class="mt-10" type="danger" @click="toHost" >IP</el-button>
      <el-button class="mt-10" type="danger" @click="toCompanyChild" >子公司</el-button>
      <el-button class="mt-10" type="danger" @click="toWord" >文档</el-button>
      <el-button class="mt-10" type="danger" @click="toCode" >代码</el-button>
      <el-button class="mt-10" type="danger" @click="toSupplyChain" >供应链</el-button>
    </div>
  </div>
</template>

<script>

export default {
  name: "MainTo",
  data() {
    return {
      input:"",
      total:0,
      pageSize:5,
      pageNum:1,
      tableData: [],
      options: [],
      options1: [],
      value1: "",
      value2: [],
      questionForm: {
        companyName: "",
        subjectName: "",
      },
      dialogFormVisible: false,
    }
  },
  created() {
    this.loadSubject()
    this.load()
  },
  methods: {

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
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/company/all',
        params: {
          subjectName: this.getSubjectName()
        }
      }).then(res => {
        this.options1 = res.data.data
      })
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
    getCompanyName() {
      var list1 = this.options1

      for (var i = 0; i < list1.length; i++) {

        if (list1[i].id === this.value2) {

          return this.questionForm.subjectName = list1[i].companyName
        }
      }
    },
    exceRun() {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/exec/tyc/do',
        params: {
          companyName: this.getCompanyName()
        }
      })
    },
    getResult(){
      if(this.getCompanyName()!=null){
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/company/get',
          params: {
            companyName: this.getCompanyName()
          }
        }).then(res=>{
          this.tableData=res.data.data
        })
      }else {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/company/all',
          params: {
            subjectName: this.getSubjectName()
          }
        }).then(res=>{
          this.tableData=res.data.data
        })
      }

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
    toDoamin(){
      if (this.$route.name!=="domain")
      {
        this.$router.push({path:"/domain"})
      }
    },
    toCompanyChild(){
      if (this.$route.name!=="companychild")
      {
        this.$router.push({path:"/companychild"})
      }
    },
    toWord(){
      if (this.$route.name!=="word")
      {
        this.$router.push({path:"/word"})
      }
    },
    toCode(){
      if (this.$route.name!=="code")
      {
        this.$router.push({path:"/code"})
      }
    },
    toSupplyChain(){

      if (this.$route.name!=="supplychain")
      {
        this.$router.push({path:"/supplychain"})
      }
    }
  }
}
</script>

<style scoped>

</style>