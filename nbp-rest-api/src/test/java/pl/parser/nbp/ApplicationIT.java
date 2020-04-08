package pl.parser.nbp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldServeSwaggerDocumentation() {
        String swaggerString = restTemplate.getForObject("http://localhost:" + port + "/swagger-ui.html", String.class);
        assertThat(swaggerString).contains("<div id=\"swagger-ui\"></div>");
    }
}
