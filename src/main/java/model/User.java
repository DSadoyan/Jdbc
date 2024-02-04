package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class User {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;
}
