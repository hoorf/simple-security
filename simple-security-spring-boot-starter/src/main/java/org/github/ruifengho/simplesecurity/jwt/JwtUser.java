package org.github.ruifengho.simplesecurity.jwt;

import java.util.List;

public interface JwtUser {


    /**
     * 用户id
     *
     * @return
     */
    String getId();


    /**
     * 用户名称
     *
     * @return
     */
    String getUserName();


    /**
     * 权限列表
     *
     * @return
     */
    List<String> getPermissions();

}
