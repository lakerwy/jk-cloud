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
				<div class="node-wrap-box start-node">
					<div class="title" style="background: #576a95">
						<user-outlined class="icon" />
						<span>{{ childNode.title }}</span>
					</div>
					<div class="content">
						<span>{{ toText(childNode) }}</span>
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
			toText() {
				return '系统自动配置参与人'
			},
			getClassName() {
				if (this.childNode.id === this.currentActivityId) {
					return 'node-state-label-activity'
				} else {
					return this.childNode.properties && this.childNode.properties.commentList.length > 0 ? 'node-state-label' : ''
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
	.node-state-label-activity {
		padding: 1px;
		border: 3px dashed #00e97c;
		border-radius: 2px;
	}
</style>
