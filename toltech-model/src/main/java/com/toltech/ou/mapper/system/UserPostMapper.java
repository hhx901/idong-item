package com.toltech.ou.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.system.UserPost;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 14:13
 * @Version 1.0
 */
public interface UserPostMapper extends BaseMapper<UserPost> {
    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserPostByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    public int countUserPostById(Long postId);

    /**
     * 批量删除用户和岗位关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserPost(Long[] ids);

    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户角色列表
     * @return 结果
     */
    public int batchUserPost(List<UserPost> userPostList);
}
