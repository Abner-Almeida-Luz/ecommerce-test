package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.exceptions.ErrorResponse;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;

@RestController
@RequestMapping(PRODUCTS)
@Tag(name = "Products", description = "Product management")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @Operation(summary = "Find all products", description = "Returns paginated product list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List all Products"),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(PRODUCTS_LIST_ALL)
    public ResponseEntity<Page<ProductSummaryResponse>> findAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "name") String sort) {
        return ResponseEntity.ok(productsService.findAll(page,size,sort));
    }

    @Operation(summary = "Search products by filter", description = "Returns paginated product list with filtered products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List all filtered Products"),
            @ApiResponse(responseCode = "400", description = "Invalid Validation",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(PRODUCTS_SEARCH)
    public ResponseEntity<Page<ProductSummaryResponse>> searchProductsByFilter(
            @Valid @RequestBody SearchProductRequest request) {
        return ResponseEntity.ok(
                productsService.search(request));
    }

    @Operation(summary = "Find product by id", description = "Returns product with id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @GetMapping(PRODUCTS_FIND_BY_ID)
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id){
        return ResponseEntity.ok(productsService.findById(id));
    }

    @Operation(summary = "Create products", description = "Create and returns product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid Validation",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @PostMapping(PRODUCTS_CREATE)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.create(request));
    }

    @Operation(summary = "Put product by Id", description = "Update and return product with id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid Validation",
                    content = @Content(schema = @Schema(implementation = ValidationException.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @PutMapping(PRODUCTS_PUT_BY_ID)
    public ResponseEntity<ProductResponse> putProductByid(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productsService.put(id,request));
    }

    @Operation(summary = "Delete product", description = "Deletes product with id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @DeleteMapping(PRODUCTS_DELETE_BY_ID)
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}