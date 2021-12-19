package com.example.rentiaserver;

public class ApplicationConstants {

    public static class Urls {
        public static final String BASE_ENDPOINT_PREFIX = "/api";
        private Urls() {
            throw new IllegalStateException();
        }
    }

    public static class Origins {
//        public static final String LOCALHOST_ORIGIN = "http://localhost:3000";
        public static final String LOCALHOST_ORIGIN = "https://delive-server-1.herokuapp.com";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.0.115:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.2.109:3000";

        private Origins() {
            throw new IllegalStateException();
        }
    }

    public static class Sql {
        public static final String ORDER_BY_CREATED_AT = "ORDER BY CREATED_AT DESC";
        public static final String NOT_ARCHIVED = "IS_ARCHIVED = FALSE";
        private Sql() {
            throw new IllegalStateException();
        }
    }

    public static class Security {
        public static final String ROLE_PREFIX = "role";
        public static final String ID_PREFIX = "userId";
        private Security() {
            throw new IllegalStateException();
        }
    }

    private ApplicationConstants() {
        throw new IllegalStateException();
    }
}
