package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.exception.BusinessException;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.Post;
import com.toltech.ou.mapper.system.PostMapper;
import com.toltech.ou.mapper.system.UserPostMapper;
import com.toltech.ou.service.system.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 15:42
 * @Version 1.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper,Post> implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Override
    public List<Post> selectPostList(Post post) {
        return postMapper.selectPostList(post);
    }

    @Override
    public List<Post> selectPostAll() {
        return postMapper.selectPostAll();
    }

    @Override
    public List<Post> selectPostsByUserId(Long userId) {
        List<Post> userPosts = postMapper.selectPostsByUserId(userId);
        List<Post> posts = postMapper.selectPostAll();
        for (Post post : posts)
        {
            for (Post userRole : userPosts)
            {
                if (post.getPostId().longValue() == userRole.getPostId().longValue())
                {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    @Override
    public Post selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    @Override
    public int deletePostByIds(String ids) throws Exception {
        Long[] postIds = Convert.toLongArray(ids);
        for (Long postId : postIds)
        {
            Post post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    @Override
    public int insertPost(Post post) {
        post.setCreateBy(ShiroUtils.getLoginName());
        return postMapper.insertPost(post);
    }

    @Override
    public int updatePost(Post post) {
        post.setUpdateBy(ShiroUtils.getLoginName());
        return postMapper.updatePost(post);
    }

    @Override
    public int countUserPostById(Long postId) {
        return userPostMapper.countUserPostById(postId);
    }

    @Override
    public String checkPostNameUnique(Post post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        Post info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }

    @Override
    public String checkPostCodeUnique(Post post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        Post info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.POST_CODE_NOT_UNIQUE;
        }
        return UserConstants.POST_CODE_UNIQUE;
    }
}
