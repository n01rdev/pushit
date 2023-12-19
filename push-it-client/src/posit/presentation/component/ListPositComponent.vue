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
import webstomp from 'webstomp-client';
import { Posit } from '../../domain/model/Posit';
import { IsAuthenticatedService } from "../../../security/application/service/isAuthenticatedService.ts";

export default defineComponent({
  setup() {
    const posits = ref<Posit[]>([]);
    let client: webstomp.Client;

    const isAuthenticated = new IsAuthenticatedService();

    onMounted(() => {
      if (!isAuthenticated.check()) {
        window.location.href = '/login';
        return;
      }

      const token = localStorage.getItem('authToken');
      const socket = new WebSocket(`ws://localhost:8080/websocket`);
      client = webstomp.over(socket);

      const headers = {
        'Authorization': 'Bearer ' + token
      };

      client.connect(headers, {
      }, () => {
        console.log('WebSocket connection established');
        client.subscribe('/topic/posits', (message: any) => {
          if (message.body) {
            const newPosit = JSON.parse(message.body);
            posits.value.push(newPosit);
          }
        });
      }, (error: any) => {
        console.error('WebSocket error: ', error);
      });
    });

    onUnmounted(() => {
      if (client) {
        client.disconnect(() => {
          console.log('WebSocket connection closed');
        });
      }
    });

    return { posits };
  }
});
</script>