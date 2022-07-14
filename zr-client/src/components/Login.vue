<template>
  <div class="login">
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item prop="userId">
        <el-input v-model="form.userId" placeholder="输入账号" clearable></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="输入密码" type="password" clearable></el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button @click="login" type="success" style="width: 100%;" v-loading="loading">{{title}}</el-button>
    </div>
    <br/>
    <div>
      <el-button @click="toClient" type="success" style="width: 100%;" v-loading="loading">前往客户端登陆</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        //绑定form对象
        form: {
          userId: '',
          password: ''
        },
        //加载动画
        loading: false,
        //登录按钮文字
        title: '登录',
        rules: {
          userId: [{
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      login: function() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.title = "登录中...";
            this.$axios({
              method: 'post',
              url: 'api/sys/user/login',
              data: this.form
            }).then(res => {
              if (res.data.success) {
                this.$store.commit('token', res.data.msg)
                this.$store.commit('loginUserName', res.data.obj.loginUserName)
                this.$store.commit('authUrlsList', res.data.obj.authUrlsList)
                this.$store.commit('headImgUrl', res.data.obj.base64)
                this.$router.push('Home')
              } else {
                this.title = "登录";
                this.$message.error(res.data.msg);
              }
              this.loading = false;
            }).catch(err => {
              this.loading = false;
              this.title = "登录";
              console.info(err)
            });
          }
        });
      },
      toClient: function () {
        this.$router.push('CustomerLogin')
      }
    }
  }
</script>

<style>
  .login {
    width: 260px;
    margin: 0 auto;
    margin-top: 20%;
  }
</style>
