<template>
  <div>
    <el-button @click="modifyAuth" style="background-color:darkred;color: #E9EEF3; width: 90px; float: left;"
      size="small">修改</el-button>
    <el-button @click="deleteConfirm" style="float: left;background-color: aqua; width: 90px;" size="small">删除
    </el-button>
    <el-button @click="createAuth" style="float: right;background-color: aqua; width: 90px;" size="medium">新建
    </el-button>
    <br><br>
    <el-tree :data="allData" ref="tree" show-checkbox="true" default-expand-all>
    </el-tree>

    <el-dialog :title="title1" :visible.sync="show" width="500px">
      <el-form :model="orderData" label-width="120px">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="orderData.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="图标样式" prop="imgStyle">
          <el-input v-model="orderData.imgStyle" clearable readonly></el-input>
        </el-form-item>
        <el-form-item label="访问地址" prop="url">
          <el-input v-model="orderData.url" clearable readonly></el-input>
        </el-form-item>
        <el-form-item label="排序序号" prop="seq">
          <el-input v-model="orderData.seq" clearable clearable oninput="value=value.replace(/[^0-9]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="权限备注" prop="mark">
          <el-input v-model="orderData.mark" clearable></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="parentMunu">
          <el-input v-model="orderData.parentMunu" clearable readonly></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitModify">保存</el-button>
        <el-button @click="show = false">关闭</el-button>
      </span>
    </el-dialog>


    <el-dialog :title="title2" :visible.sync="show2" width="500px">
      <el-form :model="create" label-width="120px">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="create.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="图标样式" prop="imgStyle">
          <el-input v-model="create.imgStyle" clearable></el-input>
        </el-form-item>
        <el-form-item label="访问地址" prop="url">
          <el-input v-model="create.url" clearable></el-input>
        </el-form-item>
        <el-form-item label="排序序号" prop="seq">
          <el-input v-model="create.seq" clearable clearable oninput="value=value.replace(/[^0-9]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="权限备注" prop="mark">
          <el-input v-model="create.mark" clearable></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="parentMunu">
          <el-autocomplete v-model="state"  :fetch-suggestions="querySearchAsync" placeholder="请输入内容"></el-autocomplete>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitCreate">保存</el-button>
        <el-button @click="show2 = false">关闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {

    props: {
      data: {
        type: Array,
        default: () => []
      }
    },
    data() {
      return {
        allData: [],
        title1: '权限修改',
        title2: '新建权限',
        show: false,
        show2: false,
        orderData: [],
        create: [],
        restaurants: [
          {"value":"系统设置", "id":1},
          {"value":"车辆管理", "id":5},
          {"value":"租赁管理", "id":14},
          {"value":"门店管理", "id":18},
          {"value":"报表管理", "id":21},
        ],
        state: '',
        timeout:  null,
        dataa:{
          "":'0',
          "系统设置":'1',
          "车辆管理":'5',
          "租赁管理":'14',
          "门店管理":'18',
          "报表管理":'21'
        }

      };
    },
    methods: {
      submitCreate(){
        let name =  this.create.name;
        let url =  this.create.url;
        let seq =  (this.create.seq);
        let mark =  this.create.mark;
        let parent = this.dataa[this.state];

        this.$axios({
          method: 'post',
          url: 'api/sys/auth/create',
          params: {
            'name':name,
            'url':url,
            'seq':seq,
            'mark':mark,
            'parent':parent
          }
        }).then(res => {
          this.show2 = false;
          this.query();
        }).catch(err => {
          console.log(err)
        })

      },

      querySearchAsync(queryString, cb) {
        var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      },
      createAuth() {
        this.show2 = true;
      },
      deleteConfirm() {
        this.$confirm('确认要更改删除吗?-(请谨慎操作)', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let id = this.$refs.tree.getCheckedNodes()[0].id;
          this.$axios({
            method: 'post',
            url: 'api/sys/auth/delete',
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
      submitModify() {
        let id = this.$refs.tree.getCheckedNodes()[0].id;
        let name = this.orderData.name;
        let mark = this.orderData.mark;
        let seq = this.orderData.seq;
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/update',
          params: {
            'id': id,
            'name': name,
            'mark': mark,
            'seq': seq
          }
        }).then(res => {
          if (res.data.success) {
            this.show = false;
            this.query();
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });

      },
      modifyAuth() {
        // this.$refs.tree.getCheckedNodes()[0].id;
        let named = this.$refs.tree.getCheckedNodes()[0].name;
        let id = this.$refs.tree.getCheckedNodes()[0].id;
        this.show = true;
        this.orderData.name = named;
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/selectById',
          params: {
            'id': id
          }
        }).then(res => {
          if (res.data.success) {
            this.orderData = res.data.obj;
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
      querySearchAsync(queryString, cb) {
        var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      }
    },
    created() {
      this.query();
    },
    mounted() {
      this.restaurants = this.loadAll();
    }
  };
</script>

<style>
</style>
