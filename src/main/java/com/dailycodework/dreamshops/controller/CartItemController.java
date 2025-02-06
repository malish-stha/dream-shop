package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;
import com.dailycodework.dreamshops.service.cart.ICartServiceItem;
import com.dailycodework.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartServiceItem cartServiceItem;
    private final ICartService cartService;
    private final IUserService userService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart( @RequestParam Long productId, @RequestParam Integer quantity){
        try {
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);

            cartServiceItem.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse( "Item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try {
            cartServiceItem.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse( "Item removed from cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity) {
        try {
            cartServiceItem.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
