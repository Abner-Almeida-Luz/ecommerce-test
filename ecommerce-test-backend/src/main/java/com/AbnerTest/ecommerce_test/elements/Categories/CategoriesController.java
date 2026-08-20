package com.AbnerTest.ecommerce_test.elements.Categories;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;

@RestController
@RequestMapping(CATEGORIES)
@Tag(name = "Categories", description = "Category management")
@RequiredArgsConstructor
public class CategoriesController{
    private final CategoriesService categoriesService;

    @Operation(summary = "List all categories", description = "Returns all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List all Categories"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error",
                    content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @GetMapping(CATEGORIES_LIST_ALL)
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok().body(categoriesService.findAll());
    }

    @Operation(summary = "Create category", description = "Creates category on database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category Created"),
            @ApiResponse(responseCode = "400", description = "Invalid Validation",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @PostMapping(CATEGORIES_CREATE)
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesService.create(request));
    }

    @Operation(summary = "Find category by id", description = "Returns category with id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @GetMapping(CATEGORIES_FIND_BY_ID)
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(categoriesService.findById(id));
    }

    @Operation(summary = "Put category by id", description = "Update category with id as Request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @PutMapping(CATEGORIES_PUT_BY_ID)
    public ResponseEntity<CategoryResponse> putCategoryById(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoriesService.put(id, request));
    }

    @Operation(summary = "Delete category by id", description = "Deletes category with id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @DeleteMapping(CATEGORIES_DELETE_BY_ID)
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        categoriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
