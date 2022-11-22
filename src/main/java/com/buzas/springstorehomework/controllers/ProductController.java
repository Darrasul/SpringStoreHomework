package com.buzas.springstorehomework.controllers;

import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.lang.module.FindException;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/product/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ModelAndView getAllProduct(@RequestParam(required = false) Double minimumFilter,
                                      @RequestParam(required = false) Double maximumFilter,
                                      @RequestParam(required = false, defaultValue = "1") Optional<Integer> page,
                                      @RequestParam(required = false, defaultValue = "10") Optional<Integer> size,
                                      Model model) {
        int currentPage = page.orElse(1) - 1;
        int sizeValue = size.orElse(10);
        Page<ProductDto> dtoPage = productService.findAllByFilters(minimumFilter, maximumFilter, currentPage, sizeValue);
        model.addAttribute("products", dtoPage);
        return new ModelAndView("ProductsPage");
    }

    @GetMapping("/{id}")
    public ModelAndView getProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.findById(id).orElseThrow(() ->
                new FindException("No such product")));
        return new ModelAndView("ProductPage");
    }

    @PostMapping("/update")
    @Secured("ROLE_Manager")
    public ModelAndView updateProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result) {
        checkProductForErrors(productDto, result);
        if (result.hasErrors()){
            return new ModelAndView("ProductPage");
        }
        productService.save(productDto);
        return new ModelAndView("ProductPage");
    }

    @GetMapping("/new")
    @Secured("ROLE_Manager")
    public ModelAndView getNewProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return new ModelAndView("NewProductPage");
    }

    @PostMapping("/create")
    @Secured("ROLE_Manager")
    public ModelAndView createProduct(@Valid @ModelAttribute("product") ProductDto productDto,
                                      BindingResult result, Model model) {
        checkProductForErrors(productDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("NewProductPage");
        }
        productService.save(productDto);
        model.addAttribute("product", productDto);
        return new ModelAndView("ProductPage");
    }

    @PostMapping("/delete/{id}")
    @Secured("ROLE_Manager")
    public ModelAndView deleteProduct(@PathVariable("id") long id) {
        productService.deleteById(id);
        return new ModelAndView("ProductsPage");
    }



    private void checkProductForErrors(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result) {
        if (productDto.getPrice() == null) {
            result.addError(new FieldError(productDto.getClass().toString(), "price",
                    "You must specify a price"));
        } else if (productDto.getPrice().compareTo(BigDecimal.valueOf(0.00)) == -1) {
            result.addError(new FieldError(productDto.getClass().toString(), "price",
                    "Price need to be bigger then 0.00"));
        }

        if (!productDto.getDescription().isBlank() && productDto.getDescription().length() > 250) {
            result.addError(new FieldError(productDto.getClass().toString(), "description",
                    "Description need to be shorter than 250 symbols"));
        }

        if (productDto.getTitle().isBlank()) {
            result.addError(new FieldError(productDto.getClass().toString(), "title",
                    "You must specify a title"));
        } else if (productDto.getTitle().length() < 3 || productDto.getTitle().length() > 50) {
            result.addError(new FieldError(productDto.getClass().toString(), "title",
                    "Title need to be longer than 3 symbols and shorter than 50 symbols"));
        }

        if (productDto.getCurrency().isBlank()) {
            result.addError(new FieldError(productDto.getClass().toString(), "currency",
                    "You must specify a currency"));
        } else if (productDto.getCurrency().length() > 25) {
            result.addError(new FieldError(productDto.getClass().toString(), "currency",
                    "Currency need to be shorter than 25 symbols"));
        }
    }
}
