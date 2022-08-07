<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
  <el-tab-pane label="目录探测" name="second">
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


    <el-button class="mr-5 " type="info" @click="NewDir" >新增网站</el-button>

    <el-select class="mr-5 " v-model="valueSubjectName" filterable placeholder="选择项目" >
      <el-option
          v-for="item in options"
          :key="item.subjectId"
          :label="item.subjectName"
          :value="item.subjectId">
      </el-option>
    </el-select>
    <el-input class="mr-5" v-model="inputUrl" style="width: 200px" placeholder="Url"></el-input>
    <el-button class="mr-5" type="waring" @click="DirFind" >搜索</el-button>
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
          prop="domainTime"
          label="更新日期"
          width="200">
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
          prop="domainCode"
          label="状态码"
          width="100">
      </el-table-column>
      <el-table-column label="操作">

        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "Dir",
  data() {
    return{
      dirtionForm:{
        subjectName:"",
        domainName:"",
      },
      tableDataDir:[],
      inputDir:"",
      inputUrl:"",
      dialogDir:false,
      valueSubjectName:"",
      options:[],
    }
  },
  methods:{
    async creteDataDir(){
      this.dialogDir = false
    },
    handleCreateDir() {
      this.dialogDir=true
    },
    DirSeacrh(){

    },
    NewDir(){

      this.dirtionForm.subjectName=this.getSubjectName()
      this.handleCreateDir()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize


    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
    },
    DirFind(){

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }

  }
}
</script>

<style scoped>

</style>