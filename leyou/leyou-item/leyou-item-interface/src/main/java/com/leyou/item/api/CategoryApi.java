package com.leyou.item.api;


import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("category")
public interface CategoryApi {


    /**
     * 根据id数组查询品牌名称
     * @param ids
     * @return
     */
    @GetMapping
    public  List<String> queryNameByIds(@RequestParam("ids")List<Long> ids);


}















