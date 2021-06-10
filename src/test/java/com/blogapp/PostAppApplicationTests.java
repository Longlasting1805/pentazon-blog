package com.blogapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PostAppApplicationTests {

    @Autowired
    DataSource datasource;

    @Autowired
    TestDataSource testDataSource;



    @Test
    void applicationCanConnectToDatabaseTest(){
        assertThat(datasource).isNotNull();
        log.info("Datasource object is created");

        try(Connection connection = datasource.getConnection()){
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("blogdb");
            log.info("Connection --> {}", connection.getCatalog());

        } catch (SQLException throwables) {
            log.info("Exception occurred while " +
                    "connecting to the database --> {}",
                    throwables.getMessage());

        }
    }

    @Test
    void readValuesfromPropertiesTest(){

        assertThat(testDataSource).isNotNull();
        log.info("Test datasource values --> {}", testDataSource);

    }
}
