package com.flip.flipapp.common;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CleanUpTestExecutionListener extends AbstractTestExecutionListener {

  @Override
  public void afterTestMethod(final TestContext testContext) {
    final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
    final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
    truncateTables(jdbcTemplate, truncateQueries);
  }

  private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
    return jdbcTemplate.queryForList(
        "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME != 'flyway_schema_history'",
        String.class);
  }

  private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
    return testContext.getApplicationContext().getBean(JdbcTemplate.class);
  }

  private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
    execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = 0");
    truncateQueries.forEach(v -> execute(jdbcTemplate, v));
    execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = 1");
  }

  private void execute(final JdbcTemplate jdbcTemplate, final String query) {
    jdbcTemplate.execute(query);
  }
}
