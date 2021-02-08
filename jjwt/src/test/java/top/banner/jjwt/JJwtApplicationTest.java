package top.banner.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class JJwtApplicationTest {
    /**
     * 生成jwt
     */
    @Test
    public void testCreateJwt() {
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256, "xxxx");

        String token = jwtBuilder.compact();
        System.out.println(token);


        //三部分的base64解密
        System.out.println("--------------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }


    /**
     * 解析token
     */
    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjEyNzE2MzE2fQ.-hqlsqSTan-KvS4LI9nluVcgrGH_-X1sEf2VVyFHe50";
        //解析token获取负载中的声明对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();

        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
    }


    /**
     * 创建带过期时间的token
     */
    @Test
    public void testCreatTokenHasExp() {
        //当前系统时间的长整型
        long now = System.currentTimeMillis();
        //过期时间，这里是1分钟后的时间长整型
        long exp = now + 60 * 1000;

        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256, "xxxx")
                //设置过期时间
                .setExpiration(new Date(exp));
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
    }

    /**
     * 校验token是否过期
     */
    @Test
    public void testParseTokenHasExp() {
        //token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjEyNzE2OTU5LCJleHAiOjE2MTI3MTcwMTl9.9yqAmb-C43p5CTsW8XxD1_vyUG5TmnaUeEVAYASbAec";
        //解析token获取负载中的声明对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间:" + sf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sf.format(new Date()));
    }


    /**
     * 自定义声明
     */
    @Test
    public void testCreatTokenByClaims() {
        //当前系统时间的长整型
        long now = System.currentTimeMillis();
        //过期时间，这里是1分钟后的时间长整型
        long exp = now + 60 * 1000;
        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256, "xxxx")
                //设置过期时间
                .setExpiration(new Date(exp))
                //直接传入map
                // .addClaims(map)
                .claim("roles", "admin")
                .claim("logo", "shsxt.jpg");
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
    }

    /**
     * 获取自定义声明
     */
    @Test
    public void testParseTokenByClaims() {
        //token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjEyNzkxNTExLCJleHAiOjE2MTI3OTE1NzEsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoic2hzeHQuanBnIn0.7C1Tg_biI0iBT5G1fFWgNhi0dPrt6FmrydOv9V9xrRc";
        //解析token获取负载中的声明对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间:" + sf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sf.format(new Date()));
        System.out.println("roles:" + claims.get("roles"));
        System.out.println("logo:" + claims.get("logo"));
    }
}
