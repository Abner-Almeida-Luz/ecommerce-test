import api from './axios';
import {
  PRODUCTS_LIST_ALL,
  PRODUCTS_CREATE,
  PRODUCTS_FIND_BY_ID,
} from './routes';
import type { Page, ProductSummaryResponse, ProductResponse } from '../types';
import { ProductRequest, SearchProductRequest } from '../types';


export const listProducts = async (data:SearchProductRequest): Promise<Page<ProductSummaryResponse>> => {
  const response = await api.get(PRODUCTS_LIST_ALL, {
    params: data,
  });
  return response.data;
};

export const getProduct = async (id: number): Promise<ProductResponse> => {
  const response = await api.get(PRODUCTS_FIND_BY_ID(id));
  return response.data;
};

export const createProduct = async (data: ProductRequest): Promise<ProductResponse> => {
  const response = await api.post(PRODUCTS_CREATE, data);
  return response.data;
};
