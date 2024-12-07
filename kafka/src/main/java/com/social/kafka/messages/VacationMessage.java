package com.social.kafka.messages;

import com.social.kafka.messages.contract.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public final class VacationMessage implements KafkaMessage {

    private final String applicant;

    private final String startDate;

    private final String endDate;

    private final String submittedOn;

    private final boolean halfDay;

    private final boolean approved;

    private final String document;
}
