package one.digitalinnovation.personapi.entity;

import one.digitalinnovation.personapi.dto.request.PhoneDTO;

import java.util.List;

public class Person {

    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    private String birthDate;

    private List<Phone> phones;
}
