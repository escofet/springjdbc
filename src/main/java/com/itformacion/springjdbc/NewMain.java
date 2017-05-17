package com.itformacion.springjdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class NewMain {
    public static void main(String[] args) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/sample");
        ds.setUsername("app");
        ds.setPassword("app");
        
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(ds);
        // Get all records in table customers
        String query = "SELECT * FROM customer ORDER BY ZIP";
        List<Map<String, Object>> qr = jdbcTemplateObject.queryForList(query);
        qr.stream().forEach(System.out::println);
        // Get customers' ids
        Set<Integer> custIds = qr.stream()
                .map(x -> (Integer)x.get("CUSTOMER_ID"))
                .collect(Collectors.toSet());
        custIds.stream().forEach(System.out::println);
        // Insert a new discount
        /*
        String insertDiscount = "INSERT INTO discount_code (DISCOUNT_CODE, RATE) VALUES (?, ?)";
        jdbcTemplateObject.update(insertDiscount, "X", 20);
        */
        
        // NamedParameterJdbcTemplate
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
        String updateStr = "UPDATE discount_code SET RATE=:rate WHERE DISCOUNT_CODE=:dc";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dc", "N");
        parameters.put("rate", 35.5);
        template.update(updateStr, parameters);
    }
}
