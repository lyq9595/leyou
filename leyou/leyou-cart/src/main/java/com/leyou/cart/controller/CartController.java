package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    //登录状态下的登录购物车请求
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        boolean flag =this.cartService.addCart(cart);
        if (!flag){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 登录状态下查询redis中的数据
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts(){
        List<Cart> carts=this.cartService.queryCarts();
        if (CollectionUtils.isEmpty(carts)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改购物车商品数量
     * @param cart
     * @return
     */
    @PutMapping
    public ResponseEntity<Void>  updateNumber(@RequestBody Cart cart){
        boolean flag=this.cartService.updateNumber(cart);
        if (!flag){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 登录状态下删除商品
     * @param skuId
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void>  deleteCart(@PathVariable("skuId") String skuId){
        boolean flag=this.cartService.deleteCart(skuId);
        if (!flag){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();

    }

}








