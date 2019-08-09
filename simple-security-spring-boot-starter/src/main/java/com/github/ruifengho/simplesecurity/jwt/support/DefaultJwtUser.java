package com.github.ruifengho.simplesecurity.jwt.support;

import com.github.ruifengho.simplesecurity.jwt.JwtUser;

import java.util.List;

public class DefaultJwtUser implements JwtUser {

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 权限
     */
    private List<String> permissions;

    @java.beans.ConstructorProperties({"id", "userName", "permissions"})
    DefaultJwtUser(String id, String userName, List<String> permissions) {
        this.id = id;
        this.userName = userName;
        this.permissions = permissions;
    }

    public static DefaultUserBuilder builder() {
        return new DefaultUserBuilder();
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }


    @Override
    public String toString() {
        return "DefaultJwtUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    public static class DefaultUserBuilder {
        private String id;
        private String userName;
        private List<String> permissions;

        DefaultUserBuilder() {
        }

        public DefaultUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DefaultUserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public DefaultUserBuilder permissions(List<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public DefaultJwtUser build() {
            return new DefaultJwtUser(id, userName, permissions);
        }

        @Override
        public String toString() {
            return "DefaultJwtUser.DefaultUserBuilder(id=" + this.id + ", userName=" + this.userName + ", permissions=" + this.permissions + ")";
        }
    }
}
