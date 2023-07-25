package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContractDTO {

    private Long id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    private Boolean valid;

    @NotNull
    private Long property;

    @NotNull
    private Long tenant;

}
