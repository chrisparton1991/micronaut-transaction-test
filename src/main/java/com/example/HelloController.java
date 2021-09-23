package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller("/test")
public class HelloController {

    private final DataSource dataSource;

    public HelloController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    @Get(produces = MediaType.TEXT_PLAIN)
    public String test() throws SQLException {
        // Expected: This method should be called inside a transaction, so we see a "Roll back" error message each time.
        // Actual: This method isn't inside a transaction, so the first call creates the table, and subsequent calls fail because the table exists.
        dataSource.getConnection().createStatement().executeUpdate("CREATE TABLE TEST(id INT)");
        throw new RuntimeException("Roll back.");
    }
}
