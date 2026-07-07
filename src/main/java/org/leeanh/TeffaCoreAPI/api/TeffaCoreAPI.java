package org.leeanh.TeffaCoreAPI.api;

import org.leeanh.TeffaCoreAPI.api.profile.ProfileService;
import org.leeanh.TeffaCoreAPI.api.diagnostic.DiagnosticService;
import org.leeanh.TeffaCoreAPI.api.permisson.PermissionService;

public interface TeffaCoreAPI {

    ProfileService profileService();

    PermissionService permissionService();

    DiagnosticService diagnosticService();

}