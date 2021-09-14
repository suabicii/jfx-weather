package pl.michaelslabikovsky.model;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WeatherDataClientTest {

    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private static final String API_RESPONSE_EXAMPLE_FILE_NAME = "api_weather_response_example.json";
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
    public void testStatusCodePositive() {
        given().
                when().
                get("http://localhost:8090/an/endpoint").
                then().
                assertThat().statusCode(200);
    }


    @Test
    void shouldConnectToUrlWhenLoadWeatherDataMethodIsInvoked() {
        //given
        WeatherDataClient dataClient = mock(WeatherDataClient.class);
        given(dataClient.getMainAPIPart()).willReturn("http://localhost:8090");
        given(dataClient.getAdditionalAPIPart()).willReturn("");

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        verify(dataClient).loadWeatherData(EXAMPLE_CITY_NAME);
    }

    @Test
    void getDateTimeBasedOnTimeInterval() {
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
        wireMockServer.stubFor(get(urlEqualTo("/an/endpoint"))
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