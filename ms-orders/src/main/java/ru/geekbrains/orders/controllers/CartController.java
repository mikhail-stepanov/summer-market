package ru.geekbrains.orders.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.core.interfaces.ITokenService;
import ru.geekbrains.core.models.UserInfo;
import ru.geekbrains.orders.services.CartService;
import ru.geekbrains.routing.dtos.CartDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ITokenService tokenService;

    @PostMapping
    public UUID createNewCart() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            return cartService.getCartForUser(null, null);
        }
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartService.getCartForUser(userInfo.getUserId(), null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable String uuid) {
        return cartService.findById(UUID.fromString(uuid));
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam String uuid, @RequestParam(name = "product_id") Long productId) {
        cartService.addToCart(UUID.fromString(uuid), productId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam String uuid) {
        cartService.clearCart(UUID.fromString(uuid));
    }
}
