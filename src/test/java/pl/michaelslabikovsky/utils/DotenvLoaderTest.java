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

    private final String tempFileName = ".env-temp";
    private final String tempKey = "a4df15c3943fe91408e1436c4fad4208ceb29b02"; //przerobione słowo „ziemniak” xD

    @AfterEach
    void deleteTemporaryFile() {
        File file = new File(tempFileName);
        file.deleteOnExit();
    }

    @Test
    void shouldReturnEnvVariable() {
        //given
        writeTemporaryEnvFile();
        DotenvLoader dotenvLoader = new DotenvLoader(tempFileName);

        //when
        dotenvLoader.loadEnvFile();
        String generatedKey = dotenvLoader.getEnvVariable("TEMP_KEY");

        //then
        assertThat(generatedKey, is(tempKey));
    }

    private void writeTemporaryEnvFile() {
        try {
            FileWriter fileWriter = new FileWriter(tempFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("TEMP_KEY=" + tempKey);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}