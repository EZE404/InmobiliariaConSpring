package io.bootify.my_app.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class User {

        @Id
        @Column(nullable = false, updatable = false)
        @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence", allocationSize = 1, initialValue = 10000)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String email;

        @Column(nullable = false)
        private String pass;

        @Column(nullable = false, length = 50)
        private String firstName;

        @Column(nullable = false, length = 50)
        private String lastName;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false, length = 20)
        private String phone;

        @Column(nullable = false)
        private LocalDate birth;

        @Column(nullable = false, unique = true, length = 12)
        private String dni;

        @CreatedDate
        @Column(nullable = false, updatable = false)
        private OffsetDateTime dateCreated;

        @LastModifiedDate
        @Column(nullable = false)
        private OffsetDateTime lastUpdated;

}
