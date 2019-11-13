package com.casic.common.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.casic.common.web.domain.vo.JwtAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Assert;

/* *
 * @Author tomsun28
 * @Description 
 * @Date 16:29 2018/3/8
 */
public class JsonWebTokenUtil {
    public static final String SECRET_KEY = "?::4343fdf4fdf6cvf):";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static  CompressionCodecResolver codecResolver = new DefaultCompressionCodecResolver();

    private JsonWebTokenUtil() {

    }

    /* *
     * @Description  json web token 签发
     * @param id 令牌ID
     * @param subject 用户ID
     * @param issuer 签发人
     * @param period 有效时间(毫秒)
     * @param roles 访问主张-角色
     * @param permissions 访问主张-权限
     * @param algorithm 加密算法
     * @Return java.lang.String
     */
    public static String issueJWT(String id,String appId,String username, String issuer, Long period, String roles, String permissions, SignatureAlgorithm algorithm) {
        // 当前时间戳
        Long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secreKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        JwtBuilder jwtBuilder = Jwts.builder();
        
        if (!StringUtils.isEmpty(id)) {
            jwtBuilder.setId(id);
        }
        if (!StringUtils.isEmpty(appId)) {
            jwtBuilder.setSubject(appId);
        }
        if (!StringUtils.isEmpty(issuer)) {
            jwtBuilder.setIssuer(issuer);
        }
        // 设置签发时间
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        // 设置到期时间
        if (null != period) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period * 1000));
        }
        if (!StringUtils.isEmpty(roles)) {
            jwtBuilder.claim("roles",roles);
        }
        if (!StringUtils.isEmpty(permissions)) {
            jwtBuilder.claim("perms",permissions);
        }
        if (!StringUtils.isEmpty(username)) {
            jwtBuilder.claim("username",username);
        }
        // 压缩，可选GZIP
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        // 加密设置
        jwtBuilder.signWith(algorithm,secreKeyBytes);

        return jwtBuilder.compact();
    }

    /**
     * 解析JWT的Payload
     * Payload : 有效载荷,装载货物
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String parseJwtPayload(String jwt){
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        for (char c : jwt.toCharArray()) {
            if (c == '.') {
                CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
                String token = tokenSeq != null ? tokenSeq.toString() : null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        
        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }
        
        // =============== Header =================
        
		Header header = null;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = readValue(origValue);
            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = codecResolver.resolveCompressionCodec(header);
        }
        
        // =============== Body =================
        String payload = "";
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, io.jsonwebtoken.lang.Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        return payload;
    }

    
    /**
     * 验签JWT
     *
     * @param jwt json web token
     */
    public static JwtAccount parseJwt(String jwt, String appKey) throws 
					ExpiredJwtException, 
    				UnsupportedJwtException, 
    				MalformedJwtException, 
    				SignatureException, 
    				IllegalArgumentException,
					Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(appKey))
                .parseClaimsJws(jwt)
                .getBody();
        JwtAccount jwtAccount = new JwtAccount();
        jwtAccount.setTokenId(claims.getId());// 令牌ID
        jwtAccount.setAppId(claims.getSubject());// 客户标识
        jwtAccount.setIssuer(claims.getIssuer());// 签发者
        jwtAccount.setIssuedAt(claims.getIssuedAt());// 签发时间
        jwtAccount.setAudience(claims.getAudience());// 接收方
        jwtAccount.setRoles(claims.get("roles", String.class));// 访问主张-角色
        jwtAccount.setPerms(claims.get("perms", String.class));// 访问主张-权限
        jwtAccount.setUsername(claims.get("username", String.class)); //访问用户
        return jwtAccount;
    }
    

    /* *
     * @Description
     * @Param [val] 从json数据中读取格式化map
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readValue(String val) {
        try {
            return mapper.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + val, e);
        }
    }

    /**
     * 分割字符串进SET
     */
    @SuppressWarnings("unchecked")
    public static Set<String> split(String str) {
        Set<String> set = new HashSet<String>();
        if (StringUtils.isEmpty(str)) {
            return set;
        }
        set.addAll(CollectionUtils.arrayToList(str.split(",")));
        return set;
    }
    
    
    public static boolean isExpired(String jwtString){
    	try {
			JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwtString, JsonWebTokenUtil.SECRET_KEY);
			System.out.println("parseJwt userId : "+jwtAccount.getAppId());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT expired ！ ");
			return true;
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public static void main(String[] args) {
    	String jwt = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNo8jNsOwiAQRP9ln0tSYLn1Z8xWFq3a1hQwJsZ_F158muTMmfnArSwwgSN0cWQvrGUlMEQjiLwTNBq2alYh6AQD5Do3mW0yZ61RyeRw1hgcGY5a2SS9p5GauOTcRKrlKsp-501kPl589IYKTNKgNAYR7QD8fv6B6-DYH9zXPU-Xyrm0WW0HG63cX-O6bPD9AQAA__8.3gGia9e2zZmJC0CG08J19Aglj1i4HwdqsGEHLEhU7DmYFble7nmRx_U460KLpILrItdZXZrLLz5__lXcRlnq5w";
//    	String parseJwtPayload = JsonWebTokenUtil.parseJwtPayload(jwt);
//    	System.out.println("parseJwtPayload : "+parseJwtPayload);
		try {
//			String parseJwtPayload = JsonWebTokenUtil.parseJwtPayload(jwt);
//			System.out.println("parseJwtPayload : "+parseJwtPayload);
			JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt, JsonWebTokenUtil.SECRET_KEY);
			System.out.println("parseJwt : "+jwtAccount.getAppId());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT expired ！ ");
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}

}



