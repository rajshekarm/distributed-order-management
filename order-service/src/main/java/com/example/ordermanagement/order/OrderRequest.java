package com.example.ordermanagement.order;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrderRequest(
        @NotBlank String clientOrderId,
        @NotBlank @Pattern(regexp = "[A-Za-z.]{1,20}") String symbol,
        @NotNull OrderSide side,
        @NotNull OrderType type,
        @NotNull @DecimalMin(value = "0.000001") BigDecimal quantity,
        @DecimalMin(value = "0.000001") BigDecimal limitPrice) { }
