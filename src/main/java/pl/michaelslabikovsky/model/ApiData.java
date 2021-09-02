package pl.michaelslabikovsky.model;

import io.github.cdimascio.dotenv.DotenvException;
import pl.michaelslabikovsky.utils.DialogUtils;
import pl.michaelslabikovsky.utils.DotenvLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class ApiData {

    private URL url;
    private int responseCode;
    private final String apiKey;

    public ApiData() {
        DotenvLoader dotenvLoader = new DotenvLoader(".env");
        try {
            dotenvLoader.loadEnvFile();
        } catch (DotenvException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
        apiKey = dotenvLoader.getEnvVariable("API_KEY");
    }

    protected void connectToApi(String cityName, String mainAPIPart, String additionalAPIPart) throws MalformedURLException {
        this.url = new URL(mainAPIPart + cityName + "&appid=" + apiKey + additionalAPIPart);
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
            conn.connect();
            this.responseCode = conn.getResponseCode();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    public String getResult() throws IOException {
        if (responseCode != 200) {
            RuntimeException exception = new RuntimeException("HttpResponseCode: " + responseCode);
            DialogUtils.errorDialog(exception.getMessage());
            throw exception;
        } else {
            Scanner sc = new Scanner(url.openStream());
            StringBuilder result = new StringBuilder();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                byte[] bytes = line.getBytes();

                String utf8Bytes = new String(bytes, StandardCharsets.UTF_8);
                result.append(utf8Bytes);
            }
            sc.close();
            return result.toString();
        }
    }

    protected abstract String getMainAPIPart();
    protected abstract String getAdditionalAPIPart();
}
