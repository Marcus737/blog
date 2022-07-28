package com.wei.blogservice.service;

/**
 * @author admin
 */
public interface PermissionService {

    /**
     * 123
     * @return
     */
    boolean hasPermission();

    /**
     * 21d
     * @param uri
     * @return
     */
    boolean hasPermission(String uri);

    boolean hasAuthority(String roleName);
}
