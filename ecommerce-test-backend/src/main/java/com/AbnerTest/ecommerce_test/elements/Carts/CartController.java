package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.exceptions.ErrorResponse;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;

@RestController
@RequestMapping(CARTS)
@Tag(name = "Carts", description = "Cart management")
@RequiredArgsConstructor
public class CartController{

    private final CartsService cartsService;

    @Operation(summary = "View cart", description = "Returns Cart information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart found"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = Exceptions.InvalidTokenCredenceException.class)))
    })
    @GetMapping(CARTS_VIEW_CART)
    public ResponseEntity<CartResponse> viewCart(){
        String login = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return ResponseEntity.ok(cartsService.viewCart(login));
    }

    @Operation(summary = "Add Item", description = "Addition cart item to cart")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item created"),
            @ApiResponse(responseCode = "400", description = "Invalid Item",
                    content = @Content(schema = @Schema(implementation = Exceptions.InvalidCartItemPrice.class)))
    })
    @PostMapping(CARTS_ADD_ITEM)
    public ResponseEntity<CartResponse> addItem(@Valid @RequestBody CartItemRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartsService.addItem(request));
    }

    @Operation(summary = "Delete Item", description = "Remove cart item from cart")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removed"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @DeleteMapping(CARTS_DELETE_ITEM)
    public ResponseEntity<CartResponse> deleteItem(@PathVariable Long id){
        return ResponseEntity.ok(cartsService.removeItem(id));
    }
}
