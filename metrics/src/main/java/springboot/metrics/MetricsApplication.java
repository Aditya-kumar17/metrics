package springboot.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping(path = "api/v1/test")
public class MetricsApplication {

  public PrometheusMeterRegistry prometheusRegistry =
      new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
  public MeterRegistry registry = new SimpleMeterRegistry();
  public Map<String, Counter> testCount = new HashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(MetricsApplication.class, args);
  }

  @GetMapping
  public String test() {
    incrementRandCount();
    // 			Counter counter = prometheusRegistry.counter("my_counter");
    // counter.increment();

    // Counter counter = registry.counter("my_counter");
    // counter.increment();

    System.out.println("test");
    return "Hello";
  }

  public void incrementRandCount() {
    this.testCount.computeIfAbsent(
        "Flow_1",
        t ->
            Counter.builder("api_http_request")
                .description("counts api request")
                .tags("tags1", "tags2", "asd2", "asd3")
                .register(registry));

    this.testCount.get("Flow_1").increment();

    System.out.println("metricscalled");
  }
}
