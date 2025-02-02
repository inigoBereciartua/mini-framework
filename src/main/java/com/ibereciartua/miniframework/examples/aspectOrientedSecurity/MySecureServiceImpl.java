package com.ibereciartua.miniframework.examples.aspectOrientedSecurity;

import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.annotations.PostAuthorize;
import com.ibereciartua.miniframework.annotations.PreAuthorize;
import com.ibereciartua.miniframework.annotations.Scope;

@Component
@Scope
public class MySecureServiceImpl implements MySecuredService {

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostAuthorize("returnObject != null")
    public String secureMethod(String input) {
        return "Processed input: " + input;
    }
}
