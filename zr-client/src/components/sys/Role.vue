<template>
  <div>
    <el-form :inline="true" :model="queryForm" style="text-align: left;">
      <el-form-item label="角色名称">
        <el-input v-model="queryForm.name" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
      </el-form-item>
      <el-button type="primary" @click="openCreate" style="float: right;background-color: darkred;border: darkred;">创建</el-button>


      <!-- 数据列表 -->
      <el-table :data="tableData" :cell-style="{ textAlign: 'center' }" border max-height="600" style="width: 100%;"
        :header-cell-style="{background:'#f7f7f7',color:'#666666',textAlign: 'center'}">
        <el-table-column prop="name" label="角色名称">
        </el-table-column>
        <el-table-column prop="mark" label="角色备注">
        </el-table-column>
        <el-table-column prop="name" label="拥有权限">
          <template slot-scope="scope">
            <el-tag v-for="item in scope.row.auths">{{item}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createId" label="新增人员">
        </el-table-column>
        <el-table-column prop="createTime" label="新增时间">
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="openEdit(scope.row)" style="background-color:darkred;color: #E9EEF3;border: #E9EEF3; width: 60px;">编辑</el-button>
            <el-button type="text" @click="deleteConfirm(scope.row)" style="background-color:darkred;color: #E9EEF3; width: 60px; border: #E9EEF3;"  plain="false">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination style="text-align: center;" :page-sizes="[2, 5, 10, 20, 30]" v-show="total > 0" background
        @size-change="query" @current-change="query" :current-page.sync="queryForm.pageNum"
        :page-size.sync="queryForm.pageSize" layout="total,sizes,prev, pager, next,jumper" :total="total">
      </el-pagination>
    </el-form>


    <el-dialog :title="title1" :visible.sync="show" width="500px">
      <el-form :model="roleData" label-width="120px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleData.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="角色备注" prop="mark">
          <el-input v-model="roleData.mark" clearable></el-input>
        </el-form-item>
        <el-form-item label="拥有权限" prop="customerId">
          <el-tree
            :data="allData"
             ref="tree"
             show-checkbox="true"
             default-expand-all></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitCreate">创建</el-button>
        <el-button @click="show = false">关闭</el-button>
      </span>
    </el-dialog>

    <el-dialog :title="title2" :visible.sync="show1" width="500px">
      <el-form :model="roleData2" label-width="120px">
        <el-form-item label="角色名称" prop="name" clearable >
          <el-input  v-model="roleData2.name" @input="changed" ></el-input>
        </el-form-item>
        <el-form-item label="角色备注" prop="mark">
          <el-input v-model="roleData2.mark"  @input="changed" ></el-input>
        </el-form-item>
        <el-form-item label="拥有权限" prop="customerId">
          <el-tree
            :data="allData"
             ref="tree2"
             show-checkbox="true"
             default-expand-all></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitEdit">更新</el-button>
        <el-button @click="show1 = false">关闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: 'CarWork',
    props: {
      data: {
        type: Array,
        default: () => []
      }
    },
    data() {
      return {
        allData: [],
        queryForm: {
          orderId: '',
          customerId: '',
          pageNum: 1,
          pageSize: 5
        },
        tableData: [],
        orderData: [],
        orderData2: [],
        roleData:[],
        roleData2:[],
        carIn: [],
        carOut: [],
        total: 0,
        title1: '角色新增',
        title2: '角色编辑',
        show: false,
        show1: false,
        optId: 0,
        deal:0,
        dialogVisible: false
      }
    },
    methods: {

      changed(){
        this.$forceUpdate();
      },
      deleteConfirm(row) {
        let id = row.id;
        this.$confirm('确认要更改删除吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          this.$axios({
            method: 'post',
            url: 'api/sys/role/delete',
            params: {
              'id': id
            }
          }).then(res => {
            this.query();
          }).catch(err => {
            console.log(err)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      },
      submitEdit(){
        let nodeList = this.$refs.tree2.getCheckedNodes();
        let idList = "";
        for(let i = 0; i < nodeList.length; i++){
          idList += (nodeList[i].id + " ");
        }
        let name = this.roleData2.name;
        let mark = this.roleData2.mark;
        this.$axios({
          method: 'post',
          url: 'api/sys/role/update',
          params:{
            'id':this.optId,
            'name':name,
            'mark':mark,
            'idList':idList
          }
        }).then(res => {
          if (res.data.success) {
            this.show1 = false;
            this.query();
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });

      },
      openEdit(row){
        this.deal = 1;
        this.optId = row.id;
        this.show1 = true;
        let name = row.name;
        let mark = row.mark;
        this.roleData2.name = name;
        this.roleData2.mark = mark;
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/selectList'
        }).then(res => {
          if (res.data.success) {

            let data = eval(`(${res.data.obj})`);

            // let {data} = res.data.obj;

            let AllData = [...data.parent, ...data.child];
            // console.log(AllData);
            let treeMapList = AllData.reduce((memo, current) => {
              current.label = current.name;
              memo[current["id"]] = current;
              return memo;
            }, {});
            console.log(treeMapList);
            let result = AllData.reduce((arr, current) => {
              let pid = current.pid;
              let parent = treeMapList[pid];
              if (parent) {
                parent.children ? parent.children.push(current) :
                  parent.children = [current];
              } else if (pid == 0) { //根节点
                arr.push(current);
              }
              return arr;
            }, []);
            console.log("result");
            console.log(result);
            this.allData = result;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });

      }
      ,
      submitCreate(){
        let nodeList = this.$refs.tree.getCheckedNodes();
        let idList = "";
        for(let i = 0; i < nodeList.length; i++){
          idList += (nodeList[i].id + " ");
        }
        let name = this.roleData.name;
        let mark = this.roleData.mark;
        this.$axios({
          method: 'post',
          url: 'api/sys/role/create',
          params:{
            'name':name,
            'mark':mark,
            'idList':idList
          }
        }).then(res => {
          if (res.data.success) {
            this.show = false;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });


      },
      openCreate(){

        this.show = true;
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/list'
        }).then(res => {
          if (res.data.success) {

            let data = eval(`(${res.data.obj})`);

            // let {data} = res.data.obj;

            let AllData = [...data.parent, ...data.child];
            // console.log(AllData);
            let treeMapList = AllData.reduce((memo, current) => {
              current.label = current.name;
              memo[current["id"]] = current;
              return memo;
            }, {});
            console.log(treeMapList);
            let result = AllData.reduce((arr, current) => {
              let pid = current.pid;
              let parent = treeMapList[pid];
              if (parent) {
                parent.children ? parent.children.push(current) :
                  parent.children = [current];
              } else if (pid == 0) { //根节点
                arr.push(current);
              }
              return arr;
            }, []);
            console.log("result");
            console.log(result);
            this.allData = result;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      query() {
        this.$axios({
          method: 'post',
          url: 'api/sys/role/list',
          data: this.queryForm
        }).then(res => {
          if (res.data.success) {
            this.tableData = res.data.obj.list;
            // alert(res.data.obj.list[0].status);
            this.total = res.data.obj.total;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      }
    },
    created() {
      //初始化页面时进行数据查询
      this.query();
    }
  }
</script>

<style>
</style>
