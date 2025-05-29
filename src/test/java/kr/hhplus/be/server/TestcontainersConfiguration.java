package kr.hhplus.be.server;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
class TestcontainersConfiguration {

	public static final MySQLContainer<?> MYSQL_CONTAINER;
	private static final GenericContainer<?> REDIS_CONTAINER;
	public static final KafkaContainer KAFKA_CONTAINER;

	static {
		MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
			.withDatabaseName("hhplus")
			.withUsername("test")
			.withPassword("test");
		MYSQL_CONTAINER.start();

		System.setProperty("spring.datasource.url", MYSQL_CONTAINER.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC");
		System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
		System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());

		REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("redis:7.4.3"))
				.withReuse(true)
				.withExposedPorts(6379);
		REDIS_CONTAINER.start();

		System.setProperty("data.redis.host", REDIS_CONTAINER.getHost());
		System.setProperty("data.redis.port", REDIS_CONTAINER.getFirstMappedPort().toString());

		// Kafka
		KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("apache/kafka"));
		KAFKA_CONTAINER.start();

		System.setProperty("spring.kafka.bootstrap-servers", KAFKA_CONTAINER.getBootstrapServers());
	}

	@PreDestroy
	public void preDestroy() {
		if (MYSQL_CONTAINER.isRunning()) {
			MYSQL_CONTAINER.stop();
		}
		if (REDIS_CONTAINER.isRunning()) {
			REDIS_CONTAINER.stop();
		}

		if (KAFKA_CONTAINER.isRunning()){
			KAFKA_CONTAINER.stop();
		}
	}
}