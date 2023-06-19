<template>
	<a-card :bordered="false">
		<SnowyFormDesign
			ref="formDesign"
			:show-head="false"
			:toolbars="['preview', 'reset']"
			show-toolbars-text
			:database-config="formTableValue"
			:fields="formDesignFields"
			:dictData="dictData"
		/>
	</a-card>
</template>

<script setup name="formDesign">
	import tool from '@/utils/tool'
	import SnowyFormDesignCom from 'snowy-form-design'

	const dictData = tool.data.get('DICT_TYPE_TREE_DATA')
	const token = tool.data.get('TOKEN')
	const apiUrl = tool.data.get('SNOWY_SYS_BASE_CONFIG').SNOWY_SYS_API_URL + '/api/webapp/dev/file/uploadDynamicReturnUrl'
	SnowyFormDesignCom.setConfig({
		uploadFile: apiUrl, // 上传文件地址
		uploadImage: apiUrl, // 上传图片地址
		uploadFileName: 'file', // 上传文件name
		uploadImageName: 'file', // 上传图片name
		uploadFileData: { data: 'snowy' }, // 上传文件额外参数
		uploadImageData: { data: 'snowy' }, // 上传图片额外参数
		uploadFileHeaders: { data: 'snowy' } // 上传文件请求头部
	})

	const formDesign = ref()
	const formTableValue = ref([])
	// 表单设计器可以拖的组件
	const formDesignFields = [
		'input',
		'textarea',
		'number',
		'select',
		'checkbox',
		'radio',
		'date',
		'time',
		'rate',
		'slider',
		'uploadFile',
		'uploadImg',
		'treeSelect',
		'batch',
		'editor',
		'switch',
		'alert',
		'text',
		'html',
		'divider',
		'card',
		'grid',
		'table'
	]

	// 整个工作流的数据模型，需要接口获取到数据，做回显，有改动后改它即可
	const workFlowParame = {
		formValue: false,
		formTableValue: [],
		modelValue: {}
	}
	const setValue = (value, tableValue) => {
		formTableValue.value = tableValue
		formDesign.value.handleSetData(value)
		// 缓存model设计器的数据
		// workFlowParame.modelValue = value.modelValue
	}
	const getValue = () => {
		return formDesign.value.getValue()
	}
	defineExpose({
		getValue,
		setValue
	})
</script>
