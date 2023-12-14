import axios from 'axios';
import type { InternalAxiosRequestConfig } from 'axios';
import { Posit } from '../../domain/model/Posit';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    withCredentials: false,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

apiClient.interceptors.request.use((config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('authToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

const createPositService = {
    async create(posit: Posit) {
        const response = await apiClient.post('/posit', posit);
        return response.data;
    }
};

export default createPositService;