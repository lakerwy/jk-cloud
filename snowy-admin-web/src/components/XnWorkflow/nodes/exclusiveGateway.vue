<template>
	<div class="branch-wrap">
		<div class="branch-box-wrap">
			<div class="branch-box">
				<a-button class="add-branch" type="primary" shape="round" @click="addTerm"> 添加条件 </a-button>
				<div v-for="(item, index) in childNode.conditionNodeList" :key="index" class="col-box">
					<div class="condition-node">
						<div class="condition-node-box">
							<div class="auto-judge" @click="show(index)">
								<div v-if="index != 0" class="sort-left" @click.stop="arrTransfer(index, -1)">
									<left-outlined />
								</div>
								<div class="title">
									<span class="node-title">{{ item.title }}</span>
									<span class="priority-title">优先级{{ item.properties.configInfo.priorityLevel }}</span>
									<close-outlined class="close" @click.stop="delTerm(index)" />
								</div>
								<div class="content">
									<span v-if="toText(childNode, index)">{{ toText(childNode, index) }}</span>
									<span v-else class="placeholder">请设置条件</span>
								</div>
								<div
									v-if="index != childNode.conditionNodeList.length - 1"
									class="sort-right"
									@click.stop="arrTransfer(index)"
								>
									<right-outlined />
								</div>
							</div>
							<add-node v-model="item.childNode" :node-item="item" />
						</div>
					</div>
					<slot v-if="item.childNode" :node="item" />
					<div v-if="index == 0" class="top-left-cover-line" />
					<div v-if="index == 0" class="bottom-left-cover-line" />
					<div v-if="index == childNode.conditionNodeList.length - 1" class="top-right-cover-line" />
					<div v-if="index == childNode.conditionNodeList.length - 1" class="bottom-right-cover-line" />
				</div>
			</div>
			<add-node v-model="childNode.childNode" :parent-data="childNode"></add-node>
		</div>

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
					<a-tab-pane key="1" tab="条件配置" force-render>
						<a-form layout="vertical">
							<div v-show="!isNodeLegal(form)" style="margin-bottom: 10px">
								<a-alert message="请填写完成所有项！" type="error" />
							</div>
							<p>
								<a-button type="primary" round @click="addDynamicValidateForm" size="small">
									<plus-outlined />
									增加条件组
								</a-button>
							</p>
							<a-form-item v-for="(domain, index) in dynamicValidateForm" :key="index">
								<a-divider />
								<a-row>
									<a-col :span="22">
										<a-table :data-source="domain" size="middle" :pagination="false">
											<a-table-column data-index="field" title="条件字段" width="130">
												<template #default="{ record }">
													<a-select
														v-model:value="record.field"
														placeholder="请选择"
														v-if="recordData.formType === 'DESIGN'"
													>
														<a-select-option
															v-for="formField in fieldList"
															:key="formField.model"
															:value="formField.model"
															@click="record.label = formField.label"
															>{{ formField.label }}</a-select-option
														>
													</a-select>
													<a-input v-model:value="record.field" placeholder="条件" v-else />
												</template>
											</a-table-column>
											<a-table-column data-index="label" title="描述">
												<template #default="{ record }">
													<a-input v-model:value="record.label" placeholder="描述" />
												</template>
											</a-table-column>
											<a-table-column data-index="operator" title="运算符" width="140">
												<template #default="{ record }">
													<a-select
														v-model:value="record.operator"
														placeholder="请选择"
														v-if="recordData.formType === 'DESIGN'"
													>
														<a-select-option value="==">等于</a-select-option>
														<a-select-option value="!=">不等于</a-select-option>
														<a-select-option value=">" v-if="isSelectOption(record)">大于</a-select-option>
														<a-select-option value=">=" v-if="isSelectOption(record)">大于等于</a-select-option>
														<a-select-option value="<" v-if="isSelectOption(record)">小于</a-select-option>
														<a-select-option value="<=" v-if="isSelectOption(record)">小于等于</a-select-option>
														<a-select-option value="include" v-if="!isSelectOption(record)">包含</a-select-option>
														<a-select-option value="notInclude" v-if="!isSelectOption(record)">不包含</a-select-option>
													</a-select>
													<a-input v-model:value="record.operator" placeholder="条件" v-else />
												</template>
											</a-table-column>
											<a-table-column data-index="value" title="值" width="100">
												<template #default="{ record }">
													<a-input v-model:value="record.value" placeholder="值" />
												</template>
											</a-table-column>
											<a-table-column data-index="value" title="移除" width="55">
												<template #default="{ index }">
													<a-button size="small" type="primary" danger ghost @click="deleteConditionList(index, domain)"
														>移除</a-button
													>
												</template>
											</a-table-column>
										</a-table>
										<a-button type="dashed" class="dashedButton" @click="addConditionList(index)">
											<PlusOutlined />
											增加条件
										</a-button>
									</a-col>
									<a-col :span="2" class="deleteIcon">
										<minus-circle-two-tone class="minusCircle" @click="delDomains(index)" />
										<!--v-if="dynamicValidateForm.length > 1"-->
									</a-col>
								</a-row>
							</a-form-item>
						</a-form>
					</a-tab-pane>
					<a-tab-pane key="2" tab="执行监听" force-render>
						<prop-listener-info
							ref="propExecutionListenerInfoRef"
							:listenerValue="form.properties.executionListenerInfo"
							:defaultListenerList="executionListenerInfo"
							:listener-value-array="executionListenerArray"
						/>
					</a-tab-pane>
				</a-tabs>
			</a-layout-content>
			<template #footer>
				<a-button type="primary" style="margin-right: 8px" @click="save">保存</a-button>
				<a-button @click="drawer = false">取消</a-button>
			</template>
		</xn-form-container>
	</div>
