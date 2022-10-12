package com.buzas.springstorehomework.entities.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductDtoMapper {
    ProductDto map(Product product);
    @Mapping(target = "id", ignore = true)
    Product map(ProductDto productDto);
}
