//Responses

export interface UserResponse {
  userId:number;
  username:string;
  login:string;
  role:string;
}

export interface LoginResponse {
  token:string;
}

export interface CategoryResponse {
  categoryId:number;
  name:string;
  description:string;
}

export interface Page<T> {
  content:T[];
  totalPages:number;
  totalElements:number;
  number:number;
  size:number;
  first:boolean;
  last:boolean;
}

export interface ProductResponse {
  productId:number;
  categoryId:number;
  categoryName:string;
  name:string;
  description:string;
  price:number;
  stock:number;
  imageUrl:string;
  createdAt:string;
}

export interface ProductSummaryResponse {
  productId:number;
  name:string;
  price:number;
  stock:number;
  imageUrl:string;
}


export interface CartItemResponse {
  cartItemId:number;
  cartId:number;
  productId:number;
  productName:string;
  quantity:number;
  total:string;
}

export interface CartResponse {
  cartId:number;
  userId:number;
  cartItems:CartItemResponse[];
}

export interface OrderItemResponse {
  orderItemId:number;
  productId:number;
  productName:string;
  quantity:number;
  total:number;
}

export interface OrderResponse {
  orderId:number;
  userId:number;
  status:string;
  total:number;
  createdAt:string;
  items:OrderItemResponse[];
}

//Requests

export type UserRole = 'ADMIN' | 'USER';

export interface RegisterRequest{
  username:string,
  login:string,
  password:string,
  role:UserRole
}

export interface LoginRequest{
  login:string,
  password:string
}

export interface ProductRequest{
  categoryId:number,
  name:string,
  description:string,
  price:number,
  stock:number,
  imageUrl:string
}

export interface SearchProductRequest{
  name:string,
  categoryId:number,
  minPrice:number,
  maxPrice:number,
  page:number,
  size:number
}

/*export interface OrderItemRequest{
  orderId:number,
  productId:number,
  quantity:number
}*/

export interface OrderRequest{
  userId:number
}

export interface CategoryRequest{
  name:string,
  description:string
}

export interface CartItemRequest{
  cartId:number,
  productId:number,
  quantity:number
}