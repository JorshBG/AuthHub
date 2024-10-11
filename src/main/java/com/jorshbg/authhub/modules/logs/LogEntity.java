package com.jorshbg.authhub.modules.logs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Class for store log events in the system like sign-in or crud actions
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "syslog")
public class LogEntity {

    public LogEntity(UUID id, LogActions action, String description, UserEntity user, ZonedDateTime timestamp){
        this.id = id;
        this.timestamp = timestamp;
        this.description = description;
        this.byUser = user;
        this.action = action.getAction();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Action type this is stored as string but the constructor indicates that you need to use {@link LogActions} enumeration
     */
    @NotBlank
    @Size(max = 15)
    private String action;

    /**
     * Details about the log actions produced
     */
    @NotBlank
    @Size(max = 200)
    private String description;

    /**
     * Relation with the user that do the log
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "by_user", referencedColumnName = "id")
    private UserEntity byUser;

    /**
     * Date and time about the log
     */
    @NotNull
    private ZonedDateTime timestamp;

    /**
     * Default property to return the relation data about the user
     * @return {@link LinkedHashMap} User representation with username and email only
     */
    @JsonProperty("user")
    public LinkedHashMap<String, Object> getUser(){
        LinkedHashMap<String, Object> user = new LinkedHashMap<>();
        user.put("username", byUser.getUsername());
        user.put("email", byUser.getEmail());
        return user;
    }

}
