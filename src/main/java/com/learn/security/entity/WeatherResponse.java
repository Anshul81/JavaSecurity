package com.learn.security.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class WeatherResponse {

    public Location location;
    public Current current;
    public Condition condition;

    @Getter
    @Setter
    public static class Condition {
        public String text;
        public String icon;
        public int code;
    }
    @Getter
    @Setter
    public static class Current {
        @JsonProperty("temp_c")
        public double tempC;
        public int is_day;
        public Condition condition;
        public int humidity;
        @JsonProperty("feelslike_c")
        public double feelsLikeC;
    }
    @Getter
    @Setter
    public static class Location {
        public String name;
        public String region;
        public String country;
        public String localtime;
    }

}