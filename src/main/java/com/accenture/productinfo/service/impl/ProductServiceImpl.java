
package com.accenture.productinfo.service.impl;

import com.accenture.productinfo.dto.ProductIdResponse;
import com.accenture.productinfo.exception.BadRequestException;
import com.accenture.productinfo.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private Map <String, String> readFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream("product.json");
        if (is == null) {//if file not found
            throw new IOException("product.json not found in classpath");
        }
        return mapper.readValue(is, new TypeReference<Map<String, String>>() {});//Parses the JSON file into a Map<String, String>
    }



    @Override
    public ProductIdResponse getProductId(String productCode) {
        try {
            Map<String, String> productMap = readFile();
            if (!productMap.containsKey(productCode)) {
                log.warn("Product code {} not found in product.json", productCode);
                throw new BadRequestException(
                        400,//HTTP status
                        "Product code " + productCode + " not found",
                        Collections.emptyMap()
                );
            }

            String productId = productMap.get(productCode);
            log.info("Product code {} found. Returning product ID: {}", productCode, productId);
            return new ProductIdResponse(productId);

        } catch (IOException e) {
            log.error("Unable to read product.json", e);
            throw new BadRequestException(
                    500,
                    "Unable to read JSON file",
                    Collections.emptyMap()
            );
        }
    }
    //    @Override
//    public ProductIdResponse getProductId(String productCode) {
//        try {
//            Map<String, String> productMap = readFile();
//            if (!productMap.containsKey(productCode)) {
//                throw new BadRequestException(
//                        400,
//                        "Product code " + productCode + " not found",
//                        Collections.emptyMap()
//                );
//            }
//            return new ProductIdResponse(productMap.get(productCode));
//        } catch (IOException e) {
//            log.error("Unable to read product.json", e);
//            throw new BadRequestException(
//                    500,
//                    "Unable to read JSON file",
//                    Collections.emptyMap()
//            );
//        }
//    }

}
