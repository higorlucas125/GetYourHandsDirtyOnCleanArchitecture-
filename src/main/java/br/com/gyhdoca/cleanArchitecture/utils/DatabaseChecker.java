package br.com.gyhdoca.cleanArchitecture.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

@Component
public class DatabaseChecker implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, "PUBLIC", "%", null)) {
            System.out.println("=== TABELAS NO BANCO DE DADOS ===");
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }
    }
}