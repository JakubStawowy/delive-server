package com.example.rentiaserver.constants;

public class EndpointConstants {
    public static final String INDEX_ENDPOINT = "";
    public static final String INDEX_ENDPOINT_SLASH = "/";
    public static final String REGISTER_USER_ENDPOINT = "/register";
    public static final String LOGOUT_ENDPOINT = "/logout";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String BLOCKED_ANNOUNCEMENTS_ENDPOINT = "/blocked";
    public static final String USER_ANNOUNCEMENTS_ENDPOINT = "/user/{id}";
    public static final String ANNOUNCEMENT_ENDPOINT = "/{id}";
    public static final String FILTERED_ANNOUNCEMENTS_ENDPOINT = "/filter";
    public static final String ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT = "/normal/add";
    public static final String ADD_DELIVERY_ANNOUNCEMENTS_ENDPOINT = "/delivery/add";
    public static final String EDIT_ANNOUNCEMENT_ENDPOINT = "/{id}/edit";
    public static final String BLOCK_ANNOUNCEMENT_ENDPOINT = "/{id}/block";
    public static final String UNLOCK_ANNOUNCEMENT_ENDPOINT = "/{id}/unlock";
    public static final String REMOVE_ANNOUNCEMENT_ENDPOINT = "/{id}/remove";
    public static final String USER_FEEDBACK_ENDPOINT = "/user/{id}";
    public static final String ADD_FEEDBACK_ENDPOINT = "/add";
    public static final String DELETE_FEEDBACK_ENDPOINT = "/{id}/delete";
    public static final String EDIT_FEEDBACK_ENDPOINT = "/{id}/edit";
    public static final String USER_ENDPOINT = "/{id}";
    public static final String EDIT_USER_ENDPOINT = "/{id}/edit";
    private EndpointConstants() {}
}
