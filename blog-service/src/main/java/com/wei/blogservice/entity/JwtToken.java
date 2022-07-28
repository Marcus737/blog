package com.wei.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 凌雪
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken implements Serializable {
    private static final long serialVersionUID = 42L;

    private String token;
    private String refreshToken;
}
