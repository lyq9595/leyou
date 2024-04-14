package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    //添加品牌类别
    @Insert("insert into tb_category_brand (category_id,brand_id) values(#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    //修改品牌信息
    @Update("update tb_brand set name=#{name},letter=#{letter},image=#{image} where id=#{id} ")
    void editCategoryAndBrand(@Param("id")Long id, @Param("name")String name,@Param("letter")Character letter, @Param("image")String image);

    //删除品牌分类关联信息
    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteCategoryAndBrand( Long id);

    //删除品牌
    @Delete("delete from tb_brand where id=#{id}")
    void deleteBrand(Long bid);

    // 根据分类查询品牌
    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b ON a.id=b.brand_id WHERE b.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}












