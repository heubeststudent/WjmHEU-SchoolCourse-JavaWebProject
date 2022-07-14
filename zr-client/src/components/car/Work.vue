<template>
  <div>
    <el-form :inline="true" style="text-align: left;">

      <el-form-item label="订单编号">
        <el-input v-model="queryCustomerForm.orderId" placeholder="订单编号"></el-input>
      </el-form-item>
      <el-form-item label="客户账号">
        <el-input v-model="queryCustomerForm.customerId" placeholder="客户账号"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="queryCustomer()">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="customerTableData" style="width: 100%" :header-cell-style="{background:'#f7f7f7',color:'#666666'}">

      <el-table-column prop="orderId" label="订单编号">
      </el-table-column>

      <el-table-column prop="order.customerId" label="客户账号">
      </el-table-column>

      <el-table-column prop="order.customer.name" label="客户名称">
      </el-table-column>

      <el-table-column prop="carNo" label="车牌号码">
      </el-table-column>

      <el-table-column prop="outTime" label="出车时间">
      </el-table-column>

      <el-table-column prop="inTime" label="回车时间">
      </el-table-column>

      <el-table-column prop="status" label="状态">
      </el-table-column>

      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openAll(scope.row)">详情</el-button>
          <el-button type="text" size="small">出车</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页-->
    <el-pagination v-show="total > 0" background @size-change="queryCustomer" @current-change="queryCustomer"
      :current-page.sync="queryCustomerForm.pageNum" :page-size.sync="queryCustomerForm.pageSize"
      layout="total,sizes,prev, pager, next,jumper" :total="total">
    </el-pagination>
    <!--  详情页面 -->
    <el-dialog title="详情" :visible.sync="show" width="500px" style="text-align: left;">
      <el-form :model="form" ref="form" label-width="120px">
        <el-form-item label="订单编号" prop="id">
          <el-input v-model="form.id" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="客户账号" prop="customer_Id">
          <el-input v-model="form.customer_Id" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="预定车型" prop="car">
          <el-input v-model="form.car" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="出车门店" prop="pickShop.name">
          <el-input v-model="form.pickShop.name" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="出车时间" prop="outTime">
          <el-input v-model="form.outTime" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="出车人员" prop="outUserId">
          <el-input v-model="form.outUserId" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回车门店" prop="returnShop.name">
          <el-input v-model="form.returnShop.name" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回车时间" prop="inTime">
          <el-input v-model="form.inTime" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回车人员" prop="inUserId">
          <el-input v-model="form.inUserId" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="整备时间" prop="outFitTime">
          <el-input v-model="form.outFitTime" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="差价" prop="total">
          <el-input v-model="form.total" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input v-model="form.status" disabled="disabled"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
  export default {
    name: 'Work',
    data() {
      return {
        //查询对象
        queryCustomerForm: {
          orderId: '',
          pageNum: 1,
          pageSize: 5,
          customerId: '',
        },
        //显示,隐藏详情
        show: false,
        //表单数据初始化
        form: {
          //取车门店
          pickShop: {},
          //还车门店
          returnShop: {},
        },
        //数据表格对象数组
        customerTableData: [],
        total: 0,
      }
    },
    methods: {
      queryCustomer() {
        console.log(this.queryCustomerForm.customerId)
        this.$axios({
          method: 'post',
          url: 'api/car/work/workList',
          data: this.queryCustomerForm
        }).then(res => {
          if (res.data.success) {
            this.customerTableData = res.data.obj;
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
        this.$axios({
          method: 'post',
          url: 'api/car/work/selectwork',
          data: this.queryCustomerForm
        }).then(res => {
          if (res.data.success) {
            console.log(res)
            this.form = res.data.obj[row.orderId-1]
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
    },
    created() {
      this.queryCustomer();
    }
  }
</script>

<style>
</style>
