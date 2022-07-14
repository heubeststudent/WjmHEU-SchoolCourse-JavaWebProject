import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import NoAuth from '@/components/NoAuth'
import SysUser from '@/components/sys/User'
import SysProfile from '@/components/sys/Profile'
import SysRole from '@/components/sys/Role'
import SysAuth from '@/components/sys/auth'
import ShopCity from '@/components/shop/City'
import ShopShop from '@/components/shop/Shop'
import CarCar from '@/components/car/Car'
import CarWork from '@/components/car/Work'
import LeaseOrder from '@/components/lease/Order'
import LeaseCustomer from '@/components/lease/Customer'
import LeaseAgreement from '@/components/lease/Agreement'
import ReportCar from '@/components/report/ReportCar'
import ReportMoney from '@/components/report/ReportMoney'
import CustomerLogin from "@/components/client/CustomerLogin"
import CustomerHome from "@/components/client/CustomerHome";

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      redirect: 'Login'
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/CustomerLogin',
      name: 'CustomerLogin',
      component: CustomerLogin
    },
    {
      path: '/CustomerHome',
      name: 'CustomerHome',
      component: CustomerHome
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home,
      children: [{
          path: '/NoAuth',
          name: 'NoAuth',
          component: NoAuth
        },
        {
          path: '/SysUser',
          name: 'SysUser',
          component: SysUser
        },
        {
          path: '/SysRole',
          name: 'SysRole',
          component: SysRole
        },
        {
          path: '/SysAuth',
          name: 'SysAuth',
          component: SysAuth
        },
        {
          path: '/SysProfile',
          name: 'SysProfile',
          component: SysProfile
        },
        {
          path: '/ShopCity',
          name: 'ShopCity',
          component: ShopCity
        },
        {
          path: '/ShopShop',
          name: 'ShopShop',
          component: ShopShop
        },
        {
          path: '/CarCar',
          name: 'CarCar',
          component: CarCar
        },
        {
          path: '/CarWork',
          name: 'CarWork',
          component: CarWork
        },
        {
          path: '/LeaseOrder',
          name: 'LeaseOrder',
          component: LeaseOrder
        },
        {
          path: '/LeaseCustomer',
          name: 'LeaseCustomer',
          component: LeaseCustomer
        },
        {
          path: '/LeaseAgreement',
          name: 'LeaseAgreement',
          component: LeaseAgreement
        },
        {
          path: '/ReportCar',
          name: 'ReportCar',
          component: ReportCar
        },
        {
          path: '/ReportMoney',
          name: 'ReportMoney',
          component: ReportMoney
        },
      ]
    },
  ]
})
