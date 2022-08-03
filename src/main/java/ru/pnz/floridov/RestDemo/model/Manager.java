package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Manager")
public class Manager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "first_name")
    private String firstName;


    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Column(name = "last_name")
    private String last_name;


    @Column(name = "address")
    private String address;


    @Column(name = "phone")
    private int phone;

    @OneToMany(mappedBy = "manager", fetch=FetchType.LAZY)
    private List<Client> clients;



}
