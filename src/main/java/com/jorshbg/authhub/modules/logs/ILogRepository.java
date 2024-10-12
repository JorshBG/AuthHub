package com.jorshbg.authhub.modules.logs;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILogRepository extends IAuthHubRepository<LogEntity, UUID> {
}
