package org.leeanh.TeffaCoreAPI.api.diagnostic;

import org.leeanh.TeffaCoreAPI.core.diagnostic.DiagnosticIssue;
import org.leeanh.TeffaCoreAPI.core.diagnostic.DiagnosticProvider;
import org.leeanh.TeffaCoreAPI.core.diagnostic.DiagnosticStatus;

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