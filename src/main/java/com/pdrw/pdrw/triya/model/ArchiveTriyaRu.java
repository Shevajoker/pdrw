package com.pdrw.pdrw.triya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "archive_triya_ru")
public class ArchiveTriyaRu {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;
    @Column(name = "age")
    private Integer age;
    @Column(name = "url")
    private String url;
    @Column(name = "description")
    private String description;
    @Column(name = "count")
    private Integer count;

    @Transient
    private List<String> urls;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveTriyaRu that = (ArchiveTriyaRu) o;
        return Objects.equals(age, that.age) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, url);
    }
}
