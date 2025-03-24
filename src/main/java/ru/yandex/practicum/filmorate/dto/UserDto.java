package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.Value;

import ru.yandex.practicum.filmorate.validation.Login;

@Validated
public enum UserDto {;
	    private interface Id { @Min(value=0) Long getId(); }
	    private interface UserEmail { @Email String getUserEmail(); }
	    private interface UserLogin { @Login String getUserLogin(); }
	    private interface Name { String getName(); }
	    private interface Birthday { @Past LocalDate getBirthday(); }

	    public enum Request{;
	        @Value public static class Create implements UserEmail, UserLogin, Name, Birthday {
	            String userEmail;
	            String userLogin;
	            String name;
	            LocalDate birthday;
	        }
	        
	        @Value public static class Update implements Id, UserEmail, UserLogin, Name, Birthday {
	        	Long id;
	        	String userEmail;
	            String userLogin;
	            String name;
	            LocalDate birthday;
	        }
	    }
	  

	    public enum Response{;
	        @Value public static class Public implements UserLogin, Name {
	            String userLogin;
	            String name;
	        }

	        @Value public static class Private implements Id, UserEmail, UserLogin, Name, Birthday {
	        	Long id;
	        	String userEmail;
	            String userLogin;
	            String name;
	            LocalDate birthday;
	        }
	    }
}
