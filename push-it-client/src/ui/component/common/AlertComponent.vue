<template>
  <fwb-alert v-if="message" :closable="true" :icon="true" :type="type" class="bg-gray-50 dark:bg-gray-700 my-2" @close="handleClose">
    {{ message }}
  </fwb-alert>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { FwbAlert } from 'flowbite-vue'

type AlertType = "info" | "danger" | "success" | "warning" | "dark";

export default defineComponent({
  components: { FwbAlert },
  props: {
    message: {
      type: String,
      required: true
    },
    type: {
      type: String as () => AlertType,
      required: true
    }
  },
  methods: {
    handleClose() {
      this.$emit('close');
    }
  }
});
</script>