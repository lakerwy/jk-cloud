<template>
	<div class="branch-wrap">
		<div class="branch-box-wrap">
			<div class="branch-box">
				<div v-for="(item, index) in childNode.conditionNodeList" :key="index" class="col-box">
					<div class="condition-node">
						<div class="condition-node-box">
							<user-tasks v-model="childNode.conditionNodeList[index]" :currentActivityId="currentActivityId" />
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
	import userTasks from './cUserTask.vue'
	export default {
		components: {
			addNodes,
			userTasks
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			currentActivityId: { type: String, default: () => '' }
		},
		data() {
			return {
				childNode: {},
				index: 0
			}
		},
		watch: {
			modelValue() {
				this.childNode = this.modelValue
			}
		},
		mounted() {
			this.childNode = this.modelValue
		}
	}
</script>
