package com.example.rentiaserver.constants;

public class EndpointConstants {
    public static final String REGISTER_USER_ENDPOINT = "/register";
    public static final String LOGOUT_ENDPOINT = "/logout";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT = "/normal/add";
    public static final String REMOVE_ANNOUNCEMENT_ENDPOINT = "/delete";
    public static final String ADD_FEEDBACK_ENDPOINT = "/add";
    public static final String DELETE_FEEDBACK_ENDPOINT = "/{id}/delete";
    public static final String EDIT_FEEDBACK_ENDPOINT = "/{id}/edit";
    public static final String EDIT_USER_ENDPOINT = "/edit";
    private EndpointConstants() {}
}
