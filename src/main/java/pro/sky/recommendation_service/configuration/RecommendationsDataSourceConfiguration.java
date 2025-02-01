package pro.sky.recommendation_service.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RecommendationsDataSourceConfiguration {

    /**
     * Configures and returns a read-only Hikari DataSource for the transaction database like a recommendations DataSource.
     * The URL for the database is injected from the application properties using the 'application.recommendations-db.url' key.
     * The H2 driver is used.
     *
     * @param recommendationsUrl    The JDBC URL for the transaction database.
     * @return A HikariDataSource configured for read-only access to the transaction database like a recommendations DataSource.
     */
    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}") String recommendationsUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    /**
     * Creates and returns a JdbcTemplate configured with the recommendations DataSource.
     * This JdbcTemplate is used to interact with the transaction database.
     *
     * @param dataSource    The read-only DataSource for the transaction database, injected
     *                      using the @Qualifier("recommendationsDataSource") annotation.
     * @return A JdbcTemplate configured with the recommendations DataSource.
     */
    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}
