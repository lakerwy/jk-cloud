<template>
	<start-events v-if="childNode.type === 'startEvent'" v-model="childNode" :currentActivityId="currentActivityId" />
	<start-tasks v-if="childNode.type === 'startTask'" v-model="childNode" :currentActivityId="currentActivityId" />
	<user-tasks v-if="childNode.type === 'userTask'" v-model="childNode" :currentActivityId="currentActivityId" />
	<service-tasks v-if="childNode.type === 'serviceTask'" v-model="childNode" :currentActivityId="currentActivityId" />
	<exclusive-gateways v-if="childNode.type === 'exclusiveGateway'" v-model="childNode" :currentActivityId="currentActivityId">
		<template #default="slot">
			<c-node-wrap v-if="slot.node" v-model="slot.node.childNode" :currentActivityId="currentActivityId" />
		</template>
	</exclusive-gateways>
	<parallel-gateways v-if="childNode.type === 'parallelGateway'" v-model="childNode" :currentActivityId="currentActivityId">
		<template #default="slot">
			<c-node-wrap v-if="slot.node" v-model="slot.node.childNode" :currentActivityId="currentActivityId" />
		</template>
	</parallel-gateways>
	<c-node-wrap v-if="childNode.childNode" v-model="childNode.childNode" :currentActivityId="currentActivityId" />
</template>

<script>
	import startEvents from './nodes/cStartEvent.vue'
	import startTasks from './nodes/cStartTask.vue'
	import userTasks from './nodes/cUserTask.vue'
	import exclusiveGateways from './nodes/cExclusiveGateway.vue'
	import parallelGateways from './nodes/cParallelGateway.vue'
	import serviceTasks from './nodes/cServiceTask.vue'

	export default {
		components: {
			startEvents,
			startTasks,
			userTasks,
			exclusiveGateways,
			parallelGateways,
			serviceTasks
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
			modelValue(val) {
				this.childNode = val
			},
			childNode(val) {
				this.$emit('update:modelValue', val)
			}
		},
		mounted() {
			this.childNode = this.modelValue
		}
	}
</script>
