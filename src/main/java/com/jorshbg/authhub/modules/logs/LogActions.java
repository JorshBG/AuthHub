package com.jorshbg.authhub.modules.logs;

import lombok.Getter;

@Getter
public enum LogActions {

    INSERT("CREATED"),
    UPDATE("UPDATED"),
    DELETE("DELETE"),
    CHANGE_STATUS("STATUS_CHANGED"),
    SIGN_IN("LOGIN"),
    SIGN_OUT("LOGOUT"),
    RECOVER_PASSWORD("PASSWORD_CHANGE"),
    PERMANENT_TOKEN("PERMANENT_JWT");

    private final String action;

    LogActions(String action) {
        this.action = action;
    }
}
