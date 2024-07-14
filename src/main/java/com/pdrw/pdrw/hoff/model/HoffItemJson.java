package com.pdrw.pdrw.hoff.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonObject
public class HoffItemJson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonField(name = "id")
    private String id;
    @JsonField(name = "name")
    private String name;
    @JsonField(name = "image")
    private String image;

    public HoffItemJson setId(String id) {
        this.id = id;
        return this;
    }

    public HoffItemJson setName(String name) {
        this.name = name;
        return this;
    }

    public HoffItemJson setImage(String image) {
        this.image = image;
        return this;
    }
}
