package com.wei.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 凌雪
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Permission {

    private Integer permissionId;
    private String permissionName;
}
