package com.buzas.springstorehomework.web.services;

import com.buzas.springstorehomework.entities.products.Product;
import com.buzas.springstorehomework.entities.products.ProductRepository;
import com.buzas.springstorehomework.entities.products.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebService {
    private final ProductRepository productRepo;

    private static final Function<Product, Products> productToSoap = product -> {
        Products p = new Products();
        p.setId(product.getId());
        p.setTitle(product.getTitle());
        p.setPrice(product.getPrice());
        p.setCurrency(product.getCurrency());
        return p;
    };

    public List<Products> findAll() {
        return productRepo.findAllForWeb().stream().map(productToSoap).collect(Collectors.toUnmodifiableList());
    }

}
