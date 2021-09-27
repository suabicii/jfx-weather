package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LocationClientTest {

    private static final String EXAMPLE_SEARCH_FIELD_VALUE = "Lublin";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_location_response_example.json";
    private final static String MAIN_API_PART = "/geo/1.0/direct?q=";
    private final static String EXAMPLE_API_KEY = "a4df15c3943fe91408e1436c4fad4208ceb29b02";
    private final static String ADDITIONAL_API_PART = "&limit=5&appid=" + EXAMPLE_API_KEY;
    private final static String ENDPOINT = MAIN_API_PART + EXAMPLE_SEARCH_FIELD_VALUE + ADDITIONAL_API_PART;
    public static final String LOCALHOST_URL = "http://localhost:8080";
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        setupStub();
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    void shouldGetFoundLocation() throws IOException {
        //given
        LocationClient locationClient = new LocationClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String exampleFoundCity = "Lublin, Województwo lubelskie, PL";

        //when
        locationClient.loadLocationData(EXAMPLE_SEARCH_FIELD_VALUE);

        assertThat(locationClient.getFoundLocation(0, EXAMPLE_SEARCH_FIELD_VALUE), is(exampleFoundCity));
    }

    private static void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + API_RESPONSE_EXAMPLE_FILE_NAME)));
    }
}