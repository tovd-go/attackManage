<template>


  <div>
<!--    <el-button class="mr-5 " type="info">新增网站</el-button>-->
    <el-select class="mr-5 " v-model="valueSubjectName" filterable placeholder="选择项目" >
      <el-option
          v-for="item in options"
          :key="item.subjectId"
          :label="item.subjectName"
          :value="item.subjectId">
      </el-option>
    </el-select>
    <el-input class="mr-5" v-model="inputCompanyName" style="width: 200px" placeholder="公司名称"></el-input>
    <el-button class="mr-5" type="waring" @click="load" >搜索</el-button>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="域名IP" name="first">

        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              prop="companyTime"
              label="更新日期"
              width="200">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyName"
              label="公司名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyDomain"
              label="域名">
          </el-table-column>
          <el-table-column
              prop="companyHost"
              label="IP">
          </el-table-column>
<!--          <el-table-column width="150" label="操作">-->
<!--            <template slot-scope="scope">-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  type="danger"-->
<!--                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>




      </el-tab-pane>
      <el-tab-pane label="投资公司" name="second">
        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              prop="companyTime"
              label="更新日期"
              width="200">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyName"
              label="公司名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyChildName"
              label="子公司">
          </el-table-column>
<!--          <el-table-column width="150" label="操作">-->
<!--            <template slot-scope="scope">-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  type="danger"-->
<!--                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="微信公众号" name="third">
        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              prop="companyTime"
              label="更新日期"
              width="200">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyName"
              label="公司名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyWechatName"
              label="微信公众号">
          </el-table-column>
          <el-table-column
              prop="companyWechatId"
              label="微信ID">
          </el-table-column>
<!--          <el-table-column width="150" label="操作">-->
<!--            <template slot-scope="scope">-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  type="danger"-->
<!--                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-tab-pane>


      <el-tab-pane label="微博，供应商" name="fourth">

        <el-table
            class="mt-10"
            border
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              prop="companyTime"
              label="更新日期"
              width="200">
          </el-table-column>
          <el-table-column
              prop="subjectName"
              label="项目名称"
              width="100">
          </el-table-column>
          <el-table-column
              prop="companyName"
              label="公司名称"
              width="100">
          </el-table-column>
        <el-table-column
            prop="companyWeibo"
            label="微博">
        </el-table-column>
        <el-table-column
            prop="companySupply"
            label="供应商">
        </el-table-column>
<!--          <el-table-column width="150" label="操作">-->
<!--            <template slot-scope="scope">-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
<!--              <el-button-->
<!--                  size="mini"-->
<!--                  type="danger"-->
<!--                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-tab-pane>
    </el-tabs>
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
  </div>
</template>

<script>
import MainTo from "@/components/MainTo";
export default {
  components: {MainTo},
  name: "CompanyChild",

  created() {
    this.load()
    this.loadSubject()
  },
  data(){
    return{
      activeName: 'first',
      pageSize:10,
      pageNum:1,
      total:0,
      tableData:[],
      options:[],
      valueSubjectName:"",
      inputCompanyName:""
    }
  },
  methods:{
    load(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/company/find',
        params: {
          subjectName:this.getSubjectName(),
          companyName:this.inputCompanyName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res=>{
        this.total=res.data.total
        this.tableData = res.data.data
      })

    },
    todomain(){
      if (this.$route.name!=="domain")
      {
        this.$router.push({path:"/domain"})
      }
    },
    handleClick(tab, event) {
      this.load()
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
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()

    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {

    },
    handleDelete(index, row){

    }

  }

}
</script>

<style scoped>

</style>