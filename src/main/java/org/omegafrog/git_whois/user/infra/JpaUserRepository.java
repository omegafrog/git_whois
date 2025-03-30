package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, String> {
}
