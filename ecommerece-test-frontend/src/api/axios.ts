import axios from 'axios';

const api = axios.create({
  baseURL:
    (import.meta as ImportMeta & { env: { VITE_API_URL?: string } }).env
      .VITE_API_URL || 'http://localhost:8080',
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401 && !error.config._retry) {
      error.config._retry = true;
      try {
        const refreshToken = localStorage.getItem('refreshToken');
        const { data } = await axios.post(`${baseURL}/users/refresh`, {
          refreshToken,
        });
        localStorage.setItem('token', data.acessToken); // ⚠️ acessToken
        error.config.headers.Authorization = `Bearer ${data.acessToken}`;
        return api(error.config);
      } catch {
        localStorage.clear();
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api;
