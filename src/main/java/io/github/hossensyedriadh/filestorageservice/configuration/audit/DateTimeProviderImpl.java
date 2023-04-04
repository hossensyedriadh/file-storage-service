package io.github.hossensyedriadh.filestorageservice.configuration.audit;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class DateTimeProviderImpl implements DateTimeProvider {
    @Override
    @NonNull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDateTime.now(Clock.systemDefaultZone()));
    }
}
