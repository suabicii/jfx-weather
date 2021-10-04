package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class WeatherDataClientTest {

    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_weather_response_example.json";
    private final static String MAIN_API_PART = "/data/2.5/forecast?q=";
    private final static String ADDITIONAL_API_PART = "&lang=pl&units=metric&appid=a4df15c3943fe91408e1436c4fad4208ceb29b02";
    private final static String ENDPOINT = MAIN_API_PART + EXAMPLE_CITY_NAME + ADDITIONAL_API_PART;
    private static final String LOCALHOST_URL = "http://localhost:8090";
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        setupStub();
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void shouldReturnPositiveStatusCodeWhenUrlIsCorrect() {
        given().
                when().
                get(LOCALHOST_URL + ENDPOINT).
                then().
                assertThat().statusCode(200);
    }

    @Test
    void shouldGetDateTimeBasedOnTimeInterval() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String[] expectedDateTimeArray = getExpectedDateTimes();

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);
        String[] actualDateTimeArray = getActualDateTimeArray(dataClient, expectedDateTimeArray);

        //then
        assertThat(actualDateTimeArray, is(expectedDateTimeArray));
    }

    @Test
    void shouldGetDescription() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getDescription(0), is("pochmurnie"));
    }

    @Test
    void shouldGetTemperature() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getTemperature(0), is("19°C"));
    }

    @Test
    void shouldGetPressure() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getPressure(0), is("1017 hPa"));
    }

    @Test
    void shouldGetWindSpeed() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getWindSpeed(0), is("7.0 m/s"));
    }

    @Test
    void shouldGetHumidity() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getHumidity(0), is("53%"));
    }

    private static void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + API_RESPONSE_EXAMPLE_FILE_NAME)));
    }

    private String[] getExpectedDateTimes() {
        return new String[]{
                "2021-09-03 12:00:00",
                "2021-09-04 12:00:00",
                "2021-09-05 12:00:00",
                "2021-09-06 12:00:00",
                "2021-09-07 12:00:00",
                "2021-09-08 09:00:00"
        };
    }

    private String[] getActualDateTimeArray(WeatherDataClient dataClient, String[] expectedDateTimeArray) {
        String[] actualDateTimeArray = new String[6];
        for (int i = 0; i < expectedDateTimeArray.length; i++) {
            actualDateTimeArray[i] = dataClient.getDateTimeBasedOnTimeInterval(i);
        }
        return actualDateTimeArray;
    }
}