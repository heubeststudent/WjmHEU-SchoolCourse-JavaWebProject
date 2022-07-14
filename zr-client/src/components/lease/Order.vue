<template>
  <div>
    <el-collapse v-model="activeNames">
      <el-collapse-item title="订单列表" name="1">
        <template slot-scope="scope">
          <div>
            <!-- 查询条件-->
            <el-form :inline="true" :model="queryForm" style="text-align: left;">
              <el-form-item label="订单编号">
                <el-input v-model="queryForm.id" placeholder="订单编号" clearable></el-input>
              </el-form-item>
              <el-form-item label="客户账号">
                <el-input v-model="queryForm.customerId" placeholder="客户账号" clearable></el-input>
              </el-form-item>
              <el-form-item label="客户名称">
                <el-input v-model="queryForm.customerName" placeholder="客户名称" clearable></el-input>
              </el-form-item>
              <el-form-item label="预定车型">
                <el-select v-model="queryForm.carName" placeholder="全部" clearable>
                  <el-option v-for="item in carList" :key="item.id" :label="item.name" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-button type="primary" @click="query">查询</el-button>
            </el-form>
            <!-- 数据列表-->
            <el-table :data="tableData" border stripe style="width: 100%">
              <el-table-column prop="id" label="订单编号" align="center" width="100px"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column prop="customerId" label="客户账号" align="center" width="180px"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column prop="cust.name" label="客户名称" align="center" width="120px">
              </el-table-column>
              <el-table-column prop="car.name" label="预定车型" align="center" width="120px">
              </el-table-column>
              <el-table-column prop="pickCity.name" label="取车城市" align="center" width="120px">
              </el-table-column>
              <el-table-column prop="fromTime" label="取车时间" align="center" width="180px"
              sortable :sort-orders="['ascending', 'descending']">
              </el-table-column>
              <el-table-column prop="returnCity.name" label="还车城市" align="center" width="150px">
              </el-table-column>
              <el-table-column prop="toTime" label="还车时间" align="center" width="180px">
              </el-table-column>
              <el-table-column prop="status" label="状态" align="center" width="120px">
              </el-table-column>
              <el-table-column prop="orderTime" label="订单日期" align="center" width="200px">
              </el-table-column>
              <el-table-column fixed="right" label="操作" align="center">
                <template slot-scope="scope">
                  <el-button type="info" size="medium" @click="openAll(scope.row)" plain >详情</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页-->
            <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
              :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
              layout="total,sizes,prev, pager, next,jumper" :total="total">
            </el-pagination>
            <!--  详情页面 -->
            <el-dialog title="详情" :visible.sync="show" width="500px" style="text-align: left;">
              <el-form :model="form" ref="form" label-width="120px">
                <el-form-item label="订单编号" prop="id">
                  <el-input v-model="form.id" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="客户账号" prop="customerId">
                  <el-input v-model="form.customerId" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" prop="cust.name">
                  <el-input v-model="form.cust.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="预定车型" prop="car.name">
                  <el-input v-model="form.car.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="取车城市" prop="pickCity.name">
                  <el-input v-model="form.pickCity.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="取车门店" prop="pickShop.name">
                  <el-input v-model="form.pickShop.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="取车时间" prop="fromTime">
                  <el-input v-model="form.fromTime" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="还车城市" prop="returnCity.name">
                  <el-input v-model="form.returnCity.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="还车门店" prop="returnShop.name">
                  <el-input v-model="form.returnShop.name" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="还车时间" prop="toTime">
                  <el-input v-model="form.toTime" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="合同名称" prop="agreement">
                  <el-input v-model="form.agreement" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="不计免赔险" prop="nomp">
                  <el-input v-model="form.nomp" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="车上人员责任险" prop="psur">
                  <el-input v-model="form.psur" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="支付金额" prop="total">
                  <el-input v-model="form.total" disabled="disabled"></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button @click="show = false">关闭</el-button>
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
    name: 'Order',
    data() {
      return {
        //下拉菜单默认开启
        activeNames: ['1'],
        //查询对象
        queryForm: {
          id: '',
          customerId: '',
          customerName: '',
          carName: '',
          pageNum: 1,
          pageSize: 10
        },
        //表单数据初始化
        form: {
          //客户对象
          cust: {},
          //租赁车信息
          car: {},
          //取车城市
          pickCity: {},
          //取车门店
          pickShop: {},
          //还车城市
          returnCity: {},
          //还车门店
          returnShop: {},
        },
        //数据总数
        total: 0,
        //数据表格数组
        tableData: [],
        //显示,隐藏详情
        show: false,
        //详情页不可更改
        disabled: false,
        //车型列表初始化
        carList: [],
      }
    },
    methods: {
      //查询方法
      query() {
        this.$axios({
          method: 'post',
          url: 'api/lease/order/list',
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
      //打开详情页面
      openAll(row) {
        //显示详情
        this.show = true;
        //表单数据初始化
        this.form = row;
      },
      queryCarList() {
        this.$axios({
          method: 'post',
          url: 'api/car/car/selectList'
        }).then(res => {
          this.carList = res.data.obj;
        }).catch(err => {
          console.log(err)
        });
      }
    },
    created() {
      //初始化页面进行数据查询
      this.query();
      this.queryCarList();
    }

  }
</script>


<style>
  .el-collapse {
    font-size: 18px;
    font-weight: 700;
    line-height: 50px;
  },
  .title {
    font-size: 18px;
    font-family: Microsoft YaHei;
    font-weight: bold;
    color: #303133;
    padding-bottom: 17px;
    padding-top: 17px;
  }
</style>
