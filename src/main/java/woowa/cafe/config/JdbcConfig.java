package woowa.cafe.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import woowa.frame.core.annotation.Component;

import javax.sql.DataSource;

@Component
public class JdbcConfig {

    private DataSource dataSource;

    public JdbcConfig() {
    }

    public void init() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/woowa";
        String user = "user1";
        String password = "test1234@";

        if (System.getenv("DB_URL") != null) {
            jdbcUrl = System.getenv("DB_URL");
        }

        if (System.getenv("DB_USER") != null) {
            user = System.getenv("DB_USER");
        }

        if (System.getenv("DB_PASSWORD") != null) {
            password = System.getenv("DB_PASSWORD");
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            init();
        }
        return dataSource;
    }
}
