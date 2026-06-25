package org.leeanh.TeffaCoreAPI.diagnostic;

import java.util.Collection;

public interface DiagnosticService {

    void registerProvider(
            DiagnosticProvider provider
    );

    void unregisterProvider(
            DiagnosticProvider provider
    );

    Collection<DiagnosticProvider> getProviders();

    DiagnosticProvider getProvider(
            String providerId
    );

    Collection<DiagnosticIssue> getIssues();

    DiagnosticStatus getStatus(
            DiagnosticProvider provider
    );
}