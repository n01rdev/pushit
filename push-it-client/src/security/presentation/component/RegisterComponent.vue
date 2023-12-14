<template>
    <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
      Create an account
    </h1>
    <AlertComponent :message="error" :type="'danger'" v-if="error" @close="resetAlerts" />
    <AlertComponent :message="'Registered successfully'" :type="'success'" v-if="success" @close="resetAlerts" />
    <form @submit.prevent="register" class="space-y-4 md:space-y-6">
    <div>
      <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
      <input v-model="user.email" type="email" id="email" placeholder="name@company.com" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
    </div>
    <div>
      <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
      <input v-model="user.password" type="password" id="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
    </div>
    <div>
      <label for="confirmPassword" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm Password</label>
      <input v-model="confirmPassword" type="password" id="confirmPassword" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
    </div>
      <div class="flex items-start">
        <div class="flex items-center h-5">
          <input id="terms" aria-describedby="terms" type="checkbox" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800" required>
        </div>
        <div class="ml-3 text-sm">
          <label for="terms" class="font-light text-gray-500 dark:text-gray-300">I accept the <a class="font-medium text-primary-600 hover:underline dark:text-primary-500" href="#">Terms and Conditions</a></label>
        </div>
      </div>
    <button type="submit" class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Register</button>
  </form>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { User } from "../../domain/model/User.ts";
import authService from '../../application/service/authService';
import AlertComponent from '../../../ui/component/common/AlertComponent.vue';
import NProgress from 'nprogress';

export default defineComponent({
  components: {AlertComponent},
  setup() {
    const user = ref(new User('', ''));
    const confirmPassword = ref('');
    const error = ref('');
    const success = ref(false);

    const resetAlerts = () => {
      error.value = '';
    };

    const register = async () => {
      NProgress.start();
      try {
        user.value.validatePassword(confirmPassword.value);
        await authService.register(user.value);
        success.value = true;
        error.value = '';
        NProgress.done();
        window.location.href = '/login';

      } catch (err: any) {
        error.value = err.message;
        success.value = false;
        NProgress.done();
      }
    };
    return { user, confirmPassword, error, success, register, resetAlerts };
  }
});
</script>