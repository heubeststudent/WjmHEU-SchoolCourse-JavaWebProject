<template>
  <div>
    <el-collapse v-model="activeNames">
      <a></a>
      <el-collapse-item title="订单列表" name="1">
        <template slot-scope="scope">
          <div>
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
            </el-table>
            <!-- 分页-->
            <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
                           :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
                           layout="total,sizes,prev, pager, next,jumper" :total="total">
            </el-pagination>
            <br>
            <el-button type="primary" @click="toOrder" plain>下单</el-button>

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
        url: 'api/client/listCustomerOrder',
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
    toOrder() {
      this.$router.push('orderCar')
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
