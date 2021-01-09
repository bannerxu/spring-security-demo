package top.banner.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("执行了自定义登录逻辑。。。。");
        if (!Objects.equals("admin", username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        String password = passwordEncoder.encode("123");
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_abc,/main.html," +
                        "/insert,/update"));
    }
}
