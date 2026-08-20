package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.*;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartsServiceTest {


    @Mock UserRepository userRepository;
    @Mock CartsRepository cartsRepository;
    @Mock ProductsRepository productsRepository;
    @Mock CartItemsRepository cartItemsRepository;
    @Mock CartMapper cartMapper;
    @InjectMocks CartsService cartsService;



    @Test
    @DisplayName("Should view cart sucessfully when user exists")
    void viewCart_whenUserExists_returnCartResponse() {
        Users user = new Users("joao","joao@gmail.com","senha123", UserRole.ADMIN);
        Carts cart = new Carts(user);
        CartResponse expected = new CartResponse(cart.getCartId(),user.getUserId(), List.of(),cart.getCreatedAt());
        String request = "joao@gmail.com";

        when(userRepository.findByActiveTrueAndLogin(request)).thenReturn(Optional.of(user));
        when(cartsRepository.findCartByUserId(user.getUserId())).thenReturn(Optional.of(cart));
        when(cartMapper.toCartDTO(cart)).thenReturn(expected);

        CartResponse result = cartsService.viewCart(request);

        assertThat(result).isEqualTo(expected);

        verify(userRepository).findByActiveTrueAndLogin(request);
    }

    @Test
    @DisplayName("Should view cart sucessfully when user exists")
    void viewCart_whenUserNotFound_throwsException() {
        when(userRepository.findByActiveTrueAndLogin("ghost@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartsService.viewCart("ghost@gmail.com"))
                .isInstanceOf(Exceptions.ResourceNotFoundException.class)
                .hasMessageContaining("ghost@gmail.com");
    }

    @Test
    @DisplayName("Should add item and return updated cart")
    void addItem_whenCartAndProductExists_returnCartResponse() {
        Users user = new Users("joao","joao@gmail.com","senha123", UserRole.ADMIN);
        Carts cart = new Carts(user);
        Categories category = new Categories("Vegetables","Refresh vegetables");
        Products product = new Products(category,"Potato","French Potato",new BigDecimal(100),1,"potato.jpg");
        CartItemRequest request = new CartItemRequest(cart.getCartId(),product.getProductId(),1);
        CartResponse expected = new CartResponse(cart.getCartId(),user.getUserId(),List.of(),cart.getCreatedAt());

        when(cartsRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(productsRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(cartMapper.toCartDTO(cart)).thenReturn(expected);

        CartResponse result = cartsService.addItem(request);

        assertThat(result).isEqualTo(expected);
        verify(cartItemsRepository).save(any(CartItems.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product doesn't exist")
    void addItem_whenProductNotFound_throwsException() {
        Users user = new Users("joao","joao@gmail.com","senha123", UserRole.ADMIN);
        Carts cart = new Carts(user);
        CartItemRequest request = new CartItemRequest(cart.getCartId(),99L,1);

        when(cartsRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(productsRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartsService.addItem(request))
                .isInstanceOf(Exceptions.ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Should remove item and return updated cart")
    void removeItem_whenItemExists_returnCartResponse() {
        Users user = new Users("joao","joao@gmail.com","senha123", UserRole.ADMIN);
        Carts cart = new Carts(user);
        Categories category = new Categories("Vegetables","Refresh vegetables");
        Products product = new Products(category,"Potato","French Potato",new BigDecimal(100),1,"potato.jpg");
        CartItems cartItem = new CartItems(cart,product,1,product.getPrice().multiply(new BigDecimal(1)));

        Long request = cartItem.getCartItemId();
        cart.getCartItems().removeIf(i -> i.getCartItemId().equals(request));
        CartResponse expected = new CartResponse(cart.getCartId(),user.getUserId(),List.of(),cart.getCreatedAt());

        when(cartItemsRepository.findById(cartItem.getCartItemId())).thenReturn(Optional.of(cartItem));
        when(cartsRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toCartDTO(cart)).thenReturn(expected);

        CartResponse result = cartsService.removeItem(request);

        assertThat(result).isEqualTo(expected);
        verify(cartItemsRepository).deleteById(cartItem.getCartItemId());
    }
}