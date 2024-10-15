package model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    Duration duration;
}
