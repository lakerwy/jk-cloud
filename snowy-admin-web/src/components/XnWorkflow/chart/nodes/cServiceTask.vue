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
			<div :class="childNode.properties && childNode.properties.commentList.length > 0 ? 'node-state-label' : ''">
				<div class="node-wrap-box">
					<div class="title" style="background: #3296fa">
						<send-outlined class="icon" />
						<span>{{ childNode.title }}</span>
					</div>
					<div class="content">
						<span v-if="toText(childNode)">{{ toText(childNode) }}</span>
						<span v-else class="placeholder">未选择人员</span>
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
					const participateInfo = childNode.properties.participateInfo
					if (participateInfo.length > 0) {
						let resultArray = []
						if (participateInfo[0].label.indexOf(',') !== -1) {
							resultArray = participateInfo[0].label.split(',')
						} else {
							resultArray.push(participateInfo[0].label)
						}
						return resultArray.toString()
					} else {
						return false
					}
				} else {
					return false
				}
			}
		}
	}
</script>
<style scoped>
	.node-state-label {
		padding: 1px;
		border: 3px solid rgb(24, 144, 255);
		border-radius: 2px;
	}
</style>
