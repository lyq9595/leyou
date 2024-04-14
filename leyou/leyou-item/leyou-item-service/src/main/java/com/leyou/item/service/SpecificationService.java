package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.groupMapper.select(record);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParms(Long gid,Long cid,Boolean generic,Boolean search) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(search);
        return this.paramMapper.select(record);
    }

    /**
     * 新增规格组
     * @param specGroup
     * @return
     */
    @Transactional
    public void saveSpecGroup(SpecGroup specGroup) {
        this.groupMapper.insert(specGroup);
    }

    /**
     * 根据gid删除分组
     * @param specGroupId
     * @return
     */
    @Transactional
    public void deleteSpecGroup(Long specGroupId) {
        this.groupMapper.deleteByPrimaryKey(specGroupId);
    }

    /**
     * 更新商品信息
     * @param specGroup
     * @return
     */
    public void updateSpecGroup(SpecGroup specGroup) {
        this.groupMapper.updateByPrimaryKeySelective(specGroup);
    }

    /**
     * 新增参数信息
     * @param specParam
     * @return
     */
    public void saveSpecParam(SpecParam specParam) {
        this.paramMapper.insert(specParam);
    }

    /**
     * 根据id删除指定规格参数
     * @param id
     * @return
     */
    public void deleteSpecParam(Long id) {
        this.paramMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改指定规格参数
     * @param specParam
     * @return
     */
    public void updateSpecParam(SpecParam specParam) {
        this.paramMapper.updateByPrimaryKeySelective(specParam);
    }

    /**
     * 根据分类id查询参数组和 参数
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupWithParam(Long cid) {
        List<SpecGroup> groups=this.queryGroupByCid(cid);
        groups.forEach(group->{
            List<SpecParam> params = this.queryParms(group.getId(), null, null, null);
            group.setParams(params);
        });

        return groups;
    }
}










