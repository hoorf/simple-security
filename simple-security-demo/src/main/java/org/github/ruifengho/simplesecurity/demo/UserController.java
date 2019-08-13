package org.github.ruifengho.simplesecurity.demo;


import com.github.ruifengho.simplesecurity.annotation.RsaApiDecrypt;
import com.github.ruifengho.simplesecurity.annotation.RsaApiEncrypt;
import com.github.ruifengho.simplesecurity.annotation.PreAuthorize;
import com.github.ruifengho.simplesecurity.annotation.SignApiDecrypt;
import com.github.ruifengho.simplesecurity.jwt.JwtTokenParser;
import com.github.ruifengho.simplesecurity.jwt.JwtUser;
import com.github.ruifengho.simplesecurity.jwt.support.DefaultJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("encryption")
    @RsaApiEncrypt
    public DefaultJwtUser encryption(){
        DefaultJwtUser user = DefaultJwtUser.builder()
                .id(1 + "")
                .userName("张三")
                .permissions(Arrays.asList("user", "admin"))
                .build();
        return user;
    }

    @RsaApiDecrypt
    @PostMapping("/decryption")
    public String Decryption(@RequestBody DefaultJwtUser test){
        System.out.println(test);
        return test.toString();
    }


    @GetMapping("/order")
    public Order getOrder(@SignApiDecrypt Order order){
        return order;
    }
}
