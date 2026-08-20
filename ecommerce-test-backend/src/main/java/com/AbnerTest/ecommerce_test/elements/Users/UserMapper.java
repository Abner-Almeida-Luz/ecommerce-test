package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDTO(Users user);
    Users toEntity(RegisterRequest dto);
}
