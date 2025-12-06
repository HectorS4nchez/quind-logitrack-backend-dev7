package com.quind.infrastructure.kafka.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quind.domain.exception.KafkaMappingException;
import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.usecase.PackageUseCase;
import com.quind.infrastructure.kafka.event.PackageStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackageStatusEventKafkaListener {

    private final ObjectMapper objectMapper;
    private final PackageUseCase packageUseCase;

    @RetryableTopic(
            attempts = "#{'${retryconfig.retry.maxRetryAttempts}'}",
            autoCreateTopics = "#{'${retryconfig.retry.autoCreateRetryTopics}'}",
            backoff = @Backoff(
                    delayExpression = "#{'${retryconfig.retry.retryIntervalMilliseconds}'}",
                    multiplierExpression = "#{'${retryconfig.retry.retryBackoffMultiplier}'}"
            ),
            dltTopicSuffix = ".packages.DLT",
            retryTopicSuffix = ".packages-retry",
            exclude = {KafkaMappingException.class},
            timeout = "#{'${retryconfig.retry.maxRetryDurationMilliseconds}'}",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            id = "PackageStatusEvents",
            topics = "${retryconfig.topics.package-status}",
            containerFactory = "packageStatusContainerFactory"
    )
    public void handlePackageStatusChange(String message, Acknowledgment acknowledgment) throws JsonProcessingException {

        try {
            PackageStatusEvent event = objectMapper.readValue(message, PackageStatusEvent.class);
            if (!packageUseCase.packageExists(event.getTrackingId())) {
                acknowledgment.acknowledge();
                return;
            }
            PackageStatus newStatus = PackageStatus.valueOf(event.getNewStatus());
            packageUseCase.changePackageStatus(event.getTrackingId(), newStatus);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[Trace-id: {}] - Error processing package status event", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }


}