</template>

<script>
	import { cloneDeep } from 'lodash-es'
	import addNode from './addNode.vue'
	import config from '@/components/XnWorkflow/nodes/config/config'
	import workFlowUtils from '@/components/XnWorkflow/nodes/utils/index'
	import PropListenerInfo from './prop/propListenerInfo.vue'

	export default {
		components: {
			addNode,
			PropListenerInfo
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			formFieldListValue: { type: Array, default: () => [] },
			recordData: { type: Object, default: () => {} },
			executionListenerArray: { type: Array, default: () => [] },
			taskListenerArray: { type: Array, default: () => [] }
		},
		data() {
			return {
				childNode: {},
				drawer: false,
				isEditTitle: false,
				index: 0,
				form: {},
				dynamicValidateForm: [],
				fieldList: [],
				activeKey: '1',
				executionListenerInfo: cloneDeep(config.listener.exclusiveGatewayExecutionListenerInfo),
				operatorList: cloneDeep(config.exclusiveGatewayConfig.operatorList)
			}
		},
		watch: {
			modelValue() {
				this.childNode = this.modelValue
			}
		},
		mounted() {
			this.childNode = this.modelValue
			// 把字段给掏出来
			this.fieldList = workFlowUtils.getListField(this.formFieldListValue).map((m) => {
				let type = m.type //slider rate number
				if (type === 'slider' || type === 'rate' || type === 'number') {
					m.type = 'number'
				}
				return {
					label: m.label,
					model: m.selectTable + '.' + m.model,
					type: m.type
				}
			})
		},
		methods: {
			show(index) {
				this.index = index
				this.form = {}
				this.form = cloneDeep(this.childNode.conditionNodeList[index])
				this.drawer = true
				this.dynamicValidateForm = this.form.properties.conditionInfo
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
				this.form.properties.conditionInfo = this.dynamicValidateForm
				this.form.properties.executionListenerInfo = this.$refs.propExecutionListenerInfoRef.selectedListenerList()
				if (this.isNodeLegal(this.form)) {
					this.form.dataLegal = true
					this.childNode.conditionNodeList[this.index] = this.form
					this.setCalibration()
					this.$emit('update:modelValue', this.childNode)
					this.drawer = false
				} else {
					this.form.dataLegal = false
				}
			},
			isSelectOption(record) {
				if (record.field) {
					return this.fieldList.find((f) => f.model === record.field).type === 'number'
				}
			},
			// 校验此条件是否通过
			isNodeLegal(data) {
				const priorityLevel = data.properties.configInfo.priorityLevel

				const len = this.childNode.conditionNodeList.length
				const priorityLevelMax = this.childNode.conditionNodeList[len - 1].properties.configInfo.priorityLevel
				// 如果往其他条件的分支中增加，那我们一视同仁
				if (priorityLevelMax === priorityLevel) {
					if (this.dynamicValidateForm.length > 0) {
						for (let i = 0; i < this.dynamicValidateForm.length; i++) {
							const obj = this.dynamicValidateForm[i]
							if (obj.length > 0) {
								return this.isNodeLegalItem()
							}
						}
					} else {
						return true
					}
				} else {
					return this.isNodeLegalItem()
				}
			},
			// 设置校验
			setCalibration() {
				// 在数据返回更新之前，我要顺手吧优先级最后的条件校验设置为 true，管他设没设
				for (let i = 0; i < this.childNode.conditionNodeList.length; i++) {
					let conditionNode = this.childNode.conditionNodeList[i]
					// 取到优先级
					const priorityLevel = conditionNode.properties.configInfo.priorityLevel
					// 如果是最高的
					if (priorityLevel === this.childNode.conditionNodeList.length) {
						// 给成通过，不管他的条件，本身优先级最后的就是其他条件进入，一般也不设
						conditionNode.dataLegal = true
					} else {
						// 其他地方的，判断是否有条件，无条件的统统给 false
						if (conditionNode.properties.conditionInfo.length === 0) {
							conditionNode.dataLegal = false
						}
					}
				}
			},
			isNodeLegalItem() {
				let arrNum = 0
				let objNum = 0
				let successNum = 0
				if (this.dynamicValidateForm.length > 0) {
					for (let i = 0; i < this.dynamicValidateForm.length; i++) {
						const obj = this.dynamicValidateForm[i]
						let objNumItem = 0
						if (obj.length > 0) {
							arrNum++
							for (let a = 0; a < obj.length; a++) {
								objNumItem++
								if (this.isObjLegal(obj[a])) {
									successNum++
								}
							}
							objNum = objNumItem
						}
					}
				}
				if (successNum !== 0) {
					if (arrNum * objNum === successNum) {
						return true
					}
				}
				return false
			},
			// 校验对象中是否有空值
			isObjLegal(obj) {
				let a = 0
				for (let b in obj) {
					if (!obj[b]) {
						a++
					}
				}
				if (a === 1) {
					return true
				} else {
					return false
				}
			},
			// 增加条件组
			addDynamicValidateForm() {
				this.dynamicValidateForm.push([])
			},
			// 删除条件组
			delDomains(index) {
				this.dynamicValidateForm.splice(index, 1)
			},
			addTerm() {
				const len = this.childNode.conditionNodeList.length
				const priorityLevel = this.childNode.conditionNodeList[len - 1].properties.configInfo.priorityLevel
				// 创建分支节点 n
				const condition = cloneDeep(config.nodeModel.node)
				condition.id = this.$TOOL.snowyUuid()
				condition.type = 'sequenceFlow'
				condition.title = `条件${priorityLevel + 1}`
				// 创建分支节点2 configInfo
				const condition1ConfigInfo = cloneDeep(config.nodeConfigInfo.conditionConfigInfo)
				condition1ConfigInfo.priorityLevel = priorityLevel + 1

				condition.properties.configInfo = condition1ConfigInfo
				this.childNode.conditionNodeList.push(condition)
			},
			delTerm(index) {
				this.childNode.conditionNodeList.splice(index, 1)
				if (this.childNode.conditionNodeList.length === 1) {
					if (this.childNode.childNode) {
						if (JSON.stringify(this.childNode.conditionNodeList[0].childNode) !== '{}') {
							this.reData(this.childNode.conditionNodeList[0].childNode, this.childNode.childNode)
						} else {
							this.childNode.conditionNodeList[0].childNode = this.childNode.childNode
						}
					}
					this.$emit('update:modelValue', this.childNode.conditionNodeList[0].childNode)
				}
			},
			reData(data, addData) {
				if (JSON.stringify(data) !== '{}') {
					data.childNode = addData
				} else {
					this.reData(data.childNode, addData)
				}
			},
			arrTransfer(index, type = 1) {
				this.childNode.conditionNodeList[index] = this.childNode.conditionNodeList.splice(
					index + type,
					1,
					this.childNode.conditionNodeList[index]
				)[0]
				this.childNode.conditionNodeList.map((item, index) => {
					item.properties.configInfo.priorityLevel = index + 1
				})
				this.setCalibration()
				this.$emit('update:modelValue', this.childNode)
			},
			addConditionList(index) {
				const domainsObj = {
					label: '',
					key: '',
					operator: '==',
					value: ''
				}
				this.dynamicValidateForm[index].push(domainsObj)
			},
			deleteConditionList(index, domain) {
				domain.splice(index, 1)
			},
			toText(childNode, index) {
				const conditionList = childNode.conditionNodeList[index].properties.conditionInfo
				const priorityLevel = childNode.conditionNodeList[index].properties.configInfo.priorityLevel
				const len = this.childNode.conditionNodeList.length
				const priorityLevelMax = this.childNode.conditionNodeList[len - 1].properties.configInfo.priorityLevel

				if (JSON.stringify(conditionList) !== undefined && conditionList.length > 0) {
					let text = ''
					for (let i = 0; i < conditionList.length; i++) {
						for (let j = 0; j < conditionList[i].length; j++) {
							if (j + 1 !== conditionList[i].length) {
								text =
									text +
									conditionList[i][j].label +
									this.getOperatorLabel(conditionList[i][j].operator) +
									conditionList[i][j].value +
									' 且 '
							} else {
								text =
									text +
									conditionList[i][j].label +
									this.getOperatorLabel(conditionList[i][j].operator) +
									conditionList[i][j].value
							}
						}
						if (i + 1 !== conditionList.length) {
							text = text + ' 或 '
						}
					}
					return text
				} else if (conditionList.length === 0 && priorityLevel < priorityLevelMax) {
					return false
				} else {
					return '其他条件进入此流程'
				}
			},
			// 通过value 获取界面显示的label汉字
			getOperatorLabel(value) {
				return this.operatorList.find((item) => item.value === value).label
			}
		}
	}
</script>

<style scoped type="less">
	.deleteIcon {
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.minusCircle {
		font-size: 25px;
	}
	.dashedButton {
		margin-top: 10px;
		width: 100%;
	}
</style>
