package io.bootify.my_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PropertyDTO {

    private Long id;

    @NotNull
    private PropertyType type;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    private Integer rooms;

    @NotNull
    private Boolean active;

    @NotNull
    private Long owner;

}
