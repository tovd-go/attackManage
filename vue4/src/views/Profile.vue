<template>
  <div>
<div class="ml-5">
  <el-tag  class="ml-5" type="danger">Fofa:</el-tag>
  <el-tag class="ml-5" type="success">FofaEmail</el-tag>
  <el-input class="ml-5" placeholder="请输入邮箱" style="width: 200px" v-model="inputfofaemail" show-password></el-input>
  <el-tag class="ml-5" type="success">FofaApiKey</el-tag>
  <el-input class="ml-5" placeholder="请输入Key" style="width: 200px" v-model="inputfofaapi" show-password></el-input>
  <el-button class="ml-5" type="waring" @click="savefofa">保存</el-button>
</div>
    <div class="ml-5">

      <el-tag class="ml-5" style="margin-top: 20px" type="danger">securityTrails:</el-tag>
      <el-tag class="ml-5" type="success">securityTrailsApiKey</el-tag>
      <el-input class="ml-5" placeholder="请输入Key" style="width: 200px" v-model="inputsecuritytrailsapiKey" show-password></el-input>
      <el-button class="ml-5" type="waring" @click="savesecurityTrails">保存</el-button>
    </div>

    <div class="ml-5" style="margin-top: 20px">
      <el-tag class="ml-5 " type="success">天眼查Cookie</el-tag>
      <el-input class="ml-5" placeholder="请输入Cookie" style="width: 200px" v-model="inputtyccookie" show-password></el-input>
      <el-button class="ml-5" type="waring" @click="savetyc">保存</el-button>
    </div>
</div>
</template>

<script>
export default {
  name: "Profile",
  data() {
    return {
      input: '',
      inputfofaemail:'',
      inputfofaapi:'',
      inputtyccookie:'',
      inputsecuritytrailsapiKey:''
    }
  },
  methods:{
    savetyc(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/config/tyc',
        params: {
          cookie: this.inputtyccookie,
        }
      })
    },
    savefofa(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/config/fofa',
        params: {
          fofaemail: this.inputfofaemail,
          fofaapi: this.inputfofaapi,
        }
      })
    },
    savesecurityTrails(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/config/securityTrails',
        params: {
          securityTrails: this.inputsecuritytrailsapiKey
        }
      })
    }

  }
}
</script>

<style scoped>

</style>