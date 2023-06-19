<template>
	<a-modal
		v-model:visible="visible"
		title="表单人员选择"
		:mask-closable="false"
		:destroy-on-close="true"
		:width="600"
		@ok="handleOk"
		@cancel="handleCancel"
	>
		<div class="form-user-table">
			<a-table
				ref="table"
				:columns="columns"
				:data-source="dataSource"
				:row-key="(record) => record.model"
				:expand-row-by-click="true"
				:row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange, type: 'radio' }"
				:pagination="false"
				size="small"
				bordered
			/>
		</div>
	</a-modal>
</template>

<script setup name="formUserSelector">
	import workFlowUtils from '@/components/XnWorkflow/nodes/utils/index'
	const visible = ref(false)

	const columns = [
		{
			title: '字段名',
			dataIndex: 'label'
		},
		{
			title: '字段',
			dataIndex: 'model'
		},
		{
			title: '类型',
			dataIndex: 'type'
		}
	]

	// 定义emit事件
	const emit = defineEmits({ click: null })
	const props = defineProps(['formFieldList'])

	const dataSource = ref([])
	dataSource.value = workFlowUtils.getListField(props.formFieldList).map((m) => {
		return {
			label: m.label,
			model: m.selectTable + '.' + m.model,
			field: m.model,
			type: m.type
		}
	})

	const selectedRowKeys = ref([])

	const showFormUserModal = (data = '') => {
		visible.value = true
		selectedRowKeys.value = data
	}

	// 设置默认选中的
	const state = reactive({
		selectedRowKeys: selectedRowKeys.value,
		loading: false
	})
	// 点击复选框回调
	const onSelectChange = (selectedRowKeys) => {
		state.selectedRowKeys = selectedRowKeys
	}

	// 确定
	const handleOk = () => {
		const result = dataSource.value.filter((item) => state.selectedRowKeys[0] === item.model)
		emit('click', result[0])
		visible.value = false
	}
	// 关闭
	const handleCancel = () => {
		visible.value = false
	}

	// 抛出方法，让上个界面使用
	defineExpose({
		showFormUserModal
	})
</script>

<style lang="less">
	.form-user-table {
		overflow: auto;
		max-height: 400px;
	}
</style>
