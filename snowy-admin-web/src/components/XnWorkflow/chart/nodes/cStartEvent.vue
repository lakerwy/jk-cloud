<template>
	<div class="node-wrap"></div>
</template>

<script>
	export default {
		props: {
			modelValue: { type: Object, default: () => {} }
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
		}
	}
</script>
