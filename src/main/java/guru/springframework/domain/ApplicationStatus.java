package guru.springframework.domain;

public enum ApplicationStatus {
    WAITINGFORCONTROL,
    MISSINGDOCUMENT,
    ACCEPTED,
    REJECTED,
    VERIFIED,
    CONFIRMED
}
