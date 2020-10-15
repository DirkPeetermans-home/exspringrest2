package be.abis.exercise;



import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExB1Application {

	public static void main(String[] args) {
		SpringApplication.run(ExB1Application.class, args);
	}

	@Bean
	@Profile("production")
	public DataSource testDataSource(){
		return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.DERBY)
					.addScript("classpath:create_course_table_derby.sql")
					.build();
	}
	
	@Bean
	@Profile("production")
	public JdbcTemplate jdbcTemplateDerby(){
		return new JdbcTemplate(testDataSource());
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
	}
	
}
