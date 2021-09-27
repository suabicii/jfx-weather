package pl.michaelslabikovsky.utils;

import io.github.cdimascio.dotenv.DotenvException;

public class DotenvClient {

    public static String getApiKey() {
        final String apiKey;
        DotenvLoader dotenvLoader = new DotenvLoader(".env");
        try {
            dotenvLoader.loadEnvFile();
        } catch (DotenvException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
        apiKey = dotenvLoader.getEnvVariable("API_KEY");
        return apiKey;
    }
}
