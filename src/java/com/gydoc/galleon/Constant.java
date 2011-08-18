package com.gydoc.galleon;

/**
 *
 */
public interface Constant {

    public static final String USER_SESSION_KEY = "GALLEON.user";

    public static final String ERROR_COMMON_UNKNOWN = "00C000001"; // General error. Error details are unknown.
    public static final String ERROR_COMMON_DOMAIN_OBJECT_STALE = "00C000002"; // Domain Object is stale.
    public static final String ERROR_COMMON_INIT_TENANT = "00C000003"; // Cannot initialize spring for specified tenant

    public static final String BOCHECK_OK = "0";
    public static final String ERROR_00U000001 = "00U000001"; // user name is empty when create user
    public static final String USER_LOGIN_EMPTY = "00U000002";
    public static final String USER_USERNAME_EMPTY = "00U000003";

}
