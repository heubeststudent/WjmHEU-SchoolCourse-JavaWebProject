<template>
	<div>
		<el-form :inline="true" :model="queryCarForm" style="text-align: left;">
			
			<el-form-item label="车辆名称">
				<el-input v-model="queryCarForm.name" placeholder="车辆名称"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="queryCar">查询</el-button>
				<el-button type="success" @click="openAdd">新增</el-button>
			</el-form-item>
		</el-form>

		<!-- 数据列表 -->
		<el-table :data="carTableData" style="width: 100%" :header-cell-style="{background:'#f7f7f7',color:'#666666'}">

			<el-table-column prop="name" label="名称">
			</el-table-column>

			<el-table-column prop="brand" label="品牌">
			</el-table-column>

			<el-table-column prop="model" label="型号">
			</el-table-column>

			<el-table-column prop="box" label="箱数">
			</el-table-column>

			<el-table-column prop="volume" label="排量">
			</el-table-column>

			<el-table-column prop="door" label="门数">
			</el-table-column>

			<el-table-column prop="seat" label="座数">
			</el-table-column>

			<el-table-column prop="gears" label="挡位">
			</el-table-column>

			<el-table-column prop="auto" label="挡位类型">
			</el-table-column>

			<el-table-column prop="price" label="价格(元/天)">
			</el-table-column>

			<el-table-column prop="status" label="状态">
			</el-table-column>

			<el-table-column prop="createId" label="新增人员">
			</el-table-column>

			<el-table-column prop="createTime" label="新增时间">
			</el-table-column>

			<el-table-column fixed="right" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="openEdit(scope.row)">编辑</el-button>
					<el-button type="text" size="small" @click="openImg()">图片</el-button>
					<el-popconfirm title="确定删除吗?" @confirm="del(scope.row)">
					  <el-button slot="reference" type="text" size="small">删除</el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
		</el-table>
		
		<!-- 分页 -->
		<el-pagination v-show="total > 0" background @size-change="queryCar" @current-change="queryCar"
			:current-page.sync="queryCarForm.pageNum" :page-size.sync="queryCarForm.pageSize"
			layout="total,sizes,prev, pager, next,jumper" :total="total">
		</el-pagination>

		<el-pagination layout="total,sizes,prev, pager, next,jumper" :total="total">

		</el-pagination>

		<!-- 新增/修改页面 -->
		<el-dialog :title="title" :visible.sync="show" width="500px">
			<el-input type="hidden" :name="dialogType"></el-input>
			<el-form :model="form" :rules="rules" ref="form" label-width="60px">

				<el-form-item label="名称" prop="name">
					<el-input v-model="form.name" clearable></el-input>
				</el-form-item>

				<el-form-item label="品牌" prop="brand">
					<el-input v-model="form.brand" clearable></el-input>
				</el-form-item>

				<el-form-item label="型号" prop="model">
					<el-input v-model="form.model" clearable></el-input>
				</el-form-item>

				<el-form-item label="箱数">
					<el-select v-model="form.box" placeholder="请选择箱数">
						<el-option v-for="item in optionsbox" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="排量" prop="volume">
					<el-input v-model="form.volume" clearable></el-input>
				</el-form-item>

				<el-form-item label="门数">
					<el-select v-model="form.door" placeholder="请选择门数">
						<el-option v-for="item in optionsdoor" :key="item.value" :label="item.name" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="座数">
					<el-select v-model="form.seat" placeholder="请选择座数">
						<el-option v-for="item in optionsseat" :key="item.value" :label="item.name" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="挡位" prop="gears">
					<el-input v-model="form.gears" clearable></el-input>
				</el-form-item>

				<el-form-item label="挡位类型">
					<el-select v-model="form.auto" placeholder="请选择类型">
						<el-option v-for="item in optionsauto" :key="item.value" :label="item.name" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="价格(元/天)" prop="price">
					<el-input v-model="form.price" clearable></el-input>
				</el-form-item>

				<el-form-item label="状态">
					<el-select v-model="form.status" placeholder="请选择状态">
						<el-option v-for="item in optionsstatus" :key="item.value" :label="item.name"
							:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">提交</el-button>
				<el-button @click="show = false">取消</el-button>
			</span>
		</el-dialog>
		
		<!-- 选择图像界面 -->
		<el-dialog
		 :title="title"
		  :visible.sync="dialogVisible"
		  width="30%"
		>
		  <span>这是一段信息</span>
		  <span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisible = false">取 消</el-button>
		    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: 'Car',
		data() {
			return {
				//查询对象
				queryCarForm: {
					name: '',
					pageNum: 1,
					pageSize: 10
				},
				//数据表格对象数组
				carTableData: [],
				total: 0,
				title: '',
				show: false,
				form: {},
				rules: {},
				dialogType: 'add',
				optionsbox: [{
					value: '1',
					label: '1'
				}, {
					value: '2',
					label: '2'
				}, {
					value: '3',
					label: '3'
				}],
				optionsdoor: [{
						value: '1',
						label: '1'
					}, {
						value: '2',
						label: '2'
					}, {
						value: '3',
						label: '3'
					},
					{
						value: '4',
						label: '4'
					},
					{
						value: '5',
						label: '5'
					}
				],
				optionsseat: [{
						value: '1',
						label: '1'
					}, {
						value: '2',
						label: '2'
					}, {
						value: '3',
						label: '3'
					},
					{
						value: '4',
						label: '4'
					},
					{
						value: '5',
						label: '5'
					}
				],
				optionsauto: [{
					value: '自动',
					label: '自动'
				}, {
					value: '一体',
					label: '一体'
				}],
				optionsstatus: [{
					value: '正常',
					label: '正常'
				}, {
					value: '下架',
					label: '下架'
				}],
				dialogVisible: false,
			}
		},
		methods: {
			queryCar() {
				console.log(this.queryCarForm);
				this.$axios({
					method: 'post',
					url: 'api/car/car/carList',
					data: this.queryCarForm
				}).then(res => {
					if (res.data.success) {
						this.carTableData = res.data.obj;
						this.total = res.data.obj.total;
					} else {
						this.$message.error(res.data.msg);
					}
				}).catch(err => {
					console.info(err)
				});
			},
			del(row){
				this.$axios({
					method: 'get',
					url: 'api/car/car/del/'+row.id,
				}).then(res => {
					//关闭对话框
					this.show = false;
					//刷新页面
					this.queryCar();
				}).catch(err => {
					console.info(err)
				});
			},
			openAdd() {
				this.title = "新增";
				this.show = true;
				this.dialogType = 'add';
			},
			openEdit(row){
				 this.title = "编辑";
				 this.show = true;
				 this.form = {},
				 this.dialogType = 'edit';
				 this.form = Object.assign({}, row);
			},
			openImg(){
				this.title = "编辑图片";
				this.show = true;
			},
			submitForm() {
				if (this.dialogType == 'add') {
					this.$axios({
						method: 'post',
						url: 'api/car/Car/add',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryCar()
					}).catch(err => {
						console.info(err)
					});
				} else {
					this.$axios({
						method: 'post',
						url: 'api/car/Car/edit',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryCar()
					}).catch(err => {
						console.info(err)
					});
				}

			}
		},
		created() {
			this.queryCar();
		}
	}
</script>

<style>
</style>
