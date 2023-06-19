<template>
	<div class="node-wrap">
		<div class="node-wrap-box start-node" @click="show">
			<div class="title" style="background: #576a95">
				<user-outlined class="icon" />
				<span>{{ childNode.title }}</span>
			</div>
			<div class="content">
				<span v-if="toText(childNode)">{{ toText(childNode) }}</span>
				<span v-else class="placeholder">请配置字段与按钮</span>
			</div>
		</div>
		<add-node v-model="childNode.childNode" />
		<xn-form-container
			v-model:visible="drawer"
			:destroy-on-close="true"
			:width="700"
			:body-style="{ 'padding-top': '0px' }"
		>
			<template #title>
				<div class="node-wrap-drawer__title">
					<label v-if="!isEditTitle" @click="editTitle">
						{{ form.title }}
						<edit-outlined class="node-wrap-drawer-title-edit" />
					</label>
					<a-input
						v-if="isEditTitle"
						ref="nodeTitle"
						v-model:value="form.title"
						allow-clear
						@blur="saveTitle"
						@keyup.enter="saveTitle"
					/>
				</div>
			</template>
			<a-layout-content>
				<a-tabs v-model:activeKey="activeKey">
					<a-tab-pane key="1" tab="按钮配置" force-render>
						<div class="font-span">
							<span>参与者可以看见哪些按钮</span>
						</div>
						<prop-button-info
							ref="propButtonInfo"
							:button-info="selectedButtonInfo"
							:show-button="startTaskDefaultButtonkey"
							:no-checked="startTaskNoCheckedButtonkey"
						/>
					</a-tab-pane>
					<a-tab-pane key="2" tab="字段配置" force-render v-if="recordData.formType === 'DESIGN'">
						<div class="font-span">
							<span>参与者可以看见表单哪些字段</span>
						</div>
						<prop-field-info ref="propFieldInfo" :field-info="fieldInfo" :form-field-list-value="formFieldListValue" />
					</a-tab-pane>
					<a-tab-pane key="3" tab="表单配置" force-render v-else>
						<div class="font-span">
							<a-alert message="参与者可以填写的表单" type="info" show-icon class="mb-2" />
						</div>
						<a-form ref="startTaskFormRef" :model="formData" layout="vertical">
							<a-form-item
								label="节点表单"
								name="FORM_URL"
								:rules="[{ required: true, message: '请输入节点表单组件地址' }]"
							>
								<a-input
									v-model:value="formData.FORM_URL"
									addon-before="src/views/flw/customform/"
									addon-after=".vue"
									placeholder="请输入节点表单组件地址"
									allow-clear
								>
									<template #suffix>
										<a-button
											v-if="formData.FORM_URL"
											type="primary"
											size="small"
											@click="$refs.previewCustomFormRef.onOpen(formData.FORM_URL)"
										>
											预览
										</a-button>
									</template>
								</a-input>
							</a-form-item>
							<a-form-item
								label="移动端节点表单"
								name="MOBILE_FORM_URL"
								:rules="[{ required: true, message: '请输入移动端节点表单组件地址' }]"
							>
								<a-input
									v-model:value="formData.MOBILE_FORM_URL"
									addon-before="pages/flw/customform/"
									addon-after=".vue"
									placeholder="请输入移动端节点表单组件地址"
									allow-clear
								/>
							</a-form-item>
						</a-form>
					</a-tab-pane>
					<a-tab-pane key="4" tab="执行监听" force-render>
						<prop-listener-info
							ref="propExecutionListenerInfoRef"
							:listener-value="form.properties.executionListenerInfo"
							:default-listener-list="executionListenerInfo"
							:listener-value-array="executionListenerArray"
						/>
					</a-tab-pane>
					<a-tab-pane key="5" tab="节点监听" force-render>
						<prop-listener-info
							ref="propTaskListenerInfoRef"
							:listener-value="form.properties.taskListenerInfo"
							:default-listener-list="taskListenerInfo"
							:listener-value-array="taskListenerArray"
						/>
					</a-tab-pane>
				</a-tabs>
			</a-layout-content>
			<template #footer>
				<a-button type="primary" style="margin-right: 8px" @click="save">保存</a-button>
				<a-button @click="drawer = false">取消</a-button>
			</template>
		</xn-form-container>
		<preview-custom-form ref="previewCustomFormRef" />
	</div>
</template>

