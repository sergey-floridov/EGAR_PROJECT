package ru.pnz.floridov.RestDemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotBlank
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Column(name = "last_name")
    private String lastName;


    @NotBlank
    @Column(name = "address")
    private String address;


    @Column(name = "phone")
    private String phone;


    @Column(name = "email")
    @Email
    private String email;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;


    @OneToMany(mappedBy = "client", fetch=FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<DebetAccount> debetAccounts;


    @OneToMany(mappedBy = "client", fetch=FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<CreditProduct> credits = new ArrayList<>();

}