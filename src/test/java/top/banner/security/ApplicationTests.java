package top.banner.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class ApplicationTests {
    @Resource
    private DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        BCryptPasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123");
        System.out.println(encode);

        System.out.println(pw.matches("123", encode));
        Connection connection = dataSource.getConnection();
        boolean closed = connection.isClosed();
    }

}
