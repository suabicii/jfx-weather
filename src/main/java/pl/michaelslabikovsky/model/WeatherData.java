package pl.michaelslabikovsky.model;

public class WeatherData extends ApiData {

    private final static String MAIN_API_PART = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private final static String ADDITIONAL_API_PART = "&lang=pl&units=metric&appid=";

    @Override
    protected String getMainAPIPart() {
        return MAIN_API_PART;
    }

    @Override
    protected String getAdditionalAPIPart() {
        return ADDITIONAL_API_PART;
    }
}
