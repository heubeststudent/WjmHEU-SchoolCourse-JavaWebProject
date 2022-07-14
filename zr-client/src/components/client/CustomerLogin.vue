<template>
  <div class="CustomerLogin">
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item prop="userId">
        <el-input v-model="form.customerId" placeholder="输入手机号" clearable></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="输入密码" type="password" clearable></el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button @click="customerLogin" type="success" style="width: 100%;" v-loading="loading">{{title}}</el-button>
    </div>
    <br/>
    <div>
      <el-button @click="toRegister" type="success" style="width: 100%;" v-loading="loading">前往注册</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomerLogin',
  data() {
    return {
      //绑定form对象
      form: {
        customerId: '',
        password: ''
      },
      //加载动画
      loading: false,
      //登录按钮文字
      title: '登录',
      rules: {
        customerId: [{
          required: true,
          message: '请输入手机号',
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
    customerLogin: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.title = "登录中...";
          this.$axios({
            method: 'post',
            url: 'api/client/customerLogin',
            data: this.form
          }).then(res => {
            if (res.data.success) {
              this.$store.commit('token', res.data.msg)
              console.log(res.data.msg)
              this.$router.push('CustomerHome')
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
    toRegister: function () {
      this.$router.push('CustomerRegister')
    }
  }
}
</script>

<style>
.CustomerLogin {
  width: 260px;
  margin: 0 auto;
  margin-top: 20%;
}
</style>
