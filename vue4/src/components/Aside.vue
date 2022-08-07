<template>
  <el-menu router :default-active="this.$router.path" :default-openeds="['1', '3']"
           style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse">
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: white" v-show="logoTextShow">攻击面管理平台</b>
    </div>
    <el-menu-item index="/subject">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">项目管理</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">资产管理</span>
      </template>
<!--      <el-menu-item-group>-->
<!--        <el-menu-item index="/company">公司资产</el-menu-item>-->
<!--      </el-menu-item-group>-->
      <el-menu-item-group>
        <el-menu-item index="/domain">web资产</el-menu-item>
      </el-menu-item-group>
<!--      <el-menu-item-group>-->
<!--        <el-menu-item index="/host">IP</el-menu-item>-->
<!--      </el-menu-item-group>-->
      <el-menu-item-group>
        <el-menu-item index="/companychild">公司资产</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/code">代码资产</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/word">文档</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/supplychain">供应链</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="/profile">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">配置管理</span>
      </template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style scoped>

</style>