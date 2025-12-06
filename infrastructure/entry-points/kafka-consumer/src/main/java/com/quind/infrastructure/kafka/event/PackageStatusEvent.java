package com.quind.infrastructure.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageStatusEvent {

    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("new_status")
    private String newStatus;

    @JsonProperty("old_status")
    private String oldStatus;

    @JsonProperty("changed_by")
    private String changedBy;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("correlation_id")
    private String correlationId;

    @JsonProperty("reason")
    private String reason;
}