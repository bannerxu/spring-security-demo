package top.banner.bean.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRegisterVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("password")
    private String password;

    @ApiModelProperty("phone")
    private String phone;
}
