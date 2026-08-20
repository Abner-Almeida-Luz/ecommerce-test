  export const PRODUCTS_LIST_ALL = '/products/listAll';
  export const PRODUCTS_SEARCH = '/products/search';
  export const PRODUCTS_CREATE = '/products/create';
  export const PRODUCTS_FIND_BY_ID = (id: number) => `/products/find/${id}`;
  export const PRODUCTS_PUT_BY_ID = (id: number) => `/products/put/${id}`;
  export const PRODUCTS_DELETE_BY_ID = (id: number) => `/products/delete/${id}`;

  export const CATEGORIES_LIST_ALL = '/categories/listAll';
  export const CATEGORIES_CREATE = '/categories/create';
  export const CATEGORIES_FIND_BY_ID = (id: number) => `/categories/find/${id}`;
  export const CATEGORIES_PUT_BY_ID = (id: number) => `/categories/put/${id}`;
  export const CATEGORIES_DELETE_BY_ID = (id: number) => `/categories/delete/${id}`;

  export const CARTS_VIEW_CART = '/carts/viewCart';
  export const CARTS_ADD_ITEM = '/carts/addItem';
  export const CARTS_DELETE_ITEM = (cartItem: number) => `/carts/deleteItem/${cartItem}`;

  export const ORDERS_CHECKOUT = '/orders/checkout';
  export const ORDERS_LIST_ALL = '/orders/listAll';

  export const USERS_LOGIN = '/users/login';
  export const USERS_REGISTER = '/users/register';
  export const USERS_LIST_ALL = '/users/listAll';
  export const USERS_FIND_BY_LOGIN = (login: string) => `/users/find/${login}`;
  export const USERS_DELETE_BY_ID = (id: number) => `/users/delete/${id}`;

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