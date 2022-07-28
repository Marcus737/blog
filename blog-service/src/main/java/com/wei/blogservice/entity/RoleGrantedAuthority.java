package com.wei.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 凌雪
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleGrantedAuthority implements GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return role.getRoleName();
    }
}
