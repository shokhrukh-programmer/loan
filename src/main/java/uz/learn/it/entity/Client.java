package uz.learn.it.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    private String passportInfo;

    private String phoneNumber;

    private String role;
}
