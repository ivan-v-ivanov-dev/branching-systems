package com.personal.vacation.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "storage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vacation {

    @Id
    private String id;

    @Indexed
    private String applicant;

    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate submittedOn;

    private boolean halfDay;

    private boolean approved;

    private String list;
}
