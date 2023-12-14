import axios from 'axios';
import type {InternalAxiosRequestConfig} from 'axios';
import { User } from "../../domain/model/User";

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1/security',
    withCredentials: false,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

apiClient.interceptors.request.use((config: InternalAxiosRequestConfig) => {
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default {
    async register(user: User) {
        const response = await apiClient.post('/register', user);
        console.log(response);
        localStorage.setItem('authToken', response.data.token);
        localStorage.setItem('userUuid', response.data.uuid);
    },
    async login(user: User) {
        const response = await apiClient.post('/login', user);
        localStorage.setItem('authToken', response.data.token);
        localStorage.setItem('userUuid', response.data.uuid);
    },
}