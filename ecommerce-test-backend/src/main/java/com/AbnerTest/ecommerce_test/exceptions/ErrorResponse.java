package com.AbnerTest.ecommerce_test.exceptions;

import java.util.List;

public record ErrorResponse(
        int status, List<String> errors) { }
