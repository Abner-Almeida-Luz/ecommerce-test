import api from './axios';
import { CATEGORIES_CREATE } from './routes';
import { CategoryRequest, CategoryResponse } from '../types';

export const createProduct = async (data: CategoryRequest): Promise<CategoryResponse> => {
  const response = await api.post(CATEGORIES_CREATE, data);
  return response.data;
};
