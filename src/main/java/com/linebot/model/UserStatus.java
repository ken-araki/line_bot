package com.linebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus implements Serializable {
    private String userId;
    private String nextAction;
}
