package pro.sky.recommendation_service.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DynamicRuleMapper implements RowMapper<DynamicRule> {

    @Override
    public DynamicRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        DynamicRule dynamicRule = new DynamicRule();
        dynamicRule.setProduct_id(UUID.fromString(rs.getString("PRODUCT_ID")));
        dynamicRule.setProduct_name(rs.getString("PRODUCT_NAME"));
        dynamicRule.setProduct_text(rs.getString("PRODUCT_TEXT"));
        return dynamicRule;
    }

}
