import api from './axios';
import { CartResponse } from '../types';
import { CARTS_VIEW_CART, CARTS_ADD_ITEM, CARTS_DELETE_ITEM } from './routes';
import { CartItemRequest } from '../types';

export const viewCart = async (): Promise<CartResponse> => {
  const response = await api.get(CARTS_VIEW_CART);
  return response.data;
};

export const addItem = async (data: CartItemRequest): Promise<CartResponse> => {
  const response = await api.post(CARTS_ADD_ITEM, data);
  return response.data;
};

export const removeItem = async (itemId: number): Promise<CartResponse> => {
  const response = await api.delete(CARTS_DELETE_ITEM(itemId));
  return response.data;
};
