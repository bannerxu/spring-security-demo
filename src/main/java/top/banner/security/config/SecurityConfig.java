package top.banner.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import top.banner.security.handler.MyAccessDeniedHandler;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@Order(90)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private PersistentTokenRepository persistentTokenRepository;
    @Resource
    private DataSource dataSource;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //自定义入参
//                .usernameParameter("username123")
//                .passwordParameter("password123")
                //自定义登录页面
                .loginPage("/login.html")
                //必须和表单提交的接口一样，才会去只写自定义逻辑。
                .loginProcessingUrl("/login")
                //登录成功的跳转地址，必须是POST请求
                .successForwardUrl("/toMain")
                //自定义登录成功handler
//                .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                //登录失败的跳转地址，必须是POST请求
                .failureForwardUrl("/toError");

        //自定义登录失败handler
//                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        //授权
        http.authorizeRequests()
                //放行 不需要认证
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                .antMatchers("/**/*.png").permitAll()
                //放行正则表达式
//                .regexMatchers(".+[.]png").permitAll()
//                .regexMatchers(HttpMethod.POST, "/demo").permitAll()
//                .mvcMatchers("/demo").servletPath("/x").permitAll()

                //权限控制
//                .antMatchers("/main1.html").hasAuthority("admin")
//                .antMatchers("/main1.html").hasAnyAuthority("admins", "admin")
//                .antMatchers("/main1.html").hasAnyRole("admins", "admin")
//                .antMatchers("/main1.html").hasRole("abc")
                //基于ip地址控制
//                .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
//                .antMatchers("/main1.html").access("hasRole('abc')")

                //所有请求必须认证才能访问，必须登录
                .anyRequest().authenticated();
        //自定义的access方法
//                .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");


        //记住我
        http.rememberMe()
                //设置数据源
                .tokenRepository(persistentTokenRepository)
                //自定义入参名
                //.rememberMeParameter("");
                //超时时间
                .tokenValiditySeconds(60)
                //自定义登录逻辑
                .userDetailsService(userDetailsService);


        //关闭csrf(跨站请求伪造)
        http.csrf().disable();


        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        //每次启动时自动建表，第一次之后就关掉。
        persistentTokenRepository.setCreateTableOnStartup(true);
        return persistentTokenRepository;
    }

}

