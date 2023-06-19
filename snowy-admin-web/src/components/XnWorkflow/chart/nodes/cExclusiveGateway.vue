<template>
	<div class="branch-wrap">
		<div class="branch-box-wrap">
			<div class="branch-box" style="margin-top: 0px">
				<div v-for="(item, index) in childNode.conditionNodeList" :key="index" class="col-box">
					<div class="condition-node">
						<div class="condition-node-box">
							<div class="auto-judge">
								<div class="title">
									<span class="node-title">{{ item.title }}</span>
									<span class="priority-title">优先级{{ item.properties.configInfo.priorityLevel }}</span>
								</div>
								<div class="content">
									<span v-if="toText(childNode, index)">{{ toText(childNode, index) }}</span>
									<span v-else class="placeholder">请设置条件</span>
								</div>
							</div>
							<add-nodes v-model="item.childNode" />
						</div>
					</div>
					<slot v-if="item.childNode" :node="item"></slot>
					<div v-if="index == 0" class="top-left-cover-line"></div>
					<div v-if="index == 0" class="bottom-left-cover-line"></div>
					<div v-if="index == childNode.conditionNodeList.length - 1" class="top-right-cover-line"></div>
					<div v-if="index == childNode.conditionNodeList.length - 1" class="bottom-right-cover-line"></div>
				</div>
			</div>
			<add-nodes v-model="childNode.childNode" :parent-data="childNode" />
		</div>
	</div>
</template>

<script>
	import addNodes from './cAddNode.vue'

	export default {
		components: {
			addNodes
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			currentActivityId: { type: String, default: () => '' }
		},
		data() {
			return {
				childNode: {},
				index: 0,
				operatorList: [
					{
						label: '等于',
						value: '=='
					},
					{
						label: '不等于',
						value: '!='
					},
					{
						label: '大于',
						value: '>'
					},
					{
						label: '大于等于',
						value: '>='
					},
					{
						label: '小于',
						value: '<'
					},
					{
						label: '小于等于',
						value: '<='
					},
					{
						label: '包含',
						value: 'include'
					},
					{
						label: '不包含',
						value: 'notInclude'
					}
				]
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
	.workflow-design .condition-node {
		min-height: 200px !important;
	}
</style>
