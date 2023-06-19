<template>
	<div class="node-wrap">
		<div class="node-wrap-box" @click="show">
			<div class="title" style="background: #3296fa">
				<send-outlined class="icon" />
				<span>{{ childNode.title }}</span>
				<close-outlined class="close" @click.stop="delNode()" />
			</div>
			<div class="content">
				<span v-if="toText(childNode)">{{ toText(childNode) }}</span>
				<span v-else class="placeholder">请选择人员</span>
			</div>
		</div>
		<add-node v-model="childNode.childNode"></add-node>
		<xn-form-container
			v-model:visible="drawer"
			:destroy-on-close="true"
			:width="700"
			:body-style="{ 'padding-top': '0px' }"
		>
			<template #title>
				<div class="node-wrap-drawer__title">
					<label v-if="!isEditTitle" @click="editTitle"
						>{{ form.title }}<edit-outlined class="node-wrap-drawer-title-edit" />
					</label>
					<a-input
						v-if="isEditTitle"
						ref="nodeTitle"
						v-model:value="form.title"
						allow-clear
						@blur="saveTitle"
						@keyup.enter="saveTitle"
					/>
				</div>
			</template>
			<a-layout-content>
				<a-tabs v-model:activeKey="activeKey">
					<a-tab-pane key="1" tab="抄送配置" force-render>
						<a-form layout="vertical">
							<div v-show="nodeLegal" style="margin-bottom: 10px">
								<a-alert message="请选择抄送人员！" type="error" />
							</div>
							<a-form-item label="选择要抄送的人员">
								<a-button type="primary" size="small" round @click="selectHandle(form.properties.participateInfo)">
									<plus-outlined />
									选择人员
								</a-button>
								<p />
								<prop-tag :tag-list="getTagList('USER')" />
							</a-form-item>
						</a-form>
					</a-tab-pane>
					<a-tab-pane key="2" tab="执行监听" force-render>
						<prop-listener-info
							ref="propExecutionListenerInfoRef"
							:listener-value="form.properties.executionListenerInfo"
							:default-listener-list="executionListenerInfo"
							:listener-value-array="executionListenerArray"
						/>
					</a-tab-pane>
				</a-tabs>
			</a-layout-content>
			<template #footer>
				<a-button type="primary" style="margin-right: 8px" @click="save">保存</a-button>
				<a-button @click="drawer = false">取消</a-button>
			</template>
		</xn-form-container>
		<user-selector-plus
			ref="userselectorPlus"
			page-url="/api/flwapp/flw/model/userSelector"
			org-url="/api/flwapp/flw/model/orgTreeSelector"
			:data-is-converter-flw="true"
			@onBack="userCallBack"
		/>
	</div>
</template>

<script>
	import addNode from './addNode.vue'
	import userSelectorPlus from '@/components/Selector/userSelectorPlus.vue'
	import propTag from './prop/propTag.vue'
	import { cloneDeep } from 'lodash-es'
	import config from '@/components/XnWorkflow/nodes/config/config'
	import PropListenerInfo from './prop/propListenerInfo.vue'

	export default {
		components: {
			addNode,
			userSelectorPlus,
			propTag,
			PropListenerInfo
		},
		props: {
			modelValue: { type: Object, default: () => {} },
			executionListenerArray: { type: Array, default: () => [] }
		},
		data() {
			return {
				childNode: {},
				drawer: false,
				isEditTitle: false,
				activeKey: '1',
				form: {},
				nodeLegal: false,
				executionListenerInfo: cloneDeep(config.listener.serviceTaskExecutionListenerInfo)
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
				this.form = {}
				this.form = cloneDeep(this.childNode)
				this.drawer = true
				if (this.form.properties.participateInfo[0] === undefined) {
					this.nodeLegal = true
				}
			},
			editTitle() {
				this.isEditTitle = true
				this.$nextTick(() => {
					this.$refs.nodeTitle.focus()
				})
			},
			saveTitle() {
				this.isEditTitle = false
			},
			save() {
				if (!this.nodeLegal) {
					this.form.properties.executionListenerInfo = this.$refs.propExecutionListenerInfoRef.selectedListenerList()
					this.form.dataLegal = true
					this.$emit('update:modelValue', this.form)
					this.drawer = false
				}
			},
			delNode() {
				this.$emit('update:modelValue', this.childNode.childNode)
			},
			selectHandle(data) {
				this.$refs.userselectorPlus.showUserPlusModal(data)
			},
			userCallBack(value) {
				if (value.label === '') {
					this.nodeLegal = true
				} else {
					this.nodeLegal = false
				}
				this.form.properties.participateInfo[0] = value
			},
			// 获取tag标签的内容
			getTagList(type) {
				const participateInfo = this.form.properties.participateInfo
				if (participateInfo.length > 0) {
					const obj = participateInfo.find((item) => item.key === type)
					if (obj.label === '') {
						return undefined
					} else {
						return obj
					}
				} else {
					return undefined
				}
			},
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
						// return '未选择抄送人';
						return false
					}
				} else {
					return false
				}
			}
		}
	}
</script>
