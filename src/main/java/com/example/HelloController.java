package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller("/hello")
public class HelloController {

    private final DataSource dataSource;

    public HelloController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    @Get(produces = MediaType.TEXT_PLAIN)
    public String test() throws SQLException {
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("SELECT 1");
        rs.next();
        return "Result: " + rs.getInt(1);
    }
}
