package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.LNRepository;
import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.products.Product;
import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.entities.products.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LNService {
    private final LNRepository lnRepo;
    private final ProductDtoMapper mapper;

    public List<LineItem> showAll() {
        return lnRepo.findAll();
    }

    public List<LineItem> showAllByOrderId(Long id) {
        return lnRepo.showAllByOrderId(id);
    }

    public LineItem findById(Long id) {
        return lnRepo.findById(id).orElseThrow(() -> new FindException("No such line item with id:" + id));
    }

    public LineItem createLN(ProductDto productDto) {
        LineItem item = new LineItem(mapper.map(productDto));
        lnRepo.saveAndFlush(item);
        return item;
    }

    public LineItem createLN(Product product) {
        LineItem item = new LineItem(product);
        lnRepo.saveAndFlush(item);
        return item;
    }

    public void deleteById(Long id) {
        lnRepo.deleteById(id);
    }
}
