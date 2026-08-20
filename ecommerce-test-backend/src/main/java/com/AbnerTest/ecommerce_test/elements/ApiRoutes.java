package com.AbnerTest.ecommerce_test.elements;

public final class ApiRoutes {
    public static final String PRODUCTS = "/products";
    public static final String PRODUCTS_LIST_ALL = "/listAll";
    public static final String PRODUCTS_SEARCH = "/search";
    public static final String PRODUCTS_CREATE = "/create";
    public static final String PRODUCTS_FIND_BY_ID = "/find/{id}";
    public static final String PRODUCTS_PUT_BY_ID = "/put/{id}";
    public static final String PRODUCTS_DELETE_BY_ID = "/delete/{id}";

    public static final String CATEGORIES = "/categories";
    public static final String CATEGORIES_LIST_ALL = "/listAll";
    public static final String CATEGORIES_CREATE = "/create";
    public static final String CATEGORIES_FIND_BY_ID = "/find/{id}";
    public static final String CATEGORIES_PUT_BY_ID = "/put/{id}";
    public static final String CATEGORIES_DELETE_BY_ID = "/delete/{id}";

    public static final String CARTS= "/carts";
    public static final String CARTS_VIEW_CART = "/viewCart";
    public static final String CARTS_ADD_ITEM = "/addItem";
    public static final String CARTS_DELETE_ITEM = "/deleteItem/{id}";

    public static final String ORDERS = "/orders";
    public static final String ORDERS_CHECKOUT = "/checkout";
    public static final String ORDERS_LIST_ALL = "/listAll";

    public static final String USERS = "/users";
    public static final String USERS_LIST_ALL = "/listAll";
    public static final String USERS_REFRESH = "/refresh";
    public static final String USERS_LOGIN = "/login";
    public static final String USERS_REGISTER = "/register";
    public static final String USERS_FIND_BY_LOGIN = "/find/{login}";
    public static final String USERS_DELETE_BY_ID = "/delete/{id}";
}