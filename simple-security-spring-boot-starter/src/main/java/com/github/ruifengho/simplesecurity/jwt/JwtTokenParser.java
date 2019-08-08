package com.github.ruifengho.simplesecurity.jwt;

public interface JwtTokenParser<U extends JwtUser> {

     U getUser();

      String generateToken(U user);
}
