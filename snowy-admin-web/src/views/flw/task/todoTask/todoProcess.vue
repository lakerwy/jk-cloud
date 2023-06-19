<template>
	<xn-form-container
		title="审批流程"
		:width="drawerWidth"
		:visible="visible"
		:destroy-on-close="true"
		:bodyStyle="{ 'padding-top': '0px' }"
		@close="onClose"
		:push="false"
	>
		<a-spin :spinning="spinning">
			<a-tabs v-model:activeKey="activeKey">
				<a-tab-pane key="flowForm" tab="表单">
					<snowy-form-build
						v-if="formType === 'DESIGN'"
						ref="formBuild"
						:value="initiatorFormJson"
						:outputString="false"
					/>
					<component v-else ref="customFormRef" :is="customFormsLayouts" :value="initiatorDataJson" />
				</a-tab-pane>
				<a-tab-pane key="flowChart" tab="流程图">
					<flow-chart v-model="initiatorModelJson" :currentActivityId="recordData.currentActivityId"/>
				</a-tab-pane>
				<a-tab-pane key="flowTrunRecord" tab="流转记录">
					<timeline-form :commentList="commentList" />
				</a-tab-pane>
			</a-tabs>
		</a-spin>

		<template #footer v-if="activeKey === 'flowForm' || buttonInfo.length === 0">
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
		<pass-reject-form ref="passRejectFormRef" @ok="passRejectFormOk" />
		<task-turn-form ref="taskTurnFormRef" @successful="onClose()" />
		<task-back-form ref="taskBackFormRef" @successful="onClose()" />
		<task-jump-form ref="taskJumpFormRef" @successful="onClose()" />
		<task-add-sign-form ref="taskAddSignFormRef" @successful="onClose()" />
	</xn-form-container>
</template>

