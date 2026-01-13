package com.callmextrm.product_service.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductCreateDTO(@NotNull @Valid Product product,
                               @NotNull @Min(0) Integer initialQuantity) {
}
