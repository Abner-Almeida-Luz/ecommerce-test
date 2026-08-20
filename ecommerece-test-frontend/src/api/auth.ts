import api from './axios';
import type { LoginRequest, LoginResponse, RegisterRequest, UserResponse } from '../types';
import { USERS_LOGIN, USERS_REGISTER} from './routes';

export const login = async (data: LoginRequest): Promise<LoginResponse> => {
  const response = await api.post(USERS_LOGIN, data);
  return response.data;
};

export const register = async (data: RegisterRequest): Promise<UserResponse> => {
  const response = await api.post(USERS_REGISTER, data);
  return response.data;
};