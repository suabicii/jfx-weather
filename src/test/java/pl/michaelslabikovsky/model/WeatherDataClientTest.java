package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherDataClientTest {

    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_weather_response_example.json";
    private final static String MAIN_API_PART = "/data/2.5/forecast?q=";
    private final static String ADDITIONAL_API_PART = "&lang=pl&units=metric&appid=";
    private final static String EXAMPLE_API_KEY = "a4df15c3943fe91408e1436c4fad4208ceb29b02";
    private final static String ENDPOINT = MAIN_API_PART + EXAMPLE_CITY_NAME + ADDITIONAL_API_PART + EXAMPLE_API_KEY;
    public static final String LOCALHOST_URL = "http://localhost:8090";
    private WireMockServer wireMockServer;

    @Mock
    private WeatherDataClient dataClient;

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
        given(dataClient.getMainAPIPart()).willReturn(LOCALHOST_URL + MAIN_API_PART);
        given(dataClient.getAdditionalAPIPart()).willReturn(ADDITIONAL_API_PART);
        given(dataClient.getApiKey()).willReturn(EXAMPLE_API_KEY);

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        verify(dataClient).loadWeatherData(EXAMPLE_CITY_NAME);
    }

    @Test
    void shouldGetDateTimeBasedOnTimeInterval() throws IOException {
        //given
        given(dataClient.getMainAPIPart()).willReturn(LOCALHOST_URL + MAIN_API_PART);
        given(dataClient.getAdditionalAPIPart()).willReturn(ADDITIONAL_API_PART);
        given(dataClient.getApiKey()).willReturn(EXAMPLE_API_KEY);
        Response response = given().when().get(LOCALHOST_URL + ENDPOINT);
        given(dataClient.getResult()).willReturn(response.jsonPath().get().toString());
        String[] dateTimeArray = new String[]{
                "2021-09-03 12:00:00",
                "2021-09-04 12:00:00",
                "2021-09-05 12:00:00",
                "2021-09-06 12:00:00",
                "2021-09-07 12:00:00",
                "2021-09-08 09:00:00"
        };

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        assertAll(
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(0), is(dateTimeArray[0])),
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(1), is(dateTimeArray[1])),
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(2), is(dateTimeArray[2])),
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(3), is(dateTimeArray[3])),
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(4), is(dateTimeArray[4])),
                () -> assertThat(dataClient.getDateTimeBasedOnTimeInterval(5), is(dateTimeArray[5]))
        );
    }

    @Test
    void getDescription() {
    }

    @Test
    void getTemperature() {
    }

    @Test
    void getPressure() {
    }

    @Test
    void getWindSpeed() {
    }

    @Test
    void getHumidity() {
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(ENDPOINT))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/" + API_RESPONSE_EXAMPLE_FILE_NAME)));
    }

    /*private String getExampleResultFromApi() {
        File file = new File("file");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            return result.toString().replaceAll("\\s+", ""); // usuń spacje
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}