<script setup name="todoProcess">
	import { message } from 'ant-design-vue'
	import { nextTick } from 'vue'
	import processApi from '@/api/flw/processApi'
	import taskApi from '@/api/flw/taskApi'
	import FlowChart from '@/components/XnWorkflow/chart/index.vue'
	import workFlowUtils from '@/components/XnWorkflow/nodes/utils/index'
	import TimelineForm from '../../process/timelineForm.vue'
	import PassRejectForm from './passRejectForm.vue'
	import TaskTurnForm from './taskTurnForm.vue'
	import TaskBackForm from './taskBackForm.vue'
	import TaskJumpForm from './taskJumpForm.vue'
	import TaskAddSignForm from './taskAddSignForm.vue'
	import { loadComponent } from '@/components/XnWorkflow/customForm'

	const customFormRef = ref()
	const customFormsLayouts = ref()
	// 选中的tab
	const activeKey = ref('flowForm')
	// 整体界面loading
	const spinning = ref(false)
	// 这个界面三个标签界面的值
	const initiatorModelJson = ref({})
	const initiatorFormJson = ref([])
	const initiatorDataJson = ref()
	const commentList = ref([])

	const formBuild = ref()
	// 默认是关闭状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })

	const recordData = ref({})
	const formData = ref({})
	const formType = ref()
	const buttonInfo = ref()
	const buttonSpinning = ref(false)
	const passRejectFormRef = ref()
	const taskTurnFormRef = ref()
	const taskBackFormRef = ref()
	const taskJumpFormRef = ref()
	const taskAddSignFormRef = ref()
	// 自动获取宽度，默认获取浏览器的宽度的90%
	const drawerWidth = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) * 0.5
	// 打开抽屉
	const onOpen = (record) => {
		// 缓存一条记录
		recordData.value = record
		// currentActivityId
		// 获取此界面的值
		const param = {
			id: record.processInstanceId
		}
		spinning.value = true
		processApi
			.processDetail(param)
			.then((data) => {
				formType.value = data.formType
				commentList.value = data.commentList
				if (!data.initiatorDataJson) {
					message.warning('此流程表单无任何数据，无法办理，请联系管理员！')
					return
				}
				// 设置模型，等上面这个方法走完，再执行这个
				initiatorModelJson.value = workFlowUtils.coalesceDataListChildNode(
					JSON.parse(data.initiatorModelJson),
					data.commentList
				)
				// 取节点（用到按钮权限跟字段），并且给节点set一个json，也就是我们的审批记录
				const childNode = workFlowUtils.getChildNode(
					initiatorModelJson.value,
					record.currentActivityId,
					data.commentList
				)
				// 给按钮赋值，这里就不管按钮了，界面上循环展示就行
				buttonInfo.value = childNode.properties.buttonInfo
				formData.value = data.initiatorDataJson
				if (data.formType === 'DESIGN') {
					// 设置表单 并且 处理显示字段
					initiatorFormJson.value = workFlowUtils.convSettingsField(
						JSON.parse(data.initiatorFormJson),
						childNode.properties.fieldInfo
					)
					const formBuildData = Object.values(JSON.parse(data.initiatorDataJson))[0]
					nextTick(() => {
						// 设置表单参数
						formBuild.value.setData(formBuildData)
					})
				} else {
					if (!childNode.properties.formInfo) {
						message.warning('该节点未配置表单，请联系管理员')
						return
					}
					const userTaskForm = childNode.properties.formInfo.find((f) => f.key === 'FORM_URL').value
					customFormsLayouts.value = loadComponent(userTaskForm)
					initiatorDataJson.value = JSON.parse(data.initiatorDataJson)
				}
			})
			.finally(() => {
				spinning.value = false
			})
		visible.value = true
	}
	// 关闭抽屉
	const onClose = () => {
		emit('successful')
		activeKey.value = 'flowForm'
		visible.value = false
	}
	const buttonType = ref()
	// 点击按钮
	const buttonClick = (key) => {
		buttonType.value = key
		if (key === 'PASS' || key === 'SUBMIT' || key === 'REJECT') {
			getDataValue()
		} else if (key === 'BACK') {
			const childNode = workFlowUtils.getChildNode(
				initiatorModelJson.value,
				recordData.value.currentActivityId,
				commentList.value
			)
			taskBackFormRef.value.onOpen(
				recordData.value,
				formData.value,
				initiatorModelJson.value.properties.configInfo.processEnableCommentRequired,
				childNode
			)
		} else if (key === 'ADD_SIGN') {
			taskAddSignFormRef.value.onOpen(
				recordData.value,
				initiatorModelJson.value.properties.configInfo.processEnableCommentRequired
			)
		} else if (key === 'PRINT') {
			message.info('开发中')
		} else if (key === 'JUMP') {
			taskJumpFormRef.value.onOpen(
				recordData.value,
				formData.value,
				initiatorModelJson.value.properties.configInfo.processEnableCommentRequired
			)
		} else if (key === 'TURN') {
			taskTurnFormRef.value.onOpen(
				recordData.value,
				initiatorModelJson.value.properties.configInfo.processEnableCommentRequired
			)
		}
	}
	// 同意或拒绝
	const getDataValue = () => {
		if (formType.value === 'DESIGN') {
			formBuild.value.getData().then((values) => {
				submitCommon(values)
			})
		} else {
			customFormRef.value.getData().then((values) => {
				submitCommon(values)
			})
		}
	}
	// 共用提交中转
	const submitCommon = (values) => {
		formData.value = values
		// 如果是重新提交，不弹框
		if (buttonType.value === 'SUBMIT') {
			const params = {
				id: recordData.value.id,
				dataJson: JSON.stringify(values)
			}
			buttonSpinning.value = true
			taskApi
				.taskAdjust(params)
				.then(() => {
					onClose()
				})
				.finally(() => {
					buttonSpinning.value = false
				})
		} else {
			// 剩下就是保存、拒绝 弹出底部抽屉，进行填写同意意见
			passRejectFormRef.value.onOpen(
				buttonType.value,
				initiatorModelJson.value.properties.configInfo.processEnableCommentRequired
			)
		}
	}
	// 意见框返回，并调用接口，返回数据
	const passRejectFormOk = (value) => {
		value.id = recordData.value.id
		value.dataJson = JSON.stringify(formData.value)
		buttonSpinning.value = true
		// 同意
		if (buttonType.value === 'PASS') {
			taskApi
				.taskPass(value)
				.then(() => {
					onClose()
				})
				.finally(() => {
					buttonSpinning.value = false
				})
		}
		// 拒绝
		if (buttonType.value === 'REJECT') {
			taskApi
				.taskReject(value)
				.then(() => {
					onClose()
				})
				.finally(() => {
					buttonSpinning.value = false
				})
		}
	}
	// 调用这个函数将子组件的一些数据和方法暴露出去
	defineExpose({
		onOpen
	})
</script>
