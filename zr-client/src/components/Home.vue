<template>
  <div id="module">
    <el-container>
      <el-aside width="auto">
        <div v-show="isCollapse" class="logo"></div>
        <div v-show="!isCollapse" class="title" style="display: inline-block;">汽车租赁平台</div>
        <el-menu default-active="1-1" :default-active="menuIndex" class="el-menu-vertical" :collapse="isCollapse"
          background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span slot="title">系统设置</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" @click="menu('SysUser','1-1','系统管理','用户管理')"><i class="el-icon-user"></i>用户管理
              </el-menu-item>
              <el-menu-item index="1-2" @click="menu('SysRole','1-2','系统管理','角色管理')"><i class="el-icon-s-check"></i>角色管理
              </el-menu-item>
              <el-menu-item index="1-3" @click="menu('SysAuth','1-3','系统管理','权限管理')"><i class="el-icon-share"></i>权限管理
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">门店管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="menu('ShopCity','2-1','门店管理','城市管理')"><i class="el-icon-location"></i>城市管理</el-menu-item>
              <el-menu-item index="2-2" @click="menu('ShopShop','2-2','门店管理','门店管理')"><i class="el-icon-location"></i>门店管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">车辆管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1" @click="menu('CarCar','3-1','车辆管理','车辆管理')"><i class="el-icon-location"></i>车辆管理</el-menu-item>
              <el-menu-item index="3-2" @click="menu('CarWork','3-2','车辆管理','车辆派遣')"><i class="el-icon-location"></i>车辆派遣</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">租赁管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="4-1" @click="menu('LeaseOrder','4-1','订单管理','订单管理')"><i class="el-icon-location"></i>订单管理</el-menu-item>
              <el-menu-item index="4-2" @click="menu('LeaseCustomer','4-2','租赁管理','客户派遣')"><i class="el-icon-location"></i>客户管理</el-menu-item>
              <el-menu-item index="4-3" @click="menu('LeaseAgreement','4-3','租赁管理','合同派遣')"><i class="el-icon-location"></i>合同管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">报表管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="5-1" @click="menu('ReportCar','5-1','报表管理','车型统计')"><i class="el-icon-location"></i>车型统计</el-menu-item>
              <el-menu-item index="5-2" @click="menu('ReportMoney','5-2','报表管理','财务统计')"><i class="el-icon-location"></i>财务统计</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>

      </el-aside>
      <el-container>
        <el-header height="56px" style="background-color: #ededed;">
          <li :class="asideClass" @click="handleAside"></li>
          <el-dropdown @command="handleCommand" style="float: right; margin-right:15px;">
            <i class="el-icon-s-custom" style="font-size: 16px;font-weight: 700;">{{loginUserName}}</i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">设置</el-dropdown-item>
              <el-dropdown-item command="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-avatar :size="50" :src="circleUrl"></el-avatar>
        </el-header>
        <el-header height="56px" style="background-color: #f7f7f7;">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="line-height: 56px;">
            <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-show="breadcrumbTitle != '' ">{{breadcrumbTitle}}</el-breadcrumb-item>
            <el-breadcrumb-item v-show="breadcrumbIndex != '' ">{{breadcrumbIndex}}</el-breadcrumb-item>
          </el-breadcrumb>
        </el-header>
        <el-main style="background-color: white;">
          <main style="height: 100%;">
            <transition name="el-zoom-in-top">
              <router-view></router-view>
            </transition>
          </main>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        isCollapse: false,
        asideClass: 'el-icon-s-fold'
      }
    },
    methods: {
      menu(commentName, menuIndex, breadcrumbTitle, breadcrumbIndex) {
        this.$store.commit('menuIndex', menuIndex);
        this.$store.commit('breadcrumbTitle', breadcrumbTitle);
        this.$store.commit('breadcrumbIndex', breadcrumbIndex);
        this.$router.push(commentName).catch(err => {});
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleAside() {
        if (!this.isCollapse) {
          this.isCollapse = true;
          this.asideClass = 'el-icon-s-unfold';
        } else {
          this.isCollapse = false;
          this.asideClass = 'el-icon-s-fold';
        }
      },
      handleCommand(command) {
        if (command == 'profile') {
          this.menu('SysProfile', 'profile', '个人设置', '头像设置')
        }
        if (command == 'logout') {
          //请求数据到后台，删除redis中的缓存
          this.$axios({
            method: 'post',
            url: 'api/sys/user/logout',
            data: this.form
          }).then(res => {
            if (res.data.success) {
              this.$store.commit('token', '');
              this.$store.commit('loginUserName', '');
              this.$store.commit('menuIndex', '');
              this.$store.commit('breadcrumbTitle', '');
              this.$store.commit('breadcrumbIndex', '');
              this.$store.commit('headImgUrl', '');
              //路由跳转
              this.$router.push('Login')
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          }).catch(err => {
            console.log(err)
          })
        }
      }
    },
    computed: {
      loginUserName() {
        return this.$store.state.loginUserName;
      },
      menuIndex() {
        return this.$store.state.menuIndex;
      },
      breadcrumbTitle() {
        return this.$store.state.breadcrumbTitle;
      },
      breadcrumbIndex() {
        return this.$store.state.breadcrumbIndex;
      },
      circleUrl() {
        return 'data:image/jpeg;base64,' + this.$store.state.headImgUrl;
      }
    },
  }
</script>

<style>
  .el-aside {
    background-color: #545c64;
    color: #333;
    text-align: left;
    height: 99.8%;
    padding: 0px;
    box-shadow: 0 0 5px #545c64;
    overflow: hidden;
  }

  .el-main {
    text-align: center;
  }

  .el-header {
    line-height: 56px;
    border-bottom: 1px solid #E9EEF3;
  }

  #module {
    height: 100%;
  }

  #app,
  html,
  body,
  .el-container {
    height: 100%;
    overflow: hidden;
    margin: 0;
  }

  .el-container {
    direction: vertical;
  }

  .el-menu-vertical:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
    border: 1px;
  }

  .el-submenu__title {
    margin-right: -1px;
  }

  .title {
    vertical-align: middle;
    text-align: center;
    width: 100%;
    height: 56px;
    color: white;
    font-size: 16px;
    font-weight: 700;
    line-height: 56px;
  }

  .logo {
    background-image: url(../assets/zr.jpg);
    height: 56px;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
  }

  .el-avatar {
    float: right;
    margin-right: 10px;
    margin-top: 3px;
  }

  .el-dialog__body {
    padding: 30px 60px;
  }

  .item .el-form-item__label {
    font-weight: 700;
  }
</style>
