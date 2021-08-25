package pl.michaelslabikovsky.model;

import pl.michaelslabikovsky.utils.DotenvLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class ApiData {

    private URL url;
    private String result = "";
    private int responseCode;
    private final String apiKey;

    public ApiData(String cityName) throws MalformedURLException {
        DotenvLoader dotenvLoader = new DotenvLoader();
        apiKey = dotenvLoader.loadEnvVariable("API_KEY");
        connectToAPI(cityName, getMainAPIPart(), getAdditionalAPIPart());
    }

    protected void connectToAPI(String cityName, String mainAPIPart, String additionalAPIPart) throws MalformedURLException {
        this.url = new URL(mainAPIPart + cityName + "&appid=" + apiKey + additionalAPIPart);
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
            conn.connect();
            this.responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() throws IOException {
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                String line = sc.nextLine();
                byte[] bytes = line.getBytes();

                String utf8Bytes = new String(bytes, StandardCharsets.UTF_8);
                this.result += utf8Bytes;
            }
            sc.close();
            return result;
        }
    }

    protected abstract String getMainAPIPart();
    protected abstract String getAdditionalAPIPart();
}