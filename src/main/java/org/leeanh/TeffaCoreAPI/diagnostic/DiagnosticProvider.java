package org.leeanh.TeffaCoreAPI.diagnostic;

import java.util.List;

public interface DiagnosticProvider {

    String getProviderId();

    String getPluginName();

    String getPluginVersion();

    DiagnosticCategory getCategory();

    List<DiagnosticIssue> getIssues();
}