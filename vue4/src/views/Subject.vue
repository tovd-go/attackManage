<template>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="项目名称" suffix-icon="el-icon-search" v-model="subjectName"></el-input>
    <el-select class="ml-5" v-model="subjectCode" clearable placeholder="请选择">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-button class="ml-5" type="primary" @click="find" >搜索</el-button>
    <el-button type="warning">重置</el-button>
    <el-button class="ml-5" type="success" @click="handleCreate">创建项目</el-button>
    <el-dialog :visible.sync="dialogFormVisible">
      <el-form
          :model="questionForm"
          ref="dataForm"
          label-position="left"
          label-width="90px"
          style="width: 400px; margin-left:40px;">

        <el-form-item label="项目名称" prop="correctAnswer">
          <el-input v-model="questionForm.subjectName" />
        </el-form-item>
        <el-form-item label="项目状态" prop="correctAnswer">
          <el-input v-model="questionForm.subjectCode" />
        </el-form-item>
        <el-form-item label="标签" prop="correctAnswer">
          <el-input v-model="questionForm.subjectLabel" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createData()">确定</el-button>
      </div>
    </el-dialog>
<!--    <el-button class="ml-5" type="danger" @click="deleteSubject">删除项目</el-button>-->

    <el-table
        class="mt-10"
        border
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="subjectTime"
          label="创建日期"
          width="200">
      </el-table-column>
      <el-table-column
          prop="subjectName"
          label="项目名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="subjectCode"
          label="项目状态"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="subjectLabel"
          label="标签"
          show-overflow-tooltip>
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
          <el-button class="mr-5" type="danger" @click="exportSubject(scope.$index, scope.row)" >导出</el-button>
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
  </div>
</template>

<script>
export default {
  name: "Subject",
  subjectName: "",
  subjectCode: "",

  data() {
    return {
      input: "",
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      questionForm: {
        subjectName: "",
        subjectCode: "",
        subjectLabel: "",
      },
      dialogFormVisible: false,
      total:0,
      pageSize:5,
      pageNum:1,
      subjectName: '',
      tableData: [],
      multipleSelection: [],
      options: [{
        value: 'doing',
        label: '进行中'
      }, {
        value: 'done',
        label: '已完成'
      }],
      subjectCode: ''
    }
  },
  created() {
    this.load()
  },
  methods: {
    exportSubject(index,row){
      this.$axios({
        method :'get',
        responseType: 'blob',
        url: 'http://localhost:9090/subject/export',
        params: {
          subjectName: row.subjectName
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
    deleteSubject(){

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
        url: 'http://localhost:9090/subject/save',
        params: {
          subjectName: this.questionForm.subjectName,
          subjectCode: this.questionForm.subjectCode,
          subjectLabel: this.questionForm.subjectLabel
        }
      })
      this.dialogFormVisible = false
      this.load()
    },
    load() {
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/subject/page',
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.data
        this.total = res.data.total
      })
    },
    find() {
      if (this.subjectCode != '' && this.subjectName != '') {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/subject',
          params: {
            subjectName: this.subjectName,
            subjectCode: this.subjectCode
          }
        }).then(res => {
          this.tableData = res.data.name
        })
      } else if (this.subjectName != '') {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/subject',
          params: {
            subjectName: this.subjectName,
          }
        }).then(res => {

          this.tableData = res.data.name
        })
      } else {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/subject',
          params: {
            subjectCode: this.subjectCode,
          }
        }).then(res => {

          this.tableData = res.data.name
        })
      }
    },/*搜索功能*/

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

    /*删除 编辑*/
    handleEdit(index, row) {
      this.handleCreate()
      this.questionForm.subjectName=row.subjectName
      this.questionForm.subjectCode=row.subjectCode
      this.questionForm.subjectLabel=row.subjectLabel
    },
    async handleDelete(index, row) {
      const confirmResult = await this.$confirm('此操作将永久删除关联此项目的数据，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      /*如果用户确认打印confirm,如果用户取消显示cancel*/
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除!')
      }
      if (confirmResult=='confirm') {
        this.$axios({
          method: 'get',
          url: 'http://localhost:9090/subject/delete',
          params: {
            subjectId: row.subjectId
          }
        })
      }
      this.load()
    },
  }
}
</script>

<style scoped>

</style>