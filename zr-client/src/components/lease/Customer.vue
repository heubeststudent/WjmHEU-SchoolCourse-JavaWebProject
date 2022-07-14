<template>
  <div>
    <el-collapse v-model="activeNames">
      <el-collapse-item title="客户列表" name="1">
        <template slot-scope="scope">
          <div>
            <!-- 查询条件-->
            <el-form :inline="true" :model="queryForm" style="text-align: left;">
              <el-form-item label="客户名称">
                <el-input v-model="queryForm.name" placeholder="客户名称" clearable></el-input>
              </el-form-item>
              <el-button type="primary" @click="query">查询</el-button>
              <el-button type="primary" @click="openAdd" plain>新增</el-button>
            </el-form>
            <!-- 数据列表-->
            <el-table :data="tableData" border stripe style="width: 100%">
              <el-table-column prop="customerId" label="客户账号"align="center"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column prop="name" label="客户名称"align="center"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column prop="idcard" label="身份证号"align="center">
              </el-table-column>
              <el-table-column prop="contact" label="紧急联系人"align="center">
              </el-table-column>
              <el-table-column prop="status" label="状态"align="center">
              </el-table-column>
              <el-table-column prop="createId" label="新增人员"align="center">
              </el-table-column>
              <el-table-column prop="createTime" label="新增时间"align="center">
              </el-table-column>
              </el-table-column>
              <el-table-column fixed="right" label="操作" align="center">
                <template slot-scope="scope">
                  <el-button type="primary" size="medium " @click="openEdit(scope.row)">编辑</el-button>
                  <el-popconfirm title="确定删除吗?" @confirm="del(scope.row)">
                    <el-button slot="reference" type="primary" size="medium ">删除</el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页-->
            <el-pagination v-show="total >= 0" background @size-change="query" @current-change="query"
              :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
              layout="total,sizes,prev, pager, next,jumper" :total="total">
            </el-pagination>
            <!-- 新增修改页面 -->
            <el-dialog :title="title" :visible.sync="show" width="500px">
              <el-input type="hidden" :name="dialogType"></el-input>
              <el-form :model="form" :rules="rules" ref="form" label-width="120px">
                <el-form-item label="账号" prop="customerId">
                  <el-input v-model="form.customerId" clearable :disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="name">
                  <el-input v-model="form.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                  <el-input v-model="form.password" type="password" clearable></el-input>
                </el-form-item>
                <el-form-item label="确认" prop="repPassword">
                  <el-input v-model="form.repPassword" type="password" clearable></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idcard">
                  <el-input v-model="form.idcard" clearable></el-input>
                </el-form-item>
                <el-form-item label="紧急联系人" prop="contact">
                  <el-input v-model="form.contact" clearable></el-input>
                </el-form-item>
                <el-form-item label="联系人电话" prop="ctel">
                  <el-input v-model="form.ctel" clearable></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">提交</el-button>
                <el-button @click="show = false">取消</el-button>
              </span>
            </el-dialog>
          </div>
        </template>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
  export default {
    name: 'Customer',
    data() {
      let validatePass = (rule, value, callback) => {
        if (this.form.password != '' && value === undefined) {
          callback(new Error("请再次输入密码"));
        } else if (this.form.password != '' && value != this.form.password) {
          callback(new Error("两次输入密码不一致"));
        } else {
          callback();
        }
      };
      return {
        //下拉菜单默认开启
        activeNames: ['1'],
        //查询对象
        queryForm: {
          name: '',
          pageNum: 1,
          pageSize: 10
        },
        //初始化校验
        form: {},
        //数据总数
        total	: 0,
        //初始化校验对象
        rules: {
          customerId: [{
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }],
          repPassword: [{
            required: true,
            validator: validatePass,
            trigger: 'blur'
          }],
          idcard: [{
            required: true,
            message: '请输入身份证号',
            trigger: 'blur'
          }],
        },
        //对话框标题
        title: '',
        //数据表格数组
        tableData: [],
        //显示,隐藏菜单
        show: false,
        //标识新增还是修改默认新增
        dialogType: 'add',
        //主键是否可以被编辑默认可编辑
        disabled: false
      }
    },
    methods: {
      //查询方法
      query() {
        this.$axios({
          method: 'post',
          url: 'api/lease/customer/list',
          data: this.queryForm
        }).then(res => {
          if (res.data.success) {
            this.tableData = res.data.obj.list;
            this.total = res.data.obj.total;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      //打开添加页
      openAdd() {
        this.form.password ='';
        //清空表单数据
        this.form = {},
        //设置对话框标题
        this.title = '新增',
        //设置表示为新增
        this.dialogType = 'add',
        //主键可被编辑
        this.disabled = false,
        //打开对话框
        this.show = true,
        //设置校验规则为可校验密码
        this.rules.password[0].required = true;
        this.rules.repPassword[0].required = true;
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate()
        })
      },
      //打开编辑页
      openEdit(row) {
        //设置对话框标题
        this.title = '编辑',
        //清空表单
        this.form = {},
        //主键不可被编辑
        this.disabled = true;
        //打开对话框
        this.show = true,
        //设置表示为新增
        this.dialogType = 'edit';
        //设置校验规则为不可校验密码
        this.rules.password[0].required = false;
        this.rules.repPassword[0].required = false;
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate()
        })
        //显示表单数据,对表单复制解决双向绑定注意：select！！!
        this.form = Object.assign({}, row);
        //清空密码
        this.form.password = '';
      },
      //提交数据
      submitForm() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'add') {
              this.$axios({
                method: 'post',
                url: 'api/lease/customer/add',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.log(err)
              });
            } else {
              this.$axios({
                method: 'post',
                url: 'api/lease/customer/edit',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.log(err)
              });
            }
          }
        })
      },
      //删除
      del(row) {
        this.$axios({
          method: 'get',
          url: 'api/lease/customer/del/' + row.customerId,
        }).then(res => {
          //刷新页面
          this.query()
        }).catch(err => {
          console.log(err)
        });
      },
    },
    created() {
      //初始化页面进行数据查询
      this.query();
    }
  }
</script>

<style>
  .el-collapse {
    font-size: 18px;
    font-weight: 700;
    line-height: 50px;
  }
</style>
