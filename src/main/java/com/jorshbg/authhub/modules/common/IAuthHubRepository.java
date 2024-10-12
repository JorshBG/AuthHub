package com.jorshbg.authhub.modules.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAuthHubRepository<T, ID> extends JpaRepository<T, ID> {
}
