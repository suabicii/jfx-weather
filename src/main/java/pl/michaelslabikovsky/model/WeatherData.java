package pl.michaelslabikovsky.model;

import pl.michaelslabikovsky.utils.DotenvLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherData {

    private URL url;
    private String CityName;
    private String result;

    private int responseCode;
    private final static String API_KEY = DotenvLoader.loadEnvVariable("API_KEY");

    public WeatherData(String cityName) throws MalformedURLException {
        this.url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=" + API_KEY + "&lang=pl");
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            this.responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResult() throws IOException {
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                result += sc.nextLine();
            }
            return result;
        }
    }

    public void setResult(String result) {
        this.result = result;
    }
}
