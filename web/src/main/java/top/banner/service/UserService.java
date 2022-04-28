package top.banner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.banner.dao.UserDao;
import top.banner.entity.User;
import top.banner.exception.MyException;
import top.banner.security.util.JwtTokenUtil;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDao userDao;


    @Transactional
    public User register(String username, String password) {
        // 用户名 去数据库查，是否已经注册
        User user = userDao.findByUsernameAndDeletedIsFalse(username);
        // 如果已经注册。报错
        if (user != null) {
            throw new MyException("username is exist");
        }
        // 如果没有注册 ，生成token并保存
        user = new User(username, password, UUID.randomUUID().toString().replace("-", ""));
        userDao.save(user);
        // 返回用户信息
        return user;
    }

    public User login(String username, String password) {
        //通过账号获取用户信息
        final User user = userDao.findByUsernameAndDeletedIsFalse(username);
        //判断用户是否存在
        if (user == null) {
            throw new MyException("用户名不存在");
        }
        //判断密码是否正确
        if (Objects.equals(password, user.getPassword())) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            user.setToken(tokenHead + jwtTokenUtil.generateToken(user));
            return user;
        }
        throw new MyException("password is error");
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username username
     */
    public UserDetails loadUserByUsername(String username) {
        return userDao.findByUsernameAndDeletedIsFalse(username);
    }
}
