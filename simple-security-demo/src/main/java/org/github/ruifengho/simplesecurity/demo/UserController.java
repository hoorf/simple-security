package org.github.ruifengho.simplesecurity.demo;


import org.github.ruifengho.simplesecurity.annotation.PreAuthorize;
import org.github.ruifengho.simplesecurity.jwt.JwtTokenParser;
import org.github.ruifengho.simplesecurity.jwt.JwtUser;
import org.github.ruifengho.simplesecurity.jwt.support.DefaultJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class UserController {

    @Autowired
    private JwtTokenParser jwtTokenParser;


    @PreAuthorize("hasLogin()")
    @GetMapping("/user")
    public JwtUser user() {
        return jwtTokenParser.getUser();
    }


    @GetMapping("/user-no-access")
    public JwtUser userNoAccess() {
        return jwtTokenParser.getUser();
    }

    /**
     * 演示基于注解的权限控制
     *
     * @return 如果有权限返回 亲，你同时有user、admin角色..
     */
    @GetMapping("/annotation-test")
    @PreAuthorize("hasAllRoles('user','admin')")
    public String annotationTest() {
        return "亲，你同时有user、admin角色..";
    }

    @GetMapping("/annotation-test-no-access")
    @PreAuthorize("hasAllRoles('user','admin','xx')")
    public String annotationTestNoAccess() {
        return "亲，你同时有user、admin、xx角色..";
    }


    @GetMapping("/login")
    public String loginReturnToken() {
        DefaultJwtUser user = DefaultJwtUser.builder()
                .id(1 + "")
                .userName("张三")
                .permissions(Arrays.asList("user", "admin"))
                .build();
        return jwtTokenParser.generateToken(user);
    }
}
