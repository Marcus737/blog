package com.wei.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 凌雪
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private List<Permission> permissions;
}
