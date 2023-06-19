<template>
	<xn-form-container
		title="发起流程"
		:width="drawerWidth"
		:visible="visible"
		:destroy-on-close="true"
		:bodyStyle="{ 'padding-top': '0px' }"
		@close="onClose"
	>
		<a-tabs v-model:activeKey="activeKey">
			<a-tab-pane key="flowForm" tab="表单">
				<snowy-form-build
					v-if="recordData.formType === 'DESIGN'"
					ref="formBuildRef"
					:value="jsonData"
					:config="formConfig"
				/>
				<component v-else ref="customFormRef" :is="customFormsLayouts" :value="initiatorDataJson" />
			</a-tab-pane>
			<a-tab-pane key="flowChart" tab="流程图">
				<flowChart v-model="modelDesignData" />
			</a-tab-pane>
		</a-tabs>
		<template #footer v-if="activeKey === 'flowForm'">
			<a-spin :spinning="buttonSpinning">
				<span v-for="button in buttonInfo" :key="button.key">
					<a-button
						style="margin-left: 8px"
						:type="button.type"
						:danger="button.type === 'danger'"
						@click="buttonClick(button.key)"
						v-if="button.value === 'SHOW'"
					>
						<template #icon>
							<component :is="button.icon" />
						</template>
						{{ button.label }}
					</a-button>
				</span>
			</a-spin>
		</template>
		<userPosSelector ref="userPosSelectorRef" @ok="startEvent" />
	</xn-form-container>
</template>

