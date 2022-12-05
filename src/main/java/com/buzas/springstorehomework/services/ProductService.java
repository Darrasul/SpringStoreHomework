package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.entities.products.ProductDtoMapper;
import com.buzas.springstorehomework.entities.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        increaseViewCountByProductId(id);
        return productRepo.findById(id)
                .map(mapper::map);
    }

    public void save(ProductDto productDto) {
        try {
            productRepo.save(mapper.map(productDto));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void update(Long id, ProductDto productDto) {
        productRepo.updateProduct(id, productDto.getCurrency(),
                productDto.getDescription(), productDto.getPrice(), productDto.getTitle());
    }

    public void increaseCartAddCountByProductId(Long id) {
        productRepo.increaseCartAddCountByProductId(id);
    }

    public void increaseOrderCountByProductId(Long id, int count) {
        productRepo.increaseOrderCountByProductId(id, count);
    }

    public void increaseViewCountByProductId(Long id) {
        productRepo.increaseViewCountByProductId(id);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    public void deleteAll() {
        productRepo.deleteAll();
    }
}
