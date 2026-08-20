package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;

@RestController
@RequestMapping(ORDERS)
@Tag(name = "Orders", description = "Order management")
@RequiredArgsConstructor
public class OrderController {
    private final OrdersService orderService;

    @Operation(summary = "Checkout", description = "Transform and validate cart to order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(ORDERS_CHECKOUT)
    public ResponseEntity<OrderResponse> checkout() {
        return ResponseEntity.ok(orderService.checkout());
    }

    @Operation(summary = "Find all orders", description = "Returns all orders from logged user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List all orders"),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(ORDERS_LIST_ALL)
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}