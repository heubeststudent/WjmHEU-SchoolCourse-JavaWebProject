
<template>
<el-container>
      <el-collapse style="width: 100%;" v-model="activeNames">
      <el-collapse-item title="收入折线图" name="1">
        <el-row>
          <el-col :span="24">
        <div class="block" style="text-align:left;">
          <el-date-picker
            v-model="financedatetimerangevalue"
            type="daterange"
            :picker-options="pickerOptions"
            @change="dateChange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right">
          </el-date-picker>
          <el-button type="warning" @click="getFinanceEchartData()">统计</el-button>
        </div>
        </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div ref="chart" style="margin-top:30px;width: 90%;height: 450px;"></div>
          </el-col>
        </el-row>
      </el-collapse-item>
    </el-collapse>

</el-container>
</template>

<script>
  import * as dayjs from 'dayjs'
  export default {
    name: 'ReportMoney',
    data() {
        return {
          activeNames:['1'],
          startDate: '',
          endDate: '',
          financedatetimerangevalue: '',
          pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
          },
        };
    },
    mounted() {
      this.getFinanceEchartData()
    },
    methods: {
      getFinanceEchartData() {
        let url= '/api/report/selectFinanceList';
        let params={
          'startDate': this.startDate,
          'endDate': this.endDate,
        };
        this.$axios.post(url,params).then((res)=>{
          let categoryData = []
          let lineData = []
          let total = 0
          if(res.data.success && res.data.obj.length > 0){
              for (let index = 0; index < res.data.obj.length; index++) {
                categoryData.push(res.data.obj[index].name);
                lineData.push(res.data.obj[index].value);
                total += parseInt(res.data.obj[index].value)
              }
          } else {
            const start = new Date();
            start.setTime(start.getTime() + 3600 * 1000 * 24);
            for(let i=0;i < 5;i++) {
                categoryData.push(dayjs(start.setTime(start.getTime() + 3600 * 1000 * 24 * i)).format('YYYY-MM-DD'))
                lineData.push(0)
            }
          }
          const chart = this.$refs.chart
          if (chart) {
            const myChart = this.$echarts.init(chart)
            var option = {
              title: {
                text: '总收入' + total,
              },
              legend: {
                data: ['金额'],
                top: '5%',
                bottom:0,
                left:'center',
                textStyle:{
                  color:'#303133',
                },
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                  type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter: function (params) {
                  console.log('aaaa', params)
                  return "日期："+params[0].axisValueLabel + '<br>' + "金额：" +params[0].data;
                }
              },
              xAxis: {
                type: 'category',
                data: categoryData
              },
              yAxis: {
                type: 'value'
              },
              series: [
                  {
                    name: '金额',
                    data: lineData,
                    type: 'line',
                    itemStyle:{					//---图形形状
                        color:'#bb0a30',
                    },
                  }
              ]
            };
            myChart.setOption(option)
            window.addEventListener("resize", function() {
              myChart.resize()
            })
          }
        }).catch((err)=>{
          if(err.code==403){
            this.$message.error({message:'请求财务统计数据时出现异常。'+err.code+":"+err.msg,duration:5000 });
          }
        })
      },
      dateChange (val) {
        if (val) {
          this.startDate = dayjs(val[0]).format('YYYY-MM-DD');
          this.endDate = dayjs(val[1]).format('YYYY-MM-DD');
        } else {
          this.startDate = '';
          this.endDate = '';
        }
      },

    },
    watch: {},
    created() {
    }
  }
</script>



<style scoped>
.el-button--warning,.el-button--warning:focus,.el-button--warning.is-active, .el-button--warning:active{background: #bb0a30;}
.el-button--warning:hover {
    background: #bb0a30;
    border-color: #bb0a30;
    color: #FFF;
}

</style>
