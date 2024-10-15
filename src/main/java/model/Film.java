package model;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
public class Film {

    int id;
    String name;
    String description;
    Instant releaseDate;
    Duration duration;
}
