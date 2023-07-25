package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentDTO {

    private Long id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Double amount;

    @NotNull
    private Long contract;

}
