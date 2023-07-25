package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 255)
    private String pass;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 20)
    private String phone;

    @NotNull
    private LocalDate birth;

    @NotNull
    @Size(max = 12)
    private String dni;

    @JsonIgnore
    private OffsetDateTime dateCreated;

    @JsonIgnore
    private OffsetDateTime lastUpdated;

}
