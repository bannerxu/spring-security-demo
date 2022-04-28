package top.banner.bean.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("password")
    private String password;
}
