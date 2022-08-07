<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="域名信息" name="first">
        <el-button class="mr-5 " type="info" @click="toDoamin" >新增网站</el-button>

        <el-select class="mr-5 " v-model="valueSubjectName" filterable placeholder="选择项目" >
          <el-option
              v-for="item in options"
              :key="item.subjectId"
              :label="item.subjectName"
              :value="item.subjectId">
          </el-option>
        </el-select>
        <el-input class="mr-5" v-model="inputUrl" style="width: 200px" placeholder="Url"></el-input>
        <el-input class="mr-5" v-model="inputCode" style="width: 200px" placeholder="状态码"></el-input>
        <el-input class="mr-5" v-model="inputFrom" style="width: 200px" placeholder="数据源"></el-input>
        <el-button class="mr-5" type="waring" @click="domainfind" >搜索</el-button>
        <el-button class="mr-5" type="info" @click="domainDel" >删除</el-button>
        <el-button class="mr-5" type="danger" @click="exportDomain" >导出</el-button>
        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
<!--          <el-table-column-->
<!--              type="selection"-->
<!--              width="55">-->
<!--          </el-table-column>-->
          <el-table-column
              prop="domainTime"
              label="更新日期"
              width="150">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="domainName"
              label="域名"
              width="200">
          </el-table-column>
          <el-table-column
              prop="domainHost"
              label="IP"
              width="200">
          </el-table-column>
          <el-table-column
              prop="domainCode"
              label="状态码"
              width="100">
          </el-table-column>
          <el-table-column
              prop="domainTitle"
              label="标题"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column
              prop="domainCdn"
              label="Cdn"
              width="50"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column
              prop="domainFrom"
              label="数据源"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column
              prop="domainServer"
              label="Server"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column
              prop="domainFinger"
              label="指纹"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column label="操作" width="100">

            <template slot-scope="scope">
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
              <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <span class="demonstration"></span>
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[2, 5, 10, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="目录探测" name="second">


        <el-button class="mr-5 " type="info" @click="NewDir" >新增网站</el-button>
        <el-select class="mr-5 " v-model="valueSubjectName" filterable placeholder="选择项目" >
          <el-option
              v-for="item in options"
              :key="item.subjectId"
              :label="item.subjectName"
              :value="item.subjectId">
          </el-option>
        </el-select>
        <el-input class="mr-5" v-model="inputUrlDir" style="width: 200px" placeholder="Url"></el-input>
        <el-input class="mr-5" v-model="inputOwner" style="width: 200px" placeholder="Owner"></el-input>
        <el-input class="mr-5" v-model="inputCodeDir" style="width: 200px" placeholder="Code"></el-input>
        <el-button class="mr-5" type="waring" @click="loadDir" >搜索</el-button>
        <el-button class="mr-5" type="info" @click="delDir" >删除</el-button>
        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableDataDir"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column
              prop="dirTime"
              label="更新日期"
              width="200">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="dirUrl"
              label="Url"
              width="400">
          </el-table-column>
          <el-table-column
              prop="dirPath"
              label="Path"
              width="200">
          </el-table-column>
          <el-table-column
              prop="dirCode"
              label="状态码">
          </el-table-column>
<!--          <el-table-column label="操作">-->

<!--&lt;!&ndash;            <template slot-scope="scope">&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;              <el-button&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;                  size="mini"&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;              <el-button&ndash;&gt;-->
<!--&lt;!&ndash;                  size="mini"&ndash;&gt;-->
<!--&lt;!&ndash;                  type="danger"&ndash;&gt;-->
<!--&lt;!&ndash;                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;            </template>&ndash;&gt;-->
<!--          </el-table-column>-->
        </el-table>
        <div class="block">
          <span class="demonstration"></span>
          <el-pagination
              @size-change="handleSizeChangeDir"
              @current-change="handleCurrentChangeDir"
              :current-page="pageNumDir"
              :page-sizes="[2, 5, 10, 20]"
              :page-size="pageSizeDir"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalDir">
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Email" name="third">
        <Email></Email>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :visible.sync="dialogDir">
      <el-form
          :model="dirtionForm"
          ref="dataForm"
          label-position="left"
          label-width="90px"
          style="width: 400px; margin-left:40px;">

        <el-form-item label="项目名称">
          <el-tag>{{dirtionForm.subjectName}}</el-tag>
        </el-form-item>
        <el-form-item label="目录探测">
          <el-input class="mr-5" v-model="inputDir" style="width: 200px" placeholder="Url"></el-input>
          <el-button @click="DirSeacrh"> 探测</el-button>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDir = false">取消</el-button>
        <el-button type="primary" @click="creteDataDir()">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisible">
      <el-form
          :model="questionForm"
          ref="dataForm"
          label-position="left"
          label-width="90px"
          style="width: 400px; margin-left:40px;">

        <el-form-item label="项目名称">
          <el-tag>{{}}</el-tag>
        </el-form-item>
        <el-form-item label="目录探测">
          <el-tag>{{questionForm.domainName}}</el-tag>
          <el-button size="mini" style="margin-left: 20px" @click="DirSeacrh">开始</el-button>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createData()">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisible1">
      <el-form
          :model="dirtionForm"
          ref="dataForm"
          label-position="left"
          label-width="90px"
          style="width: 400px; margin-left:40px;">
        <el-form-item label="项目名称">
          <el-tag>{{dirtionForm.subjectName}}</el-tag>
        </el-form-item>
        <el-form-item label="批量搜索">
          <el-input
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="inputDomain">
          </el-input>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取消</el-button>
        <el-button type="primary" @click="createData1()">确定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import Dir from "@/components/Dir";
import Email from "@/views/Email"
export default {
  name: "Doamin",
  components:{Dir,Email},
  data(){
    return{
      activeName:"first",
      valueSubjectName:"",
      questionForm: {
        subjectName: "",
        domainHost: "",
        domainName: "",
      },
      dirtionForm:{
        subjectName:"",
        domainName:""
      },
      dialogFormVisible: false,
      dialogFormVisible1:false,
      dialogDir:false,
      options:[],
      inputDomain:"",
      inputFrom:"",
      inputCode:"",
      inputUrl:"",
      inputDir:"",
      inputOwner:"",
      inputPath:"",
      inputUrlDir:"",
      inputCodeDir:"",
      total:0,
      pageSize:10,
      pageNum:1,
      totalDir:0,
      pageNumDir:1,
      pageSizeDir:10,
      tableData:[],
      tableDataDir:[],
      resdata:[]
    }
  },

  created() {
    this.load()
    this.loadDir()
    this.loadSubject()
  },
  methods:{
    loadDir(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/dir/find',
        params: {
          subjectName:this.getSubjectName(),
          dirOwner:this.inputOwner,
          dirUrl:this.inputUrlDir,
          dirCode:this.inputCodeDir,
          pageNum: this.pageNumDir,
          pageSize: this.pageSizeDir
        }
      }).then(res=>{
        this.totalDir=res.data.totalDir
        this.tableDataDir = res.data.data
      })
    },
    delDir(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/dir/del',
        params: {
          subjectName:this.getSubjectName(),
          dirOwner:this.inputOwner,
          dirUrl:this.inputUrlDir,
          dirCode:this.inputCodeDir,
        }
      }).then(res=>{
        if (res.data.code=="200"){
          this.$message("删除成功")
        }else if(res.data.code=="201"){
          this.$message("选择条件")
        }
      })
    },
    domainDel(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/domain/del',
        params: {
          subjectName:this.getSubjectName(),
          domainName:this.inputUrl,
          domainCode:this.inputCode,
          domainFrom:this.inputFrom
        }
      }).then(res=>{
        if (res.data.code=="200"){
          this.$message("删除成功")
        }else if(res.data.code=="201"){
          this.$message("选择条件")
        }
      })
    },
    exportDomain(){
      this.$axios({
        method :'get',
        responseType: 'blob',
        url: 'http://localhost:9090/domain/export',
        params: {
          subjectName: this.getSubjectName()
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
    },
    DirSeacrh(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/dir/insert',
        params: {
          url:this.inputDir,
          subjectName:this.getSubjectName(),
        }
      })
    },
    DirFind(){

    },
    PortScan(){

    },
    /*域名探测*/
    domainfind(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/domain/find',
        params: {
          subjectName:this.getSubjectName(),
          domainName:this.inputUrl,
          domainCode:this.inputCode,
          domainFrom:this.inputFrom,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res=>{
        this.total=res.data.total
        this.tableData = res.data.data
      })
    },
    handleClick(tab, event) {
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()

    },
    handleSizeChangeDir(pageSize) {
      this.pageSizeDir = pageSize
      this.loadDir()

    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleCurrentChangeDir(pageNum) {
      this.pageNumDir = pageNum
      this.loadDir()
    },
    load(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/domain/find',
        params: {
          subjectName:this.getSubjectName(),
          domainName:this.inputUrl,
          domainCode:this.domainCode,
          domainFrom:this.domainFrom,
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
        if (list1[i].subjectId === this.valueSubjectName) {
          return list1[i].subjectName
        }
      }
    },

    domainInsert(){
      if (this.inputDomain != "") {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/domain/insert',
          params: {
            subjectName: this.getSubjectName(),
            domain: this.inputDomain
          }
        }).then(res=>{
          this.resdata=res.data
          var temp=""
          for (var i in res.data){
            if (res.data[i]=="200"){
              temp+=i
              temp+=","
            }
          }
          this.$message('已存在：'+temp+"\n"+"其他执行中")
        })
      } else {
        this.$message('请输入域名')
      }
    },

    //dialog
    toDoamin(){
      this.dirtionForm.subjectName=this.getSubjectName()
      this.handleCreate1()
    },
    handleCreate() {
      this.dialogFormVisible = true;
    },
    handleCreate1() {
      this.dialogFormVisible1 = true;
    },
    async createData() {
      this.dialogFormVisible = false
      this.load()
    },
    async createData1(){
      this.dialogFormVisible1 = false
      this.domainInsert()

    },
    async creteDataDir(){
      this.dialogDir = false
    },
    handleCreateDir() {
      this.dialogDir=true
    },


    NewDir(){

      this.dirtionForm.subjectName=this.getSubjectName()
      this.handleCreateDir()
    },
    handleEdit(index, row) {
      this.questionForm.domainHost= row.domainHost
      this.questionForm.domainName=row.domainName
      this.questionForm.subjectName=row.subjectName
      this.handleCreate()

    },
    handleDelete(index, row) {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/domain/delete',
        params: {
          id: row.id
        }
      })
      this.load()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }

  }
}
</script>

<style scoped>

</style>