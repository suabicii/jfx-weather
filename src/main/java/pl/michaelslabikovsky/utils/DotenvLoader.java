package pl.michaelslabikovsky.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {

    private final Dotenv dotenv = Dotenv.load();

    public String loadEnvVariable(String variableName) {
        return dotenv.get(variableName);
    }
}
