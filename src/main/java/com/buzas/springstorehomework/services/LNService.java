package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.LNRepository;
import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.products.Product;
import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.entities.products.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    public List<LineItem> showAllByCartId(Long id) {
        return lnRepo.showAllByCartId(id);
    }

    public BigDecimal showTotalCostByCartId(Long id) {
        List<LineItem> lineItems = lnRepo.showAllByCartId(id);
        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (LineItem lineItem : lineItems) {
            totalCost = totalCost.add(lineItem.getPrice());
        }
        return totalCost;
    }

    public Set<LineItem> showSetFromCartById(Long id) {
        return lnRepo.showSetByCartId(id);
    }

    public Set<LineItem> showSetFromOrderById(Long id) {
        return lnRepo.showSetByOrderId(id);
    }

    public LineItem findById(Long id) {
        return lnRepo.findById(id).orElseThrow(() -> new FindException("No such line item with id:" + id));
    }

    public boolean checkRightItem(ProductDto productDto) {
        return lnRepo.findRightItem(productDto.getTitle(), productDto.getPrice(), productDto.getCurrency()).isPresent();
    }

    public LineItem findRightItem(ProductDto productDto) {
        return lnRepo.findRightItem(productDto.getTitle(), productDto.getPrice(), productDto.getCurrency())
                .orElseThrow(() -> new FindException("No such item with parameters:" + productDto));
    }

    public LineItem createLN(ProductDto productDto) {
        LineItem item = new LineItem(mapper.map(productDto));
        lnRepo.addLN(item.getCurrency(), item.getPrice(), item.getTitle(), productDto.getId());
        return item;
    }

    public LineItem createLN(Product product) {
        LineItem item = new LineItem(product);
        lnRepo.addLN(item.getCurrency(), item.getPrice(), item.getTitle(), product.getId());
        return item;
    }

    public void deleteById(Long id) {
        lnRepo.deleteById(id);
    }
}
