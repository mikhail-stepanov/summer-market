package ru.geekbrains.routing.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.geekbrains.routing.dtos.ProductDto;

import java.util.List;

@FeignClient("ms-products")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDto findProductById(@PathVariable Long id);

    @GetMapping("/api/v1/products/ids")
    List<ProductDto> findProductsByIds(@RequestParam List<Long> ids);
}
