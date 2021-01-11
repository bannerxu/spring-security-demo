package top.banner.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import top.banner.security.service.UserService;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserService userService;
    @Resource
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

//        http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all


        clients.inMemory()
                //客户端id
                .withClient("client")
                //秘钥
                .secret(passwordEncoder.encode("112233"))
                //重定向地址
                .redirectUris("http://www.baidu.com")
                //授权范围
                .scopes("all")
                /*
                 * 授权类型
                 * authorization_code 授权码模式
                 * password 密码模式
                 */
                .authorizedGrantTypes("authorization_code", "password");
    }
}
