package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.exception.BusinessException;
import com.toltech.ou.common.shiro.service.PasswordService;
import com.toltech.ou.common.utils.ShiroUtils;

import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.*;
import com.toltech.ou.mapper.system.*;
import com.toltech.ou.service.system.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 16:40
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPostMapper userPostMapper;
    @Autowired
    private PasswordService passwordService;

    /**
     * 根据条件分页查询用户列表
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user) {
        // 生成数据权限过滤条件
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectAllocatedList(User user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUnallocatedList(User user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 通过用户ID删除用户
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws Exception {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            checkUserAllowed(new User(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        user.setSalt(randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<UserRole> list = new ArrayList<UserRole>();
            for (Long roleId : user.getRoleIds())
            {
                UserRole ur = new UserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<UserPost> list = new ArrayList<UserPost>();
            for (Long postId : user.getPostIds())
            {
                UserPost up = new UserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }


    /**
     * 生成随机盐
     */
    public String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int resetUserPwd(User user) {
        return 0;
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        return null;
    }

    @Override
    public String checkPhoneUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkEmailUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    @Override
    public void checkUserAllowed(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new BusinessException("不允许操作超级管理员用户");
        }
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String selectUserPostGroup(Long userId) {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport) {
        return null;
    }

    @Override
    public int changeStatus(User user) {
        return userMapper.updateUser(user);
    }
}
