package io.github.ductvi.apicebolinha.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatasourceConfiguration {

    /**
     * classe de config do datasource
     * injeção dos atributos para config do hikari
     * config do hikari datasource
     * */

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSource hikariDatasource() {
        HikariConfig config = new HikariConfig();

        /**
         * injeção das propriedades do datasource na classe config do hikari
         * */

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        /**
         * maximumPoolSize -> Máximo de conexões que o pool mantém, contando as conexões em uso e ociosas
         * poolName -> Nome do pool de conexões
         * maxLifetime -> Tempo máximo que uma conexão fica ativa (passada em ms)
         * connectionTimeout -> Tempo de espera para conseguir uma conexão do pool
         * connectionTestQuery -> Query de teste para validar a conexão
         * leakDetectionThreshold -> Alerta se a conexão ficar presa pelo tempo determinado
         * */

        config.setMaximumPoolSize(10);
        config.setPoolName("cebolinha-db-pool");
        config.setMaxLifetime(600000); // 10 min
        config.setConnectionTimeout(5000);
        config.setLeakDetectionThreshold(20000);
        config.setConnectionTestQuery("SELECT 1");

        log.info("Iniciando conexão com o datasource na url: {}", url);

        return new HikariDataSource(config); // Instância do hikariDatasource passando as configs no construtor
    }
}
