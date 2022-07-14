<template>
  <div>
    <el-collapse v-model="activeNames">
      <el-collapse-item title="合同列表" name="1">
        <template slot-scope="scope">
          <div>
            <!-- 查询条件-->
            <el-form :inline="true" :model="queryForm" style="text-align: left;">
              <el-form-item label="合同名称">
                <el-input v-model="queryForm.name" placeholder="合同名称"></el-input>
              </el-form-item>
              <el-button type="primary" @click="query">查询</el-button>
              <el-button type="primary" @click="openAdd" plain>新增</el-button>
            </el-form>
            <!-- 数据列表-->
            <el-table :data="tableData" border stripe style="width: 100%">
              <el-table-column align="center" prop="name" label="合同名称"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column align="center" width="800px" prop="text" label="合同内容"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column align="center" prop="status" label="状态"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column align="center" prop="createId" label="新增人员">
              </el-table-column>
              <el-table-column align="center" prop="createTime" label="新增时间">
              </el-table-column>
              <el-table-column align="center" fixed="right" label="操作">
                <template slot-scope="scope">
                  <el-button type="primary" size="medium" @click="openEdit(scope.row)">编辑</el-button>
                  <el-popconfirm title="确定删除吗?" @confirm="del(scope.row)">
                    <el-button slot="reference" type="primary" size="medium">删除</el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页-->
            <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
              :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
              layout="total,sizes,prev, pager, next,jumper" :total="total">
            </el-pagination>
            <!-- 对话窗口-->
            <el-dialog :title="title" :visible.sync="show" width="500px">
              <el-input type="hidden" :name="dialogType"></el-input>
              <el-form :model="form" :rules="rules" ref="form" label-width="120px" label-position="left">
                <el-form-item label="名称" prop="name">
                  <el-input v-model="form.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="活动形式" prop="text">
                  <el-input type="textarea" v-model="form.text" size="big"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                  <el-select v-model="form.status" placeholder="全部" clearable>
                    <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="id">
                  <el-input type="hidden" v-model="form.id" clearable></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm()">提交</el-button>
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
    name: 'Agreement',
    data() {
      return {
        //下拉菜单默认开启
        activeNames: ['1'],
        //查询对象
        queryForm: {
          name: '',
          pageNum: 1,
          pageSize: 5
        },
        //初始化校验对象
        rules: {
          name: [{
            required: true,
            message: '请输入合同名称',
            trigger: 'blur'
          }],
          text: [{
            required: true,
            message: '请输入合同内容',
            trigger: 'blur'
          }],
          status: [{
            required: true,
            message: '请输入合同状态',
            trigger: 'blur'
          }]
        },
        //数据表格数组
        tableData: [],
        //数据总数
        total: 0,
        //对话框标题
        title: '',
        //显示,隐藏菜单
        show: false,
        //标识新增还是修改默认新增
        dialogType: 'add',
        //主键是否可以被编辑默认可编辑
        disabled: false,
        //表单数据初始化
        form: {},
        //状态列表
        statusList: [{
            value: '有效',
            label: '有效'
          },
          {
            value: '作废',
            label: '作废'
          }
        ]
      }
    },
    methods: {
      //查询
      query() {
        this.$axios({
          method: 'post',
          url: 'api/lease/agreement/list',
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
      //打开添加页面
      openAdd() {
        //设置对话框标题
        this.title = '新增',
          //主键可被编辑
          this.disabled = false;
        //清空表单
        this.form = {};
        //打开对话框
        this.show = true,
          //设置表示为新增
          this.dialogType = 'add',
          //清除表单校验结果
          this.$nextTick(function() {
            this.$refs['form'].clearValidate()
          })
      },
      //打开编辑页面
      openEdit(row) {
        //设置对话框标题
        this.title = '编辑',
          //清空表单
          this.form = {};
        //主键不可被编辑
        this.disabled = true;
        //打开对话框
        this.show = true,
          //设置表示为新增
          this.dialogType = 'edit';
        //显示表单数据,对表单复制解决双向绑定注意：select！！!
        this.form = Object.assign({}, row);
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate()
        })
      },
      //提交数据
      submitForm() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'add') {
              this.$axios({
                method: 'post',
                url: 'api/lease/agreement/add',
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
                url: 'api/lease/agreement/edit',
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
          url: 'api/lease/agreement/del/' + row.name,
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
