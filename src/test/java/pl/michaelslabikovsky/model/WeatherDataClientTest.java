package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherDataClientTest {

    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_weather_response_example.json";
    private final static String MAIN_API_PART = "/data/2.5/forecast?q=";
    private final static String EXAMPLE_API_KEY = "a4df15c3943fe91408e1436c4fad4208ceb29b02";
    private final static String ADDITIONAL_API_PART = "&lang=pl&units=metric&appid=" + EXAMPLE_API_KEY;
    private final static String ENDPOINT = MAIN_API_PART + EXAMPLE_CITY_NAME + ADDITIONAL_API_PART;
    public static final String LOCALHOST_URL = "http://localhost:8090";
    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    void teardown() {
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
    void shouldConnectToUrlWhenLoadWeatherDataMethodIsInvoked() {
        //given
        WeatherDataClient dataClient = mock(WeatherDataClient.class);
        given(dataClient.getMainAPIPart()).willReturn(LOCALHOST_URL + MAIN_API_PART);
        given(dataClient.getAdditionalAPIPart()).willReturn(ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        verify(dataClient).loadWeatherData(EXAMPLE_CITY_NAME);
    }

    @Test
    void shouldGetDateTimeBasedOnTimeInterval() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String[] dateTimeArray = getExampleDateTimes();

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        for (int i = 0; i < dateTimeArray.length; i++) {
            assertThat(dataClient.getDateTimeBasedOnTimeInterval(i), is(dateTimeArray[i]));
        }
    }

    @Test
    void shouldGetDescription() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String exampleDescription = "pochmurnie";

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getDescription(0), is(exampleDescription));
    }

    @Test
    void shouldGetTemperatureAsStringValue() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getTemperature(0), isA(String.class));
    }

    @Test
    void shouldGetTemperature() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String exampleTemperature = "19°C";

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getTemperature(0), is(exampleTemperature));
    }

    @Test
    void shouldGetTemperatureInCelsiusDegrees() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertTrue(dataClient.getTemperature(0).contains("°C"));
    }

    @Test
    void shouldGetPressure() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String examplePressure = "1017 hPa";

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getPressure(0), is(examplePressure));
    }

    @Test
    void shouldGetPressureInHectopascals() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertTrue(dataClient.getPressure(0).contains("hPa"));
    }

    @Test
    void pressureValueShouldBeGreaterThanZero() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);
        int pressureExtracted = extractNumberFromStringAndConvertToInt(dataClient.getPressure(0));

        //then
        assertThat(pressureExtracted, greaterThan(0));
    }

    @Test
    void shouldGetWindSpeed() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String exampleWindSpeed = "7.0 m/s";

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getWindSpeed(0), is(exampleWindSpeed));
    }

    @Test
    void shouldGetWindSpeedInMetersPerSecond() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertTrue(dataClient.getWindSpeed(0).contains("m/s"));
    }

    @Test
    void windSpeedValueShouldBeGreaterOrEqualZero() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);
        double windSpeedExtracted = extractNumberFromStringAndConvertToDouble(dataClient.getWindSpeed(0));

        //then
        assertThat(windSpeedExtracted, greaterThanOrEqualTo(0.0));
    }

    @Test
    void shouldGetHumidity() {
        //given
        WeatherDataClient dataClient = new WeatherDataClient(LOCALHOST_URL + MAIN_API_PART, ADDITIONAL_API_PART);
        String exampleHumidity = "53%";

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertThat(dataClient.getHumidity(0), is(exampleHumidity));
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + API_RESPONSE_EXAMPLE_FILE_NAME)));
    }

    private String[] getExampleDateTimes() {
        return new String[]{
                "2021-09-03 12:00:00",
                "2021-09-04 12:00:00",
                "2021-09-05 12:00:00",
                "2021-09-06 12:00:00",
                "2021-09-07 12:00:00",
                "2021-09-08 09:00:00"
        };
    }

    private int extractNumberFromStringAndConvertToInt(String str) {
        int pressureAsNumber = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                pressureAsNumber = Integer.parseInt(str.substring(0, i));
            }
        }
        return pressureAsNumber;
    }

    private double extractNumberFromStringAndConvertToDouble(String str) {
        double extractedValue = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                extractedValue = Double.parseDouble(str.substring(0, i));
            }
        }
        return extractedValue;
    }
}