package pl.michaelslabikovsky.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {

    public static String loadEnvVariable(String variableName) {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get(variableName);
    }
}
