package top.banner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.banner.bean.user.UserLoginVO;
import top.banner.bean.user.UserRegisterVO;
import top.banner.entity.User;
import top.banner.service.UserService;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/api/users")
public class UserController {
    @Resource
    private UserService userService;


    @ApiOperation("注册")
    @PostMapping("/register")
    public User register(@RequestBody UserRegisterVO registerVO) {
        return userService.register(registerVO.getUsername(), registerVO.getPassword());
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public User login(@RequestBody UserLoginVO loginVO) {
        return userService.login(loginVO.getUsername(), loginVO.getPassword());
    }

    @ApiOperation("通过Token获取用户信息")
    @GetMapping("/info")
    public User info() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
