<template>
	<xn-form-container
		:title="formData.id ? '编辑数据源' : '增加数据源'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-alert
				message="注：数据源新增后名称、类型、驱动类型皆不可更改"
				type="warning"
				v-if="!formData.id"
				class="mb-4"
			/>
			<a-row :gutter="16">
				<a-col :span="12" v-if="!formData.id">
					<a-form-item label="名称：" name="poolName">
						<a-input v-model:value="formData.poolName" placeholder="请输入数据源名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12" v-if="!formData.id">
					<a-form-item label="数据源类型：" name="category">
						<a-select
							v-model:value="formData.category"
							:options="categoryOptions"
							style="width: 100%"
							placeholder="请选择数据库类型"
							:disabled="formData.id !== undefined"
						/>
					</a-form-item>
				</a-col>
				<a-col :span="12" v-if="!formData.id">
					<a-form-item label="数据库驱动类型：" name="driverName">
						<a-select
							v-model:value="formData.driverName"
							:options="dataBaseTypeOptions"
							style="width: 100%"
							placeholder="请选择数据库驱动类型"
							:disabled="formData.id !== undefined"
						/>
					</a-form-item>
				</a-col>
				<a-col :span="24">
					<a-form-item label="连接URL：" name="url">
						<a-textarea
							v-model:value="formData.url"
							placeholder="请输入数据源连接URL"
							allow-clear
							:auto-size="{ minRows: 3, maxRows: 6 }"
						/>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="用户名：" name="username">
						<a-input v-model:value="formData.username" placeholder="请输入数据源用户名" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="密码：" name="password">
						<a-input v-model:value="formData.password" placeholder="请输入数据源密码" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="排序码：" name="sortCode">
						<a-input-number
							v-model:value="formData.sortCode"
							style="width: 100%"
							placeholder="请输入排序码"
							:max="1000"
						/>
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="dbsForm">
	import { required } from '@/utils/formRules'
	import dbsApi from '@/api/dbs/dbsApi'
	import tool from '@/utils/tool'
	// 定义emit事件
	const emit = defineEmits({ successful: null })
	// 默认是关闭状态
	let visible = $ref(false)
	const formRef = ref()
	// 表单数据，也就是默认给一些数据
	const formData = ref({})
	const submitLoading = ref(false)
	// 搜索框下拉字典数据
	const categoryOptions = tool.dictList('DBS_CATEGORY')
	const dataBaseTypeOptions = tool.dictList('DATABASE_DRIVE_TYPE')
	// 打开抽屉
	const onOpen = (record) => {
		visible = true
		formData.value = {
			sortCode: 99
		}
		if (record) {
			formData.value = Object.assign({}, record)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		visible = false
	}
	// 默认要校验的
	const formRules = {
		poolName: [required('请输入数据源名称')],
		driverName: [required('请选择数据库类型')],
		url: [required('请输入连接URL')],
		username: [required('请输入数据源用户名')],
		password: [required('请输入数据源密码')],
		category: [required('请选择数据源分类')],
		sortCode: [required('请选择排序')]
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value
			.validate()
			.then(() => {
				submitLoading.value = true
				dbsApi.submitForm(formData.value, !formData.value.id).then(() => {
					visible = false
					emit('successful')
				})
				.finally(() => {
					submitLoading.value = false
				})
			})
			.catch(() => {})
	}
	// 调用这个函数将子组件的一些数据和方法暴露出去
	defineExpose({
		onOpen
	})
</script>
