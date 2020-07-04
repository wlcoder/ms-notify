package com.wl.msnotify.util;

import com.wl.msnotify.entity.SysUser;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @author : wl
 * @Description :
 * @date : 2020/7/3 13:25
 */
public class JwtUtil {

    public static final String AUTHORIZATION_SECRET = "wlcoder";
    private static final String UID = "uid";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String STATUS = "status";

    //创建秘钥
    public static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(AUTHORIZATION_SECRET);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }

    /**
     * 生成token的方法
     *
     * @param user
     * @param expire
     * @return
     */
    public static String generatorToken(SysUser user, int expire) {
        return Jwts.builder().claim(UID, user.getId())
                .claim(USERNAME, user.getUsername())
                .claim(PASSWORD, user.getPassword())
                .claim(STATUS, user.getStatus())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**
     * 根据token获取token中的信息
     *
     * @param token
     * @return
     */
    public static SysUser getTokenInfo(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return SysUser.builder().id((Integer) claims.get(UID))
                .username((String) claims.get(USERNAME))
                .password((String) claims.get(PASSWORD))
                .status((Integer) claims.get(STATUS))
                .build();

    }

}
