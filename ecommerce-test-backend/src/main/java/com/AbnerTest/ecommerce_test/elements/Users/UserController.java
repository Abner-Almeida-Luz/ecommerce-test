package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.exceptions.ErrorResponse;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;

@RestController
@RequestMapping(USERS)
@Tag(name = "Users", description = "User management")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "List all users", description = "Returns all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List all Users"),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(USERS_LIST_ALL)
    public ResponseEntity<List<UserResponse>> listAll() {
        return ResponseEntity.ok().body(userService.listAll());
    }

    @Operation(summary = "Login", description = "Login as user Request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.Forbidden.class)))
    })
    @PostMapping(USERS_LOGIN)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping(USERS_REFRESH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.Forbidden.class)))
    })
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(userService.refresh(request.refreshToken()));
    }

    public record RefreshRequest(@NotBlank String refreshToken) {}

    @Operation(summary = "Register", description = "Register a new user as Request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = HttpClientErrorException.Forbidden.class)))
    })
    @ApiResponse(responseCode = "201", description = "Success")
    @PostMapping(USERS_REGISTER)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @Operation(summary = "Find by login", description = "Returns user with login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @GetMapping(USERS_FIND_BY_LOGIN)
    public ResponseEntity<UserResponse> find(@PathVariable String login){
        return ResponseEntity.ok(userService.findByLogin(login));
    }
    @Operation(summary = "Delete by login", description = "Deletes user with login")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = Exceptions.ResourceNotFoundException.class)))
    })
    @DeleteMapping(USERS_DELETE_BY_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
