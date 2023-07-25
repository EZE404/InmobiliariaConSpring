package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDTO extends UserDTO {

    //private Long id;

    @NotNull
    private Boolean admin;

    @NotNull
    private LocalDate hireDate;

    @NotNull
    private Boolean active;

}
