package com.subcodes.journalApp.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Location location;
    private Current current;

    @Getter
    @Setter
    private class AirQuality{
        private String co;
        private String no2;
        private String o3;
        private String so2;
        private String pm2_5;
        private String pm10;
        @JsonProperty("us-epa-index")
        private String usEpaIndex;
        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;
    }

    @Getter
    @Setter
    private class Astro{
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        private String moon_phase;
        private int moon_illumination;
    }

    @Getter
    @Setter
    private class Current{
        private String observation_time;
        private int temperature;
        private List<String> weather_descriptions;
        private Astro astro;
        private AirQuality air_quality;
        private int wind_speed;
        private int wind_degree;
        private String wind_dir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        private int uv_index;
        private int visibility;
        private String is_day;
    }

    @Getter
    @Setter
    private class Location{
        private String name;
        private String country;
        private String region;
    }

}
