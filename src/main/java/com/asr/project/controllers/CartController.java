package com.asr.project.controllers;

import com.asr.project.dtos.CartDto;
import com.asr.project.models.AddItemCartRequest;
import com.asr.project.payloads.ApiResponseMessage;
import com.asr.project.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/users/{userId}")
    ResponseEntity<CartDto> addItemToCart(@PathVariable String userId,
            @RequestBody AddItemCartRequest addItemCartRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).
                body(cartService.addItemToCart(userId, addItemCartRequest));
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<CartDto> getCart(@PathVariable String userId) {

        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/items/{itemId}")
    ResponseEntity<ApiResponseMessage> removeItemToCart(@PathVariable String userId, @PathVariable String itemId) {

        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMessage response = ApiResponseMessage.builder().
                message("Item is removed from cart successfully !!").success(true).
                status(HttpStatus.OK).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {

        String responseMessage = "";
        CartDto cartDto = cartService.getCartByUser(userId);
        if(cartDto.getCartItems().isEmpty()) {
            responseMessage = "Cart is already empty !!";
        } else {
            responseMessage = "Now cart is empty !!";
            cartService.clearCart(userId);
        }
        ApiResponseMessage response = ApiResponseMessage.builder().
                message(responseMessage).success(true).
                status(HttpStatus.OK).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
