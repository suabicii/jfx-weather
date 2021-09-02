package pl.michaelslabikovsky.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DotenvLoaderTest {

    private final static String TEMP_FILE_NAME = ".env-temp";
    private final static String TEMP_KEY = "a4df15c3943fe91408e1436c4fad4208ceb29b02"; //przerobione słowo „ziemniak” xD

    @AfterEach
    void deleteTemporaryFile() {
        File file = new File(TEMP_FILE_NAME);
        file.deleteOnExit();
    }

    @Test
    void shouldReturnEnvVariable() {
        //given
        writeTemporaryEnvFile();
        DotenvLoader dotenvLoader = new DotenvLoader(TEMP_FILE_NAME);

        //when
        dotenvLoader.loadEnvFile();
        String generatedKey = dotenvLoader.getEnvVariable("TEMP_KEY");

        //then
        assertThat(generatedKey, is(TEMP_KEY));
    }

    private void writeTemporaryEnvFile() {
        try {
            FileWriter fileWriter = new FileWriter(TEMP_FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("TEMP_KEY=" + TEMP_KEY);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}