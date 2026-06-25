package org.leeanh.TeffaCoreAPI.diagnostic;

public class DiagnosticIssue {

    private final String id;
    private final DiagnosticCategory category;
    private final DiagnosticSeverity severity;
    private final String message;
    private final long timestamp;
    private final String recommendedFix;

    public DiagnosticIssue(
            String id,
            DiagnosticCategory category,
            DiagnosticSeverity severity,
            String message,
            String recommendedFix
    ) {
        this.id = id;
        this.category = category;
        this.severity = severity;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.recommendedFix = recommendedFix;
    }

    public String getId() {
        return id;
    }

    public DiagnosticCategory getCategory() {
        return category;
    }

    public DiagnosticSeverity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getRecommendedFix() {
        return recommendedFix;
    }
}