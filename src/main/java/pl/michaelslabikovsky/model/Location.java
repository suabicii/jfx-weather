package pl.michaelslabikovsky.model;

import java.net.MalformedURLException;

public class Location extends APIData {

    private final static String MAIN_API_PART = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private final static String ADDITIONAL_API_PART = "&limit=5";
    private String city = "";

    public Location(String cityName) throws MalformedURLException {
        super(cityName);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
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
