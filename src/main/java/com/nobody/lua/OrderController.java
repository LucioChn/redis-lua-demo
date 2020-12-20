package com.nobody.lua;

import com.nobody.lua.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Mr.nobody
 * @Date 2020/12/20
 * @Version 1.0
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("order")
    public Boolean order(@RequestParam String productId, @RequestParam String userId) {
        return orderService.order(productId, userId);
    }
}
