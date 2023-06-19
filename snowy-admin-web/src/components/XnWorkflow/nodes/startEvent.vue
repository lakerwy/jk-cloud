<template>
	<div class="node-wrap" />
</template>

<script>
	export default {
		props: {
			modelValue: { type: Object, default: () => {} }
		},
		data() {
			return {
				childNode: {},
				// drawer: false,
				isEditTitle: false,
				form: {}
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
			show() {
				this.form = JSON.parse(JSON.stringify(this.childNode))
				this.isEditTitle = false
				this.drawer = true
			},
			// editTitle() {
			// 	this.isEditTitle = true;
			// 	this.$nextTick(() => {
			// 		this.$refs.nodeTitle.focus();
			// 	});
			// },
			// saveTitle() {
			// 	this.isEditTitle = false;
			// },
			save() {
				this.form.id = this.$TOOL.snowyUuid()
				this.$emit('update:modelValue', this.form)
				this.drawer = false
			}
		}
	}
</script>
