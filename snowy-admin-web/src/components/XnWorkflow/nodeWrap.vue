<template>
	<start-event v-if="childNode.type === 'startEvent'" v-model="childNode" />
	<start-task
		v-if="childNode.type === 'startTask'"
		v-model="childNode"
		:formFieldListValue="formFieldListValue"
		:recordData="recordData"
		:processConfigInfo="processConfigInfo"
		:execution-listener-array="executionListenerArray"
		:task-listener-array="taskListenerArray"
	/>
	<user-task
		v-if="childNode.type === 'userTask'"
		v-model="childNode"
		:formFieldListValue="formFieldListValue"
		:recordData="recordData"
		:processConfigInfo="processConfigInfo"
		:execution-listener-array="executionListenerArray"
		:task-listener-array="taskListenerArray"
	/>
	<service-task
		v-if="childNode.type === 'serviceTask'"
		v-model="childNode"
		:execution-listener-array="executionListenerArray"
	/>
	<exclusive-gateway
		v-if="childNode.type === 'exclusiveGateway'"
		v-model="childNode"
		:form-field-list-value="formFieldListValue"
		:record-data="recordData"
		:execution-listener-array="executionListenerArray"
		:task-listener-array="taskListenerArray"
	>
		<template #default="slot">
			<node-wrap
				v-if="slot.node"
				v-model="slot.node.childNode"
				:formFieldListValue="formFieldListValue"
				:recordData="recordData"
				:processConfigInfo="processConfigInfo"
				:execution-listener-array="executionListenerArray"
				:task-listener-array="taskListenerArray"
			/>
		</template>
	</exclusive-gateway>
	<parallel-gateway
		v-if="childNode.type === 'parallelGateway'"
		v-model="childNode"
		:formFieldListValue="formFieldListValue"
		:recordData="recordData"
		:processConfigInfo="processConfigInfo"
		:execution-listener-array="executionListenerArray"
		:task-listener-array="taskListenerArray"
	>
		<template #default="slot">
			<node-wrap
				v-if="slot.node"
				v-model="slot.node.childNode"
				:formFieldListValue="formFieldListValue"
				:recordData="recordData"
				:processConfigInfo="processConfigInfo"
				:execution-listener-array="executionListenerArray"
				:task-listener-array="taskListenerArray"
			/>
		</template>
	</parallel-gateway>
	<node-wrap
		v-if="childNode.childNode"
		v-model="childNode.childNode"
		:formFieldListValue="formFieldListValue"
		:recordData="recordData"
		:processConfigInfo="processConfigInfo"
		:execution-listener-array="executionListenerArray"
		:task-listener-array="taskListenerArray"
	/>
</template>

<script>
	import startEvent from './nodes/startEvent.vue'
	import startTask from './nodes/startTask.vue'
	import userTask from './nodes/userTask.vue'
	import exclusiveGateway from './nodes/exclusiveGateway.vue'
	import parallelGateway from './nodes/parallelGateway.vue'
	import serviceTask from './nodes/serviceTask.vue'

	export default {
		components: {
			startEvent,
			startTask,
			userTask,
			exclusiveGateway,
			parallelGateway,
			serviceTask
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
