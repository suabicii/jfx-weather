package pl.michaelslabikovsky.model;

import java.net.MalformedURLException;

public class Location extends ApiData {

    private final static String MAIN_API_PART = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private final static String ADDITIONAL_API_PART = "&limit=5";
    private final String foundCity;

    public Location(String searchedCity) throws MalformedURLException {
        super(searchedCity);
        this.foundCity = "";
    }

    public Location(String searchedCity, String foundCity) throws MalformedURLException {
        super(searchedCity);
        this.foundCity = foundCity;
    }

    public String getFoundCity() {
        return foundCity;
    }

    @Override
    protected String getMainAPIPart() {
        return MAIN_API_PART;
    }

    @Override
    protected String getAdditionalAPIPart() {
        return ADDITIONAL_API_PART;
    }
}
