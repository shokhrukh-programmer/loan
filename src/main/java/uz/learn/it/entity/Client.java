package uz.learn.it.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.enums.Role;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "clients_id_seq", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private long id;

    private String firstName;

    private String lastName;

    private String passportInfo;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}