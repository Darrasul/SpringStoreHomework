package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.entities.products.ProductDtoMapper;
import com.buzas.springstorehomework.entities.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final ProductDtoMapper mapper;

    public Page<ProductDto> findAllByFilters(Double minimumFilter, Double maximumFilter, int page, int size) {
        return productRepo.findAllByFilters(maximumFilter, minimumFilter, PageRequest.of(page, size)).map(mapper::map);
    }

    public List<ProductDto> findAllByFiltersV2(Double minimumFilter, Double maximumFilter) {
        return productRepo.findAllByFiltersV2(minimumFilter, maximumFilter)
                .stream().map(mapper::map).collect(Collectors.toList());
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepo.findById(id)
                .map(mapper::map);
    }

    public void save(ProductDto productDto) {
        productRepo.save(mapper.map(productDto));
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    public void deleteAll() {
        productRepo.deleteAll();
    }
}
