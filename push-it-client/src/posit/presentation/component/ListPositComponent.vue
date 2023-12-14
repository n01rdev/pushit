<template>
  <div>
    <div v-for="posit in posits" :key="posit.uuid">
      <h2>{{ posit.title }}</h2>
      <p>{{ posit.content }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, onUnmounted } from 'vue';
import {io, Socket} from 'socket.io-client';
import { Posit } from '../../domain/model/Posit';

export default defineComponent({
  setup() {
    const posits = ref<Posit[]>([]);
    let socket: Socket

    onMounted(() => {
      socket = io('http://localhost:8080');

      socket.on('newPost', (newPosit) => {
        posits.value.push(newPosit);
      });
    });

    onUnmounted(() => {
      socket.disconnect();
    });

    return { posits };
  }
});
</script>