package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups=this.specificationService.queryGroupByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "generic",required = false)Boolean generic,
            @RequestParam(value ="search",required = false)Boolean search
                                                       ){
        List<SpecParam> params=this.specificationService.queryParms(gid,cid,generic,search);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 新增规格组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void>  saveSpecGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据gid删除分组
     * @param specGroupId
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void>  deleteSpecGroup(@PathVariable("id") Long specGroupId){
        this.specificationService.deleteSpecGroup(specGroupId);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新商品信息
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void>  updateSpecGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.updateSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增参数信息
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam){
        this.specificationService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id删除指定规格参数
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public  ResponseEntity<Void> deleteSpecParam(@PathVariable("id") Long id){
        this.specificationService.deleteSpecParam(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改指定规格参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam){
        this.specificationService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类id查询参数组和 参数
     * @param cid
     * @return
     */
    @GetMapping("group/param/{cid}")
    public  ResponseEntity<List<SpecGroup>> queryGroupWithParam(@PathVariable("cid")Long cid){
        List<SpecGroup> groups=this.specificationService.queryGroupWithParam(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

}























