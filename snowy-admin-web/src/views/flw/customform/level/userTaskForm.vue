<template>
	<div style="text-align: center; margin-bottom: 10px"><h2>审批单</h2></div>
	<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
		<a-form-item label="名称：" name="name">
			<a-input v-model:value="formData.name" :disabled="props.disabled" placeholder="请输入名称" allow-clear />
		</a-form-item>
		<a-form-item label="分类：" name="category">
			<a-input v-model:value="formData.category" :disabled="props.disabled" placeholder="请输入分类" allow-clear />
		</a-form-item>
		<a-form-item label="排序：" name="sortCode">
			<a-input-number
				v-model:value="formData.sortCode"
				:disabled="props.disabled"
				placeholder="请输入排序"
				:max="1000"
				style="width: 100%"
			/>
		</a-form-item>
	</a-form>
</template>

<script setup name="levelUserTaskForm">
	import { required } from '@/utils/formRules'

	const formRef = ref()
	const formData = ref({})
	const formRules = {
		name: [required('请输入名称')],
		category: [required('请输入分类')],
		sortCode: [required('请输入排序')]
	}
	// 接受参数
	const props = defineProps({
		value: {
			type: Object,
			default: () => {}
		},
		disabled: {
			type: Boolean,
			default: () => false
		}
	})
	// 监听
	watch(
		() => props.value,
		(newVal, oldVal) => {
			if (newVal) {
				formData.value = newVal
			}
		},
		{ deep: true, immediate: true }
	)
	// 数据提交，此方法提供给工作流界面使用
	const getData = () => {
		return new Promise((resolve, reject) => {
			try {
				formRef.value.validate().then((value) => {
					submitApi(value).then((data) => {
						resolve(value)
					})
				})
			} catch (error) {
				reject(error)
			}
		})
	}
	// 重置
	const resetForm = () => {
		formRef.value.resetFields()
	}
	// 自定义数据去除
	const submitApi = (value) => {
		return new Promise((resolve, reject) => {
			// 这里调用自己的api接口，完了成功后记得resolve()
			const result = {
				code: 200,
				msg: '操作成功',
				data: ''
			}
			// 实际情况下这里会被统一网络请求给过滤，能到这肯定是能行的
			resolve(result.data)
		})
		// 如果想吧这个数据存进自己的业务表，那么这里提交就行了
	}
	defineExpose({
		getData,
		resetForm
	})
</script>
