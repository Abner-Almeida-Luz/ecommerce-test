package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.CartItems;
import com.AbnerTest.ecommerce_test.core.Carts;
import com.AbnerTest.ecommerce_test.core.Products;
import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions.ResourceNotFoundException;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartsService {
    private final UserRepository userRepository;
    private final CartsRepository cartsRepository;
    private final ProductsRepository productsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CartMapper cartMapper;

    @Transactional
    public CartResponse viewCart(String login) {
        log.info("View cart by userLogin {}", login);
        Users user = userRepository.findByActiveTrueAndLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        Carts cart = cartsRepository.findCartByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found with user id: " + user.getUserId()));
        log.info("View cart completed. userLogin={} cartId={}", login, cart.getCartId());
        return cartMapper.toCartDTO(cart);
    }

    @Transactional
    public CartResponse addItem(CartItemRequest request) {
        log.info("Adding cartItem={}", request);
        Carts cart = cartsRepository.findById(request.cartId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + request.cartId()));
        Products product = productsRepository.findById(request.productId()).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.productId()));;
        BigDecimal total = BigDecimal.valueOf(request.quantity()).multiply(product.getPrice());
        cartItemsRepository.save(new CartItems(cart, product, request.quantity(),total));
        log.info("Adding cart item completed. cartItem={} cartId={}", request, cart.getCartId());
        return cartMapper.toCartDTO(cart);
    }

    @Transactional
    public CartResponse removeItem(Long id) {
        log.info("Removing cartItem={}", id);
        CartItems cartItem = cartItemsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found with id: " + id));;
        Carts cart = cartItem.getCart();
        cart.getCartItems().removeIf(i -> i.getCartItemId().equals(id));
        cartItemsRepository.deleteById(id);
        cartsRepository.save(cart);
        log.info("Removing completed completed. cartItem={} cartId={}", id, cart.getCartId());
        return cartMapper.toCartDTO(cart);
    }
}