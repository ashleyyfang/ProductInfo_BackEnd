package com.accenture.productinfo.service.impl;

import com.accenture.productinfo.dto.ProductIdResponse;
import com.accenture.productinfo.exception.BadRequestException;
import com.accenture.productinfo.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {



    @Test
    void testGetProductByValidProductCode() {
        ProductServiceImpl service = new ProductServiceImpl() {

            Map<String, String> readFile() {
                return Map.of(
                        "123451", "123456",
                        "123456", "1234567"
                );
            }
        };

        ProductIdResponse response = service.getProductId("123456");
        assertNotNull(response);
        assertEquals("1234567", response.getProductId());

        ProductIdResponse response2 = service.getProductId("123451");
        assertNotNull(response2);
        assertEquals("123456", response2.getProductId());
    }

    @Test
    void testGetProductsByInvalidProductCode() {
        ProductServiceImpl service = new ProductServiceImpl() {

            Map<String, String> readFile() {
                return Map.of(
                        "123451", "123456",
                        "123456", "1234567"
                );
            }
        };

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.getProductId("12345")
        );

        assertEquals(400, exception.getErrorId());
        assertEquals("Product code 12345 not found", exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }

    //    @Test
//    void testGetProductByValidProductCode() {
//        ProductServiceImpl service = new ProductServiceImpl() {
////            @
//            protected Map<String, String> readFile() {
//                return Map.of("12345", "12345");
//            }
//        };
//
//        ProductIdResponse response = service.getProductId("12345");
//        assertNotNull(response);
//        assertEquals("12345", response.getProductId());
//    }

//    @Test
//    void testGetProductsByInvalidProductCode() {
//        ProductServiceImpl service = new ProductServiceImpl() {
////            @
//            protected Map<String, String> readFile() {
//                return Map.of("12345", "12345");
//            }
//        };
//
//        BadRequestException exception = assertThrows(
//                BadRequestException.class,
//                () -> service.getProductId("1245")
//        );
//
//        assertEquals(400, exception.getErrorId());
//        assertEquals("Product code 1245 not found", exception.getErrorMessage());
//        assertTrue(exception.getErrorDetails().isEmpty());
//    }



}