package com.example.rentiaserver.constants;

public class ApplicationConstants {

    public static class Urls {
        public static final String BASE_API_URL = "/api";
        private Urls() {}
    }

    public static class Origins {
        public static final String LOCALHOST_ORIGIN = "http://localhost:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.0.115:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.2.104:3000";
        private Origins() {}
    }

    public static class Sql {
        public static final String ORDER_BY_CREATED_AT = "ORDER BY CREATED_AT DESC";
        public static final String NOT_ARCHIVED = "IS_ARCHIVED = FALSE";
    }

    public static class Security {
        public static final String ROLE_PREFIX = "role";
        public static final String ID_PREFIX = "userId";
        private Security() {}
    }

    private ApplicationConstants() {}
}
