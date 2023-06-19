<template>
	<div class="workflow-design">
		<div class="workflow-design-btns">
			<a-space>
				<a-tooltip>
					<template #title>放大</template>
					<a-button @click="handleZoom(true)" :disabled="zoom > 2">
						<template #icon><plus-outlined /></template>
					</a-button>
				</a-tooltip>
				<a-tooltip>
					<template #title>缩小</template>
					<a-button @click="handleZoom(false)" :disabled="zoom < 1">
						<template #icon><minus-outlined /></template>
					</a-button>
				</a-tooltip>
			</a-space>
		</div>
		<div id="div1">
			<div class="box-scale" :style="style">
				<node-wrap-chart v-if="childNode" v-model="childNode.childNode" :currentActivityId="currentActivityId" />
				<div class="end-node">
					<div class="end-node-circle"></div>
					<div class="end-node-text">流程结束</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import nodeWrapChart from '@/components/XnWorkflow/chart/cNodeWrap.vue'
	import FullscreenPreviewHelper from './zoom_helper'

	const FullScreenRightSpace = 20
	export default {
		components: {
			nodeWrapChart
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			currentActivityId: { type: String, default: () => '' }
		},
		data() {
			return {
				childNode: this.modelValue,
				style: {},
				zoom: 1
			}
		},
		computed: {},
		watch: {
			modelValue(val) {
				this.childNode = val
			},
			childNode(val) {
				this.$emit('update:modelValue', val)
			}
		},
		methods: {
			handleGetStyle(zoom) {
				return FullscreenPreviewHelper.getZoomStyles(zoom, 1, 0, FullScreenRightSpace)
			},
			handleZoom(zoomIn) {
				const zoom = this.zoom
				const zoomData = FullscreenPreviewHelper.getZoomData(zoomIn, zoom)
				const style = this.handleGetStyle(zoomData.zoom)
				this.style = style
				this.zoom = zoomData.zoom
			}
		}
	}
</script>

<style lang="less">
	@import '../flowIndex.less';
	.workflow-design-btns {
		width: 100px;
	}
</style>
