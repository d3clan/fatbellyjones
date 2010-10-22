package com.viviquity.core.security;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextFacade {

    public SecurityContext getContext();

    public void setContext(SecurityContext securityContext);

}
