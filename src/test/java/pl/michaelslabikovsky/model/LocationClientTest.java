package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class LocationClientTest {

    private static final String EXAMPLE_SEARCH_FIELD_VALUE = "Lublin";
    private static final String SEARCH_FIELD_NO_RESULTS = "NO_RESULTS";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_location_response_example.json";
    private static final String EMPTY_RESPONSE_FILE_NAME = "empty.json";
    private final static String MAIN_API_PART = "/geo/1.0/direct?q=";
    private final static String ADDITIONAL_API_PART = "&limit=5&appid=a4df15c3943fe91408e1436c4fad4208ceb29b02";
    private final static String ENDPOINT = MAIN_API_PART + EXAMPLE_SEARCH_FIELD_VALUE + ADDITIONAL_API_PART;
    private final static String ENDPOINT_EMPTY_RESULT = MAIN_API_PART + SEARCH_FIELD_NO_RESULTS + ADDITIONAL_API_PART;
    private static final String LOCALHOST_URL = "http://localhost:8080";
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        setupStub();
        setupEmptyStub();
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

        //then
        assertThat(locationClient.getFoundLocation(0, EXAMPLE_SEARCH_FIELD_VALUE), is(exampleFoundCity));
    }

    @Test
    void getFoundLocationShouldReturnEmptyStringIfNoResultsAreFound() throws IOException {
        //given
        LocationClient locationClient = new LocationClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        locationClient.loadLocationData(SEARCH_FIELD_NO_RESULTS);

        //then
        assertThat(locationClient.getFoundLocation(0, SEARCH_FIELD_NO_RESULTS), is(""));
    }

    private static void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + API_RESPONSE_EXAMPLE_FILE_NAME)));
    }

    private static void setupEmptyStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT_EMPTY_RESULT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + EMPTY_RESPONSE_FILE_NAME)));
    }
}