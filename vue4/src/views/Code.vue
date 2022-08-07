<template>
  <div>
    <el-button class="mr-5 " type="info">新增网站</el-button>
    <el-select class="mr-5 " v-model="valueSubjectName" filterable placeholder="选择项目" >
      <el-option
          v-for="item in options"
          :key="item.subjectId"
          :label="item.subjectName"
          :value="item.subjectId">
      </el-option>
    </el-select>
    <el-input class="mr-5" v-model="inputGit" style="width: 200px" placeholder="公司名称"></el-input>
    <el-button class="mr-5" type="waring" @click="load" >搜索</el-button>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="代码仓库" name="代码仓库">
      <el-collapse v-model="activeNames" :data="tableData" @change="handleChange">
        <el-collapse-item v-for="(item,i) in tableData" :key="i">
          <template slot="title">
            {{item.gitFileName}}
          </template>
          <div>
            <el-tag  class="ml-5" type="danger">Owner:</el-tag>
            {{item.gitOwner}}
          </div>
          <div>
            <el-tag  class="ml-5" type="danger">UpdateTime:</el-tag>
            {{item.gitFileTime}}
          </div>
          <div>
            <el-tag  class="ml-5" type="danger">Desc:</el-tag>
            {{item.gitDesc}}
          </div>
          <div>
            <el-tag  class="ml-5" type="danger">Path:</el-tag>
            {{item.gitPath}}
          </div>

          <div>

          </div>
        </el-collapse-item>
      </el-collapse>
    </el-tab-pane>
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
  </el-tabs>
  </div>
</template>

<script>
export default {
  name: "Code",

  data() {
    return {
      inputGit:"",
      valueSubjectName:"",
      options:[],
      total:0,
      pageSize:10,
      pageNum:1,
      tableData:[],
      activeName: '代码仓库',
      activeNames: ['1']
    };
  },
  created() {
    this.load()
    this.loadSubject()
  },
  methods: {
    load(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/git/find',
        params:{
          subjectName:this.getSubjectName(),
          gitFileName:this.inputGit,
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then(res => {
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
    handleClick(tab, event) {
    },
    handleChange(val) {
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
     this.load()

    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
     this.load()
    }
  }
}
</script>

<style scoped>

</style>