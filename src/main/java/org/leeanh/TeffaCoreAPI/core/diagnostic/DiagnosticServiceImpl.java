package org.leeanh.TeffaCoreAPI.core.diagnostic;

import org.leeanh.TeffaCoreAPI.api.diagnostic.DiagnosticService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiagnosticServiceImpl
        implements DiagnosticService {

    private final List<DiagnosticProvider> providers =
            new ArrayList<>();

    @Override
    public DiagnosticProvider getProvider(
            String providerId
    ) {

        return providers.stream()
                .filter(provider ->
                        provider.getProviderId()
                                .equalsIgnoreCase(
                                        providerId
                                )
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<DiagnosticIssue> getIssues() {

        List<DiagnosticIssue> issues =
                new ArrayList<>();

        for (DiagnosticProvider provider :
                providers) {

            issues.addAll(
                    provider.getIssues()
            );
        }

        return issues;
    }

    @Override
    public void registerProvider(
            DiagnosticProvider provider
    ) {

        providers.add(provider);
    }

    @Override
    public void unregisterProvider(
            DiagnosticProvider provider
    ) {

        providers.remove(provider);
    }

    @Override
    public Collection<DiagnosticProvider> getProviders() {

        return List.copyOf(providers);
    }

    @Override
    public DiagnosticStatus getStatus(
            DiagnosticProvider provider
    ) {

        boolean hasWarning = false;

        for (DiagnosticIssue issue :
                provider.getIssues()) {

            if (issue.getSeverity()
                    == DiagnosticSeverity.ERROR) {

                return DiagnosticStatus.ERROR;
            }

            if (issue.getSeverity()
                    == DiagnosticSeverity.WARNING) {

                hasWarning = true;
            }
        }

        if (hasWarning) {
            return DiagnosticStatus.WARNING;
        }

        return DiagnosticStatus.HEALTHY;
    }
}