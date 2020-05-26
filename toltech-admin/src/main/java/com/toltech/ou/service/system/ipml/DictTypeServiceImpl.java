package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.assist.Ztree;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.exception.BusinessException;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.DictData;
import com.toltech.ou.entity.system.DictType;
import com.toltech.ou.mapper.system.DictDataMapper;
import com.toltech.ou.mapper.system.DictTypeMapper;
import com.toltech.ou.service.system.DictDataService;
import com.toltech.ou.service.system.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/24 13:25
 * @Version 1.0
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper,DictType> implements DictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;
    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    @Override
    public DictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public DictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    @Override
    public int deleteDictTypeById(Long dictId) {
        return dictTypeMapper.deleteDictTypeById(dictId);
    }

    @Override
    public int deleteDictTypeByIds(String ids) throws Exception {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            DictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return dictTypeMapper.deleteDictTypeByIds(dictIds);
    }

    @Override
    public int insertDictType(DictType dictType) {
        dictType.setCreateBy(ShiroUtils.getLoginName());
        return dictTypeMapper.insertDictType(dictType);
    }

    @Override
    @Transactional
    public int updateDictType(DictType dictType) {
        dictType.setUpdateBy(ShiroUtils.getLoginName());
        DictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return dictTypeMapper.updateDictType(dictType);
    }

    @Override
    public String checkDictTypeUnique(DictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }

    @Override
    public List<Ztree> selectDictTree(DictType dictType) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<DictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
        for (DictType dict : dictList) {
            if (UserConstants.DICT_NORMAL.equals(dict.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }
    public String transDictName(DictType dictType) {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }
}
