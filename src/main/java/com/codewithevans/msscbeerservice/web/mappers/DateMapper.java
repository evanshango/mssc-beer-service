package com.codewithevans.msscbeerservice.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        return ts != null ? OffsetDateTime.of(
                ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC
        ) : null;
    }

    public Timestamp asTimeStamp(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? Timestamp.valueOf(
                offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        ) : null;
    }
}