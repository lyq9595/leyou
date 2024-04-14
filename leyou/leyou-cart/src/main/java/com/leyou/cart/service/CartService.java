package com.leyou.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX="user:cart:";

    @Autowired
    private GoodsClient goodsClient;

    //登录状态下的登录购物车请求
    public boolean addCart(Cart cart) {

        Integer num=cart.getNum();

        //拿到ThreadLocal线程对象获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //根据redis查询是否存在
        BoundHashOperations<String, Object, Object> hashOperations
                = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        if(hashOperations.hasKey(cart.getSkuId().toString())){
            //有则商品+1
            String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
            cart  = JsonUtils.parse(cartJson, Cart.class);//将json字符串反序列化为对象
            cart.setNum(cart.getNum() + num);
            hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
            return true;
        }else {
            //根据skuId获取商品信息
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            //没有则添加，
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(),"，")[0]);
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            //存入redis中
            hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
            return true;
        }

    }


    /**
     * 登录状态下查询redis中的数据
     * @return
     */
    public List<Cart> queryCarts() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //先判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX+userInfo.getId())){
            return null;
        }
        //获取用户购物车
        BoundHashOperations<String, Object, Object> hashOperations
                = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        //获取购物车map中的所有cart值集合
        List<Object> cartsJson = hashOperations.values();
        //购物车集合为空
        if (CollectionUtils.isEmpty(cartsJson)){
            return null;
        }
        //对象转换
        return cartsJson.stream().map(cartJson->
            JsonUtils.parse(cartJson.toString(),Cart.class)
        ).collect(Collectors.toList());

    }

    /**
     * 修改购物车商品数量
     * @param cart
     * @return
     */
    public boolean updateNumber(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //先判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PREFIX+userInfo.getId())){
            return false;
        }

        Integer num=cart.getNum();

        //获取用户购物车
        BoundHashOperations<String, Object, Object> hashOperations
                = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        cart = JsonUtils.parse(cartJson, Cart.class);
        //更新数量
        cart.setNum(num);
        hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
        return true;
    }


    /**
     * 登录状态下删除商品
     * @param skuId
     * @return
     */
    public boolean deleteCart(String skuId) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //获取用户购物车
        BoundHashOperations<String, Object, Object> hashOperations
                = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        hashOperations.delete(skuId);
        return true;
    }
}












