import api from './axios';
import type { OrderResponse } from '../../../../frontend/src/types';
import { ORDERS_CHECKOUT, ORDERS_LIST_ALL } from './routes';

export const checkout = async (): Promise<OrderResponse> => {
  const response = await api.post(ORDERS_CHECKOUT);
  return response.data;
};

export const listOrders = async (): Promise<OrderResponse[]> => {
  const response = await api.get(ORDERS_LIST_ALL);
  return response.data;
};