<script>
	import { cloneDeep } from 'lodash-es'
	import config from '@/components/XnWorkflow/nodes/config/config'
	import addNode from './addNode.vue'
	import propButtonInfo from './prop/propButtonInfo.vue'
	import propFieldInfo from './prop/propFieldInfo.vue'
	import propListenerInfo from './prop/propListenerInfo.vue'
	import PreviewCustomForm from '@/components/XnWorkflow/nodes/common/previewCustomForm.vue'

	export default {
		components: {
			addNode,
			propButtonInfo,
			propFieldInfo,
			propListenerInfo,
			PreviewCustomForm
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			formFieldListValue: { type: Array, default: () => [] },
			recordData: { type: Object, default: () => {} },
			processConfigInfo: { type: Object, default: () => {} },
			executionListenerArray: { type: Array, default: () => [] },
			taskListenerArray: { type: Array, default: () => [] }
		},
		data() {
			return {
				childNode: {},
				drawer: false,
				isEditTitle: false,
				form: {},
				activeKey: '1',
				selectedButtonInfo: [],
				executionListenerInfo: cloneDeep(config.listener.startTaskExecutionListenerInfo),
				taskListenerInfo: cloneDeep(config.listener.userTaskTaskListenerInfo),
				startTaskDefaultButtonkey: cloneDeep(config.button.startTaskDefaultButtonkey),
				startTaskNoCheckedButtonkey: cloneDeep(config.button.startTaskNoCheckedButtonkey),
				fieldInfo: [],
				formData: {}
			}
		},
		watch: {
			modelValue() {
				this.childNode = this.modelValue
			}
		},
		mounted() {
			this.childNode = this.modelValue
		},
		methods: {
			show() {
				this.form = cloneDeep(this.childNode)
				this.isEditTitle = false
				this.drawer = true
				this.selectedButtonInfo = this.form.properties.buttonInfo
				this.fieldInfo = this.form.properties.fieldInfo
				// 初始化自定义表单字段值
				this.initFormInfo()
			},
			initFormInfo() {
				const processConfigInfo = cloneDeep(this.processConfigInfo)
				if (!this.form.properties.formInfo.find((f) => f.key === 'FORM_URL')) {
					this.formData.FORM_URL = processConfigInfo.processStartTaskFormUrl
				} else {
					this.formData.FORM_URL = this.form.properties.formInfo.find((f) => f.key === 'FORM_URL').value
				}
				if (!this.form.properties.formInfo.find((f) => f.key === 'MOBILE_FORM_URL')) {
					this.formData.MOBILE_FORM_URL = processConfigInfo.processStartTaskMobileFormUrl
				} else {
					this.formData.MOBILE_FORM_URL = this.form.properties.formInfo.find((f) => f.key === 'MOBILE_FORM_URL').value
				}
			},
			editTitle() {
				this.isEditTitle = true
				this.$nextTick(() => {
					this.$refs.nodeTitle.focus()
				})
			},
			saveTitle() {
				this.isEditTitle = false
			},
			save() {
				this.form.id = this.$TOOL.snowyUuid()
				this.form.properties.buttonInfo = this.$refs.propButtonInfo.selectedButtonKeyList()
				this.form.properties.executionListenerInfo = this.$refs.propExecutionListenerInfoRef.selectedListenerList()
				this.form.properties.taskListenerInfo = this.$refs.propTaskListenerInfoRef.selectedListenerList()
				if (this.recordData.formType === 'DESIGN') {
					this.form.properties.fieldInfo = this.$refs.propFieldInfo.selectedFieldList()
					this.form.dataLegal = true
					this.$emit('update:modelValue', this.form)
					this.drawer = false
				} else {
					this.$refs.startTaskFormRef
						.validate()
						.then((values) => {
							this.form.dataLegal = true
							this.form.properties.formInfo = cloneDeep(config.nodeModel.formInfo)
							this.form.properties.formInfo.find((f) => f.key === 'FORM_URL').value = values.FORM_URL
							this.form.properties.formInfo.find((f) => f.key === 'MOBILE_FORM_URL').value = values.MOBILE_FORM_URL
							this.$emit('update:modelValue', this.form)
							this.drawer = false
						})
						.catch((err) => {})
				}
			},
			// eslint-disable-next-line no-unused-vars
			toText(childNode) {
				if (childNode.dataLegal) {
					return '系统自动配置参与人'
				} else {
					return false
				}
			}
		}
	}
</script>

<style scoped>
	.font-span {
		padding-bottom: 8px;
	}
</style>
