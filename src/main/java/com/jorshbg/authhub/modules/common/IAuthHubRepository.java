package com.jorshbg.authhub.modules.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Default custom repository to access to the resources in database.
 * This repository can be used to add general methods.
 * Take care and only add methods with all compatibility among the entities
 * @param <T> Entity class
 * @param <ID> Data type of ID
 */
@NoRepositoryBean
public interface IAuthHubRepository<T, ID> extends JpaRepository<T, ID> {
}
