<template>
	<div class="node-wrap">
		<a-tooltip placement="left">
			<template #title v-if="childNode.properties && childNode.properties.commentList.length > 0">
				<div v-for="comment in childNode.properties.commentList" :key="comment.id">
					{{ comment.userName }}于{{ comment.approveTime }}
					<a-tag color="rgb(212, 212, 212)">{{ comment.operateText }}</a-tag
					>，意见：{{ comment.comment }}
				</div>
			</template>
			<div :class="getClassName()">
				<div class="node-wrap-box">
					<div class="title" style="background: #ff943e">
						<user-outlined class="icon" />
						<span>{{ childNode.title }}</span>
					</div>
					<div class="content">
						<span v-if="toText(childNode)">{{ toText(childNode) }}</span>
						<span v-else class="placeholder">未选择审批人</span>
					</div>
				</div>
			</div>
		</a-tooltip>
		<add-nodes v-model="childNode.childNode" />
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
				childNode: {}
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
			toText(childNode) {
				if (JSON.stringify(childNode) !== '{}') {
					const strArray = this.toTag(childNode.properties.participateInfo[0])
					if (strArray.length > 0) {
						let value = ''
						// eslint-disable-next-line no-plusplus
						for (let i = 0; i < strArray.length; i++) {
							if (strArray.length === i + 1) {
								value = value + strArray[i]
							} else {
								value = value + strArray[i] + '，'
							}
						}
						return value
					} else {
						return false
					}
				} else {
					return false
				}
			},
			toTag(participateInfo) {
				// eslint-disable-next-line no-undefined
				if (participateInfo === undefined) {
					return []
				}
				if (participateInfo.label === '') {
					return []
				} else {
					let resultArray = []
					if (participateInfo.label.indexOf(',') !== -1) {
						resultArray = participateInfo.label.split(',')
					} else {
						resultArray.push(participateInfo.label)
					}
					return resultArray
				}
			},
			getClassName() {
				if (this.childNode.properties && this.childNode.properties.commentList.length > 0) {
					if (this.childNode.properties.commentList.length === 1) {
						if (this.childNode.properties.commentList[0].operateType === 'PASS') {
							return 'node-state-label-pass'
						}
						if (this.childNode.properties.commentList[0].operateType === 'REJECT') {
							return 'node-state-label-reject'
						}
						if (this.childNode.properties.commentList[0].operateType === 'JUMP') {
							return 'node-state-label-jump'
						}
						if (this.childNode.properties.commentList[0].operateType === 'BACK') {
							return 'node-state-label-back'
						}
					}
					return 'node-state-label'
				} else {
					if (this.childNode.id === this.currentActivityId) {
						return 'node-state-label-activity'
					}
				}
			}
		}
	}
</script>
<style scoped>
	.node-state-label {
		padding: 1px;
		border: 3px solid #1890ff;
		border-radius: 2px;
	}
	.node-state-label-pass {
		padding: 1px;
		border: 3px solid #52c41a;
		border-radius: 2px;
	}
	.node-state-label-reject {
		padding: 1px;
		border: 3px solid #ff4d4f;
		border-radius: 2px;
	}
	.node-state-label-jump {
		padding: 1px;
		border: 3px solid #bfbfbf;
		border-radius: 2px;
	}
	.node-state-label-back {
		padding: 1px;
		border: 3px solid #bfbfbf;
		border-radius: 2px;
	}
	.node-state-label-activity {
		padding: 1px;
		border: 3px dashed #00e97c;
		border-radius: 2px;
	}
</style>
