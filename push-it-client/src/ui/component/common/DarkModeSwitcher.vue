<template>
  <button @click="toggleDarkMode" class="text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 focus:outline-none focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 rounded-lg text-sm p-2.5">
    <img v-if="isDarkMode" class="w-5 h-5" :src="darkIcon" alt="Dark mode icon">
    <img v-else class="w-5 h-5" :src="lightIcon" alt="Light mode icon">
  </button>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';

export default defineComponent({
  setup() {
    const isDarkMode = ref(false);

    const darkIcon = '/dark-icon.svg';
    const lightIcon = '/light-icon.svg';

    onMounted(() => {
      if (localStorage.getItem('color-theme') === 'dark' || (!('color-theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
        isDarkMode.value = true;
        document.documentElement.classList.add('dark');
      } else {
        isDarkMode.value = false;
        document.documentElement.classList.remove('dark');
      }
    });

    const toggleDarkMode = () => {
      isDarkMode.value = !isDarkMode.value;
      if (isDarkMode.value) {
        document.documentElement.classList.add('dark');
        localStorage.setItem('color-theme', 'dark');
      } else {
        document.documentElement.classList.remove('dark');
        localStorage.setItem('color-theme', 'light');
      }
    };

    return { isDarkMode, toggleDarkMode, darkIcon, lightIcon };
  }
});
</script>