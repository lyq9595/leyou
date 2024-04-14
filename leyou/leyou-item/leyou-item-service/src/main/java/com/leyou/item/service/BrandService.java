package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

  @Autowired
  private BrandMapper brandMapper;

  /**
   * 根据查询条件分页并排序查询品牌信息
   *
   * @param key
   * @param page
   * @param rows
   * @param sortBy
   * @param desc
   * @return
   */
  public PageResult<Brand> queryBrandsByPage(
      String key, Integer page, Integer rows, String sortBy, Boolean desc) {
    // 初始化example对象
    Example example = new Example(Brand.class);
    Example.Criteria criteria = example.createCriteria();

    // 根据name模糊查询，或者根据首字母查询
    if (StringUtils.isNotBlank(key)) {
      criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
    }

    // 添加分页条件
    PageHelper.startPage(page, rows);

    // 添加排序条件
    if (StringUtils.isNotBlank(sortBy)) {
      example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
    }

    List<Brand> brands = this.brandMapper.selectByExample(example);
    // 包装成pageInfo
    PageInfo<Brand> pageInfo = new PageInfo<>(brands);
    // 包装成分页结果集返回
    return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
  }

  /**
   * 新增品牌
   *
   * @param brand
   * @param cids
   */
  @Transactional
  public void saveBrand(Brand brand, List<Long> cids) {
    // 先新增brand
    this.brandMapper.insertSelective(brand);

    // 在新增中间表
    cids.forEach(
        cid -> {
          this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
  }

  /**
   * 修改品牌
   *
   * @param brand
   * @param cids
   */
  @Transactional
  public void editBrand(Brand brand, List<Long> cids) {
    Long id = brand.getId();
    String name = brand.getName();
    Character letter = brand.getLetter();
    String image = brand.getImage();
    // System.out.println(id+" "+name+" "+letter+" ");
    // System.out.println(image+" ");
    // 先修改brand
    this.brandMapper.editCategoryAndBrand(id, name, letter, image);
    // 先删除原有分类
    this.brandMapper.deleteCategoryAndBrand(id);
    // 在重新新增分类
    cids.forEach(
        cid -> {
          this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
  }

  @Transactional
  public void deleteCategoryAndBrand(Long bid) {
    this.brandMapper.deleteCategoryAndBrand(bid);
  }

  @Transactional
  public void deleteBrand(Long bid) {
    this.brandMapper.deleteByPrimaryKey(bid);
  }


  /**
   * 根据分类查询品牌
   * @param cid
   * @return
   */
  public List<Brand> queryBrandByCid(Long cid) {
    return this.brandMapper.selectBrandByCid(cid);
  }

  /**
   * 根据分类id查询品牌列表
   * @param id
   * @return
   */
  public Brand queryBrandById(Long id) {
    return this.brandMapper.selectByPrimaryKey(id);
  }
}













