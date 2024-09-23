package com.jorshbg.authhub.modules.logs;

import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "syslog")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(max = 15)
    private String action;

    @NotBlank
    @Size(max = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "by_user", referencedColumnName = "id")
    private UserEntity byUser;

    @NotNull
    private String timestamp;

}
