<!--
 * @Descripttion: 仿钉钉流程设计器
 * @version: 1.2
 * @Author: sakuya
 * @Date: 2021年9月14日08:38:35
 * @LastEditors: yubaoshan
 * @LastEditTime: 2022年2月9日16:48:49
-->
<template>
	<div class="workflow-design">
		<!-- 配置流程全局属性 -->
		<div style="float: right; padding-right: 10px">
			<span v-if="!toDataLegal(childNode)" style="padding-right: 5px">
				<exclamation-circle-outlined style="color: red; font-size: 18px" />
			</span>
			<a-tooltip>
				<template #title>配置流程全局属性</template>
				<a-button @click="$refs.process.showDrawer()">
					<template #icon>
						<setting-outlined />
					</template>
					全局配置
				</a-button>
			</a-tooltip>
		</div>
		<div class="box-scale">
			<node-wrap
				v-if="childNode"
				v-model="childNode.childNode"
				:form-field-list-value="childFormFieldListValue"
				:record-data="childRecordData"
				:process-config-info="childNode.properties.configInfo"
				:execution-listener-array="executionListenerArray"
				:task-listener-array="taskListenerArray"
			/>
			<div class="end-node">
				<div class="end-node-circle"></div>
				<div class="end-node-text">流程结束</div>
			</div>
		</div>
		<process
			ref="process"
			v-model="childNode"
			:form-field-list-value="childFormFieldListValue"
			:record-data="childRecordData"
			:sn-template-array="snTemplateArray"
			:print-template-array="printTemplateArray"
			:execution-listener-array="executionListenerArray"
			:task-listener-array="taskListenerArray"
		/>
	</div>
</template>

<script>
	import nodeWrap from './nodeWrap.vue'
	import process from './process.vue'

	export default {
		components: {
			nodeWrap,
			process
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			formFieldListValue: { type: Array, default: () => [] },
			recordData: { type: Object, default: () => {} },
			snTemplateArray: { type: Array, default: () => [] },
			printTemplateArray: { type: Array, default: () => [] },
			executionListenerArray: { type: Array, default: () => [] },
			taskListenerArray: { type: Array, default: () => [] }
		},
		data() {
			return {
				childNode: this.modelValue,
				childFormFieldListValue: this.formFieldListValue,
				childRecordData: this.recordData
			}
		},
		watch: {
			modelValue(val) {
				this.childNode = val
			},
			// 监听字段列表传输的相关动静
			formFieldListValue(val) {
				this.childFormFieldListValue = val
			},
			recordData(val) {
				this.childRecordData = val
			},
			childNode(val) {
				this.$emit('update:modelValue', val)
			}
		},
		methods: {
			toDataLegal(childNode) {
				if (childNode === undefined) {
					return false
				} else {
					return childNode.dataLegal
				}
			}
		}
	}
</script>

<style lang="less">
	@import './flowIndex.less';
</style>
