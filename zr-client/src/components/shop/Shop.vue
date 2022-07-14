<template>
  <div>
    <!--查询条件-->
    <el-form :inline="true" :model="queryFrom" style="text-align: left;">
      <el-form-item label="门店名称">
        <el-input v-model="queryFrom.name" placeholder="门店名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
        <el-button type="primary" :plain='true' @click="openAdd">新增</el-button>
      </el-form-item>
      <!--城市数据列表-->
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="cityName.provinceName.name" label="省份名称">
        </el-table-column>
        <el-table-column prop="cityName.name" label="城市名称">
        </el-table-column>
        <el-table-column prop="name" label="门店名称">
        </el-table-column>
        <el-table-column prop="address" label="门店地址">
        </el-table-column>
        <el-table-column prop="tel" label="电话">
        </el-table-column>
        <el-table-column prop="admin" label="负责人">
        </el-table-column>
        <el-table-column prop="status" label="状态">
        </el-table-column>
        <el-table-column prop="createId" label="新增人员">
        </el-table-column>
        <el-table-column prop="createTime" label="新增时间">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row)">
              <el-button slot="reference" size="small" type="danger">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <!--分页-->
    <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
      :current-page.sync="queryFrom.pageNum" :page-size.sync="queryFrom.pageSize"
      layout="total,sizes,prev, pager, next,jumper" :total="total">
    </el-pagination>
    <!--新增/修改页面-->
    <el-dialog :title="title" :visible.sync="show" width="30%">
      <el-input type="hidden" :name="dialogType"></el-input>
      <el-form :model="form" :rules="rules" ref="form" label-width="70px">
        <el-form-item label="省份">
          <el-select v-model="form.provinceId" filterable placeholder="请选择" @change="changeCity">
            <el-option v-for="item in provinceList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="城市">
          <el-select v-model="form.cityId" filterable placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in cityList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="门店" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="admin">
          <el-input v-model="form.admin"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in options" :key="item.status" :label="item.label" :value="item.status">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="show = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'Shop',
    data() {
      //查询条件
      return {
        queryFrom: {
          name: '',
          pageNum: 1,
          pageSize: 10
        },
        provinceId: '',
        cityId: '',
        //门店数据表格数组
        tableData: [],
        //一共多少条数据
        total: 0,
        //对话框标题
        title: '',
        //显示/隐藏对话框
        show: false,
        //初始化表单对象
        form: {},
        //初始化校验对象
        rules: {
          name: [{
            required: true,
            message: '请输入城市名称',
            trigger: 'blur'
          }],
          address: [{
            required: true,
            message: '请输入地址',
            trigger: 'blur'
          }],
          tel: [{
            required: true,
            message: '请输入电话',
            trigger: 'blur'
          }],
          admin: [{
            required: true,
            message: '请输入负责人姓名',
            trigger: 'blur'
          }]
        },
        //所有省份名称
        provinceList: [],
        //省份对应城市名称
        cityList: [],
        //标识新增or修改
        dialogType: 'add',
        //店面状态
        options: [{
          status: '在营',
          label: '在营'
        }, {
          status: '关闭',
          label: '关闭'
        }],
        status: ''
      }
    },
    methods: {
      //查询数据
      query() {
        this.$axios({
          method: 'post',
          url: 'api/shop/shop/list',
          data: this.queryFrom
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
      openAdd() {
        //设置对话框标题
        this.title = "新增";
        //清空表单
        this.form = {};
        //打开对话框
        this.show = true;
        //标识为新增
        this.dialogType = 'add';
        //获取所有省份数组
        this.queryProvinceList();
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate();
        });
      },
      openEdit(row) {
        //设置对话框标题
        this.title = "编辑";
        //清空表单
        this.form = {};
        //打开对话框
        this.show = true;
        //标识为新增
        this.dialogType = 'edit';
        //获取所有省份数组
        this.queryProvinceList();
        //对列表行对象进行复制，解决双向绑定问题
        this.form = Object.assign({}, row);
        //处理省份
        //this.form.province = [];
        //for (let i = 0; i < row.provinceList.length; i++) {
        //  let province = row.provinceList[i];
        //  this.form.province.push(province.id);
        //}
      },
      submitForm() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'add') {
              this.$axios({
                method: 'post',
                url: 'api/shop/shop/add',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.info(err)
              });
            } else {
              this.$axios({
                method: 'post',
                url: 'api/shop/shop/edit',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.info(err)
              });
            }
          }
        });
      },
      del(row) {
        this.$axios({
          method: 'get',
          url: 'api/shop/shop/del/' + row.id,
        }).then(res => {
          //刷新页面
          this.query()
        }).catch(err => {
          console.info(err)
        });
      },
      //查询所有省份名称
      queryProvinceList() {
        this.$axios({
          method: 'post',
          url: 'api/shop/province/proselectlist'
        }).then(res => {
          this.provinceList = res.data.obj;
        }).catch(err => {
          console.info(err)
        });
      },
      changeCity(value) {
        this.cityList = [],
          this.form.cityId = ''
        if (value) {
          let provinceId = this.provinceList.find(item => item.id === value).id
          this.$axios({
            method: 'get',
            url: 'api/shop/city/cityselectlist/' + provinceId
          }).then(res => {
            this.cityList = res.data.obj;
          }).catch(err => {
            console.info(err)
          });
        }
      }
      //     queryCityList(String) {
      //       this.$axios({
      //        method: 'get',
      //        url: 'api/shop/city/cityselectlist/' + provinceId
      //     }).then(res => {
      //        this.cityList = res.data.obj;
      //      }).catch(err => {
      //        console.info(err)
      //      });
      //    }
    },
    created() {
      //初始化页面时进行城市数据查询
      this.query();
    }
  }
</script>

<style>
  .el-select {
    width: 100%;
  }
</style>
