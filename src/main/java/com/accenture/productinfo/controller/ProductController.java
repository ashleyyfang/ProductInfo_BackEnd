
package com.accenture.productinfo.controller;

import com.accenture.productinfo.dto.ProductIdResponse;
import com.accenture.productinfo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ms-product-info")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    //@Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProductId/{productCode}")
    public ProductIdResponse getProductDetails(@PathVariable String productCode) {
        log.info("Received request for product code: {}", productCode);
        return productService.getProductId(productCode);
    }
}
