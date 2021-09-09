package ru.geekbrains.orders.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.core.interfaces.ITokenService;
import ru.geekbrains.core.models.UserInfo;
import ru.geekbrains.orders.models.Order;
import ru.geekbrains.orders.services.CartService;
import ru.geekbrains.orders.services.OrderService;
import ru.geekbrains.routing.dtos.OrderDto;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CartService cartService;

    private final ITokenService tokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(@RequestParam UUID cartUuid, @RequestParam String address) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderDto orderDto = orderService.createFromUserCart(userInfo.getUserId(), cartUuid, address);
        cartService.clearCart(cartUuid);
        return orderDto;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.findAllOrdersByUserId(userInfo.getUserId());
    }
}
