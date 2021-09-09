package pl.michaelslabikovsky.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import pl.michaelslabikovsky.Launcher;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WeatherDataClientTest {

    private static final String API_RESPONSE_EXAMPLE_FILE = "assets/api_weather_response_example.json";
    private static final String API_RESPONSE_EXAMPLE_PATH = "file:///" + Launcher.class.getResource(API_RESPONSE_EXAMPLE_FILE).getPath();
    private static final String EXAMPLE_CITY_NAME = "Warszawa";
    private ClientAndServer mockServer;

    @BeforeEach
    void startServer() {
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

    @AfterEach
    void stopServer() {
        mockServer.stop();
    }

    @Test
    void shouldLoadWeatherData() {
        //given
        WeatherDataClient dataClient = mock(WeatherDataClient.class);
        given(dataClient.getMainAPIPart()).willReturn(API_RESPONSE_EXAMPLE_PATH);
        given(dataClient.getAdditionalAPIPart()).willReturn("");

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);

        //then
        verify(dataClient).loadWeatherData(EXAMPLE_CITY_NAME);
    }

    /*@Test
    void weatherDataResultShouldNotBeEmpty() throws IOException {
        //given
        WeatherDataClient dataClient = mock(WeatherDataClient.class);
        URL url = mock(URL.class);
        HttpURLConnection conn = mock(HttpURLConnection.class);
        given(dataClient.getMainAPIPart()).willReturn(API_RESPONSE_EXAMPLE_PATH);
        given(dataClient.getAdditionalAPIPart()).willReturn("");
        given(dataClient.getApiKey()).willReturn("");

        //when
        dataClient.loadWeatherData(EXAMPLE_CITY_NAME);
        String result = dataClient.getResult();

        //then
        assertThat(result, notNullValue());
    }*/

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

    private String getExampleResultFromApi() {
        File file = new File(API_RESPONSE_EXAMPLE_PATH);
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
    }
}