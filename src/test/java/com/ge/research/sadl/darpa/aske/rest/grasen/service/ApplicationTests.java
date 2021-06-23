package com.ge.research.sadl.darpa.aske.rest.grasen.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Checks that the application starts successfully and its health endpoint returns "UP".
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"management.port="})
class ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private GrasenController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt_port;
    
    @Test
    public void contextShouldLoad() {
        assertThat(controller).isNotNull();
        logger.trace("TRACE log");
        logger.debug("DEBUG log");
        logger.info("INFO log");
        logger.warn("WARN log");
        logger.error("ERROR log");
    }

    @Test
    public void healthShouldReturnString() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + mgt_port + "/actuator/health",
                                             String.class)).contains("{\"status\":\"UP\"}");
    }

}
