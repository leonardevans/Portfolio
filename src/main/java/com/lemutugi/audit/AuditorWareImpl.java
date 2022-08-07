package com.lemutugi.audit;

import com.lemutugi.model.User;
import com.lemutugi.security.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorWareImpl implements AuditorAware<Long> {
    @Autowired
    private AuthUtil authUtil;

    @Override
    public Optional<Long> getCurrentAuditor() {
        User loggedInUser = authUtil.getLoggedInUser();

        if (loggedInUser == null) return Optional.empty();

        return Optional.ofNullable(loggedInUser.getId());
    }
}
