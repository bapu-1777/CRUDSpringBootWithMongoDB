package com.example.demomongodabApi;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "Student")
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String id;

    @NotNull(message = "not be null")
    @Size(min = 2, max = 20, message = "Please enter your firstName in 2 to 20 chanractors")
    private String fristName;

    @NotNull(message = "not be null")
    @Size(min = 2, max = 20, message = "Please enter your lastName in 2 to 20 chanractors")
    private String lastName;
    private String email;
    private String gender;
    private Address address;
    private List<String> subjects;
    private BigDecimal spendInBook;
    private Date time;



}
