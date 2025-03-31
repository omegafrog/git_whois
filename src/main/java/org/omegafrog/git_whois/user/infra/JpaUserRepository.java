package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<GithubUser, String> {
	GithubUser findByMetaData_GithubId(GithubId metaDataGithubId);

	boolean existsByMetaData_GithubId(GithubId metaDataGithubId);

}
