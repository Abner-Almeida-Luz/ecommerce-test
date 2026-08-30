  const API_URL = import.meta.env.VITE_API_URL;
  
  export const PRODUCTS_LIST_ALL = `${API_URL}/products/listAll`;
  export const PRODUCTS_SEARCH = `${API_URL}/products/search`;
  export const PRODUCTS_CREATE = `${API_URL}/products/create`;
  export const PRODUCTS_FIND_BY_ID = (id: number) => `${API_URL}/products/find/${id}`;
  export const PRODUCTS_PUT_BY_ID = (id: number) => `${API_URL}/products/put/${id}`;
  export const PRODUCTS_DELETE_BY_ID = (id: number) => `${API_URL}/products/delete/${id}`;

  export const CATEGORIES_LIST_ALL = `${API_URL}/categories/listAll`;
  export const CATEGORIES_CREATE = `${API_URL}/categories/create`;
  export const CATEGORIES_FIND_BY_ID = (id: number) => `${API_URL}/categories/find/${id}`;
  export const CATEGORIES_PUT_BY_ID = (id: number) => `${API_URL}/categories/put/${id}`;
  export const CATEGORIES_DELETE_BY_ID = (id: number) => `${API_URL}/categories/delete/${id}`;

  export const CARTS_VIEW_CART = `${API_URL}/carts/viewCart`;
  export const CARTS_ADD_ITEM = `${API_URL}/carts/addItem`;
  export const CARTS_DELETE_ITEM = (cartItem: number) => `${API_URL}/carts/deleteItem/${cartItem}`;

  export const ORDERS_CHECKOUT = `${API_URL}/orders/checkout`;
  export const ORDERS_LIST_ALL = `${API_URL}/orders/listAll`;

  export const USERS_LOGIN = `${API_URL}/users/login`;
  export const USERS_REGISTER = `${API_URL}/users/register`;
  export const USERS_LIST_ALL = `${API_URL}/users/listAll`;
  export const USERS_FIND_BY_LOGIN = (login: string) => `${API_URL}/users/find/${login}`;
  export const USERS_DELETE_BY_ID = (id: number) => `${API_URL}/users/delete/${id}`;

export const API_ROUTES = {
PRODUCTS_LIST_ALL,
PRODUCTS_SEARCH,
PRODUCTS_CREATE,
PRODUCTS_FIND_BY_ID,
PRODUCTS_PUT_BY_ID,
PRODUCTS_DELETE_BY_ID,
CATEGORIES_LIST_ALL,
CATEGORIES_CREATE,
CATEGORIES_FIND_BY_ID,
CATEGORIES_PUT_BY_ID,
CATEGORIES_DELETE_BY_ID,
CARTS_VIEW_CART,
CARTS_ADD_ITEM,
CARTS_DELETE_ITEM,
ORDERS_CHECKOUT,
ORDERS_LIST_ALL,
USERS_LOGIN,
USERS_REGISTER,
USERS_LIST_ALL,
USERS_FIND_BY_LOGIN,
USERS_DELETE_BY_ID,
} as const;

export type ApiRoutes = typeof API_ROUTES;