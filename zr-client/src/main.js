// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$axios = axios

//设置全局请求拦截器
axios.interceptors.request.use(
  config => {
    //设置请求头Token
    config.headers.Token = store.state.token
    return config
  }, error => {
    return Promise.reject(error)
  })

//设置全局响应拦截器
axios.interceptors.response.use(
  response => {
    return response
  }, err => {
    if (err.response.status == 401) {
      ElementUI.MessageBox.confirm('账号已过期, 是否跳转到登录页面?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //未登录，跳转到登录页页面
        store.state.token = ''
        router.push("Login")
      }).catch(() => {})
    } else if (err.response.status == 504) {
      ElementUI.Message.error('服务器无响应')
    } else {
      //输出全局异常信息
      ElementUI.Message.error(err.response.data.msg)
    }
    return Promise.reject(err) // 返回接口返回的错误信息
  })

//设置全局权限拦截
router.beforeEach((to, from, next) => {
  if (to.name == null) {
    ElementUI.Message.error('路由不存在!')
  } else if (to.name == 'Login' || to.name == 'Home' || to.name == 'NoAuth' || to.name == 'SysProfile' || to.name == 'CustomerLogin' || to.name == 'CustomerHome' || to.name == 'CustomerRegister') {
    next()
  } else {
    if (store.state.authUrlsList.indexOf(to.name) != -1) {
      next()
    } else {
      next({
        path: '/NoAuth'
      });
    }
  }
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>'
})
