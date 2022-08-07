<template>

  <div>
    <el-dialog :visible.sync="dialogFormVisible">
      <el-form
          :model="demo"
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
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="creteDataDir()">确定</el-button>
      </div>
    </el-dialog>
  </div>


</template>

<script>
export default {
  name: "Window",
  data(){
    return{
      dialogFormVisible:false,
    }
  },
  methods:{
    async creteDataDir(){
      this.dialogDir = false
    },
    handleCreateDir() {
      this.dialogDir=true
    },
  }
}
</script>

<style scoped>

</style>