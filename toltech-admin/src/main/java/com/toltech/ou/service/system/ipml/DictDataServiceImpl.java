package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.entity.system.Dept;
import com.toltech.ou.entity.system.DictData;
import com.toltech.ou.mapper.system.DeptMapper;
import com.toltech.ou.mapper.system.DictDataMapper;
import com.toltech.ou.service.system.DeptService;
import com.toltech.ou.service.system.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/24 13:19
 * @Version 1.0
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper,DictData> implements DictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    @Override
    public DictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    @Override
    public int deleteDictDataById(Long dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    @Override
    public int deleteDictDataByIds(String ids) {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
    }

    @Override
    public int insertDictData(DictData dictData) {
        dictData.setCreateBy(ShiroUtils.getLoginName());
        return dictDataMapper.insertDictData(dictData);
    }

    @Override
    public int updateDictData(DictData dictData) {
        dictData.setUpdateBy(ShiroUtils.getLoginName());
        return dictDataMapper.updateDictData(dictData);
    }
}