<script setup name="newProcess">
	import { message } from 'ant-design-vue'
	import { nextTick } from 'vue'
	import tool from '@/utils/tool'
	import processMyApi from '@/api/flw/processMyApi'
	import FlowChart from '@/components/XnWorkflow/chart/index.vue'
	import UserPosSelector from './userPosSelector.vue'
	import workFlowUtils from '@/components/XnWorkflow/nodes/utils/index'
	import { loadComponent } from '@/components/XnWorkflow/customForm'
	const customFormRef = ref()
	const customFormsLayouts = ref()

	const apiUrl = tool.data.get('SNOWY_SYS_BASE_CONFIG').SNOWY_SYS_API_URL + '/dev/file/uploadDynamicReturnUrl'
	const token = tool.data.get('TOKEN')
	const formConfig = ref({
		uploadFile: apiUrl, // 上传文件地址
		uploadImage: apiUrl, // 上传图片地址
		uploadFileName: '', // 上传文件name
		uploadImageName: '', // 上传图片name
		uploadFileData: { data: 223 }, // 上传文件额外参数
		uploadImageData: { data: 223 }, // 上传图片额外参数
		uploadFileHeaders: { token: token }, // 上传文件请求头部
		uploadImageHeaders: { token: token } // 上传图片请求头部
	})
	// 选中的tab
	const activeKey = ref('flowForm')
	const nodeData = ref()
	// 模型设计器的值
	const modelDesignData = ref({})
	const jsonData = ref([])
	const formBuildRef = ref()
	// 默认是关闭状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })

	// 职位选择ref定义
	const userPosSelectorRef = ref()
	const formData = ref({})
	const buttonInfo = ref()
	const buttonSpinning = ref(false)
	const recordData = ref()
	const buttonType = ref()
	const openType = ref()
	// json数据
	const initiatorDataJson = ref()
	// 自动获取宽度，默认获取浏览器的宽度的90%
	const drawerWidth = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) * 0.5

	// 打开抽屉
	const onOpen = (record, type) => {
		openType.value = type
		// if (type === 'DRAFT') {
		// 	record.id = record.modelId
		// }
		recordData.value = record
		modelDesignData.value = JSON.parse(record.processJson)
		const thisNode = JSON.parse(record.processJson).childNode.childNode
		nodeData.value = thisNode
		buttonInfo.value = thisNode.properties.buttonInfo
		if (record.formType === 'DESIGN') {
			if (thisNode.properties.fieldInfo.length === 0 || thisNode.properties.buttonInfo.length === 0) {
				message.warning('该节点按钮与字段配置不正确，请联系管理员')
				return
			}
			// 转换数据
			jsonData.value = workFlowUtils.convSettingsField(JSON.parse(record.formJson), thisNode.properties.fieldInfo)
			if (record.dataJson) {
				const formData = Object.values(JSON.parse(record.dataJson))[0]
				nextTick(() => {
					setTimeout(() => {
						// 设置表单参数
						formBuildRef.value.setData(formData)
					}, 200)
				})
			}
		} else {
			if (thisNode.properties.buttonInfo.length === 0) {
				message.warning('该节点按钮配置不正确，请联系管理员')
				return
			}
			if (!thisNode.properties.formInfo) {
				message.warning('该节点未配置表单，请联系管理员')
				return
			}
			const startTaskForm = thisNode.properties.formInfo.find((f) => f.key === 'FORM_URL').value
			customFormsLayouts.value = loadComponent(startTaskForm)
			if (record.dataJson) {
				initiatorDataJson.value = JSON.parse(record.dataJson)
			}
		}
		visible.value = true
	}
	// 关闭抽屉
	const onClose = () => {
		initiatorDataJson.value = {}
		recordData.value = {}
		visible.value = false
	}
	// 点击按钮
	const buttonClick = (key) => {
		buttonType.value = key
		getFormDataAndSubmit()
	}
	// 验证并提交数据
	const getFormDataAndSubmit = () => {
		if (recordData.value.formType === 'DESIGN') {
			formBuildRef.value.getData().then((values) => {
				formData.value = values
				if (buttonType.value === 'SUBMIT') {
					// 打开职位选择
					userPosSelectorRef.value.onOpen()
				} else {
					saveDraft()
				}
			})
		} else {
			customFormRef.value.getData().then((values) => {
				formData.value = values
				if (buttonType.value === 'SUBMIT') {
					// 打开职位选择
					userPosSelectorRef.value.onOpen()
				} else {
					saveDraft()
				}
			})
		}
	}
	// 保存草稿
	const saveDraft = () => {
		// 拼接JSON
		const param = {
			title: recordData.value.name,
			dataJson: JSON.stringify(formData.value)
		}
		if (openType.value === 'NEW') {
			param.modelId = recordData.value.id
		}
		// 草稿再保存
		if (openType.value === 'DRAFT') {
			param.modelId = recordData.value.modelId
			param.id = recordData.value.id
		}
		buttonSpinning.valueOf = true
		processMyApi
			.processSaveDraft(param)
			.then(() => {
				if (openType.value === 'DRAFT') {
					// 如果是草稿打开的，我们给其一个回调让刷新去
					emit('successful')
				}
				onClose()
			})
			.finally(() => {
				buttonSpinning.valueOf = false
			})
	}
	// 职位选择回调，这里回调后发起流程
	const startEvent = (record) => {
		const userInfo = tool.data.get('USER_INFO')
		// 拼接JSON
		const param = {
			modelId: recordData.value.id,
			dataJson: JSON.stringify(formData.value),
			initiator: userInfo.id,
			initiatorName: userInfo.name,
			initiatorOrgId: record.orgId,
			initiatorOrgName: record.orgName,
			initiatorPositionId: record.positionId,
			initiatorPositionName: record.positionName
		}
		// 这里直接换成草稿中的模型id就行
		if (openType.value === 'DRAFT') {
			param.modelId = recordData.value.modelId
		}
		// 调用接口发起流程
		buttonSpinning.valueOf = true
		processMyApi
			.processStart(param)
			.then(() => {
				// 判断如果是草稿打开后发起，那么咱们给个请求删了这个草稿
				if (openType.value === 'DRAFT') {
					const deleteParam = [
						{
							id: recordData.value.id
						}
					]
					processMyApi.processDeleteDraft(deleteParam).then(() => {
						onClose()
						emit('successful')
					})
				} else {
					message.success('操作成功')
					onClose()
				}
			})
			.finally(() => {
				buttonSpinning.valueOf = false
			})
	}
	// 调用这个函数将子组件的一些数据和方法暴露出去
	defineExpose({
		onOpen
	})
</script>
