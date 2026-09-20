package com.learning.ai.smart_loan_advisor.config.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Ranjit Soni on 19-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Configuration
public class MysqlMemoryChatMemoryConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring_ai_app");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root");
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ChatMemory mysqlChatMemory(JdbcTemplate jdbcTemplate) {
        JdbcChatMemoryRepository jdbcChatMemoryRepository = JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .build();
        return MessageWindowChatMemory.builder().chatMemoryRepository(jdbcChatMemoryRepository).build();
    }
}
