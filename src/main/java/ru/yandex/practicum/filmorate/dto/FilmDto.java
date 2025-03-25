package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@Validated
public enum FilmDto {;
	private interface Id { @Min(value=0) Long getId(); }
    private interface Name { @NotBlank @Size(max=50) String getName(); }
    private interface Description { @Size(min = 0, max = 200) String getDescription(); }
    private interface ReleaseDate { @ThisAfter1895 LocalDate getReleaseDate();}
    private interface Duration { @Min(value=1) Integer getDuration();}
    private interface RatingName { @NotBlank @Size(max=50) String getRatingName();}

    public enum Request{;
        @Value public static class Create implements Name, Description, ReleaseDate, Duration, RatingName {
            String name;
            String description;
            LocalDate releaseDate;
            Integer duration;
            String ratingName;
        }
        
        @Value public static class Update implements Id, Name, Description, ReleaseDate, Duration, RatingName {
        	Long id;
        	String name;
            String description;
            LocalDate releaseDate;
            Integer duration;
            String ratingName;
        }
    }
  

    public enum Response{;
        @Value public static class Public implements Name, Description, ReleaseDate, Duration, RatingName {
        	String name;
            String description;
            LocalDate releaseDate;
            Integer duration;
            String ratingName;
        }

        @Value public static class Private implements Id, Name, Description, ReleaseDate, Duration, RatingName {
        	Long id;
        	String name;
            String description;
            LocalDate releaseDate;
            Integer duration;
            String ratingName;
        }
    }
}
