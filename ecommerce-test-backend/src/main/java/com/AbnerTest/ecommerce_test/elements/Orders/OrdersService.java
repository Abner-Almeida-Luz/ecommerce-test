package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.*;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions.ResourceNotFoundException;
import com.AbnerTest.ecommerce_test.elements.Carts.CartsRepository;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final CartsRepository cartsRepository;
    private final UserRepository usersRepository;
    private final ProductsRepository productsRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse checkout() {
        log.info("Checkout started.");
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Users user = usersRepository.findByActiveTrueAndLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        Carts cart = cartsRepository.findCartByUserId(user.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cart not found with user id: " + user.getUserId()));

            if (cart.getCartItems().isEmpty()) {
                throw new Exceptions.InvalidCartItemPrice("Cart is empty. cartId=" + cart.getCartId());
            }

            Orders order = new Orders(cart.getUser(), OrderStatus.PENDING, BigDecimal.ZERO);
            BigDecimal total = BigDecimal.ZERO;

            for (CartItems item : cart.getCartItems()) {
                BigDecimal expectedTotal = item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

                if (expectedTotal.compareTo(item.getTotal()) != 0) {
                    throw new Exceptions.InvalidCartItemPrice(
                            "Invalid price for item: " + item.getProduct().getName());
                }

                int rows = productsRepository.decreaseStock(
                        item.getProduct().getProductId(), item.getQuantity());

                if (rows == 0) {
                    throw new Exceptions.OutOfStockException(item.getProduct().getName());
                }

                total = total.add(item.getTotal());
                order.getOrderItems().add(
                        new OrderItems(order, item.getProduct(),
                                item.getQuantity(), item.getTotal()));
            }

            order.setTotal(total);
            order.setStatus(OrderStatus.PAID);
            ordersRepository.save(order);

            cart.getCartItems().clear();
            cartsRepository.save(cart);

            log.info("Checkout completed. cartId={} orderId={} total={}",
                    cart.getCartId(), order.getOrderId(), total);

            return orderMapper.toOrderDTO(order);
    }

    @Transactional
    public List<OrderResponse> findAll() {
        log.info("Finding all orders from userLogin={}", SecurityContextHolder.getContext().getAuthentication().getName());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByActiveTrueAndLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        return ordersRepository.findByUserLoginWithItems(user.getLogin()).stream().map(orderMapper::toOrderDTO).toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting order with id={}", id);
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setActive(false);
        order.setDeletedAt(LocalDateTime.now());
        ordersRepository.save(order);
        log.info("Deleting completed. orderId={}", order.getOrderId());
    }
}