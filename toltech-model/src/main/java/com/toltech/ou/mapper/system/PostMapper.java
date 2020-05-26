package com.toltech.ou.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.system.Menu;
import com.toltech.ou.entity.system.Post;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 15:43
 * @Version 1.0
 */
public interface PostMapper extends BaseMapper<Post> {
    /**
     * 查询岗位数据集合
     *
     * @param post 岗位信息
     * @return 岗位数据集合
     */
    public List<Post> selectPostList(Post post);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    public List<Post> selectPostAll();

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    public List<Post> selectPostsByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    public Post selectPostById(Long postId);

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePostByIds(Long[] ids);

    /**
     * 修改岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    public int updatePost(Post post);

    /**
     * 新增岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    public int insertPost(Post post);

    /**
     * 校验岗位名称
     *
     * @param postName 岗位名称
     * @return 结果
     */
    public Post checkPostNameUnique(String postName);

    /**
     * 校验岗位编码
     *
     * @param postCode 岗位编码
     * @return 结果
     */
    public Post checkPostCodeUnique(String postCode);
}
