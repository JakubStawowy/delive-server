package com.example.rentiaserver.base;

public class ApplicationConstants {

    public static class Urls {

        public static final String BASE_ENDPOINT_PREFIX = "/api";

        private Urls() {
            throw new IllegalStateException();
        }
    }

    public static class Origins {

        public static final String LOCALHOST_ORIGIN = "http://localhost:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.0.115:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.2.105:3000";

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

    public static class Properties {

        public static final String APPLICATION_PROPERTIES = "classpath:application.properties";

        public static final String PROPERTY_POSITION_STACK_API_KEY = "${positionStack.api.key}";

        public static final String PROPERTY_MAPQUEST_API_KEY = "${mapquest.api.key}";

        public static final String PROPERTY_MAPQUEST_API_URI = "${mapquest.api.uri}";

        public static final String PROPERTY_POSITION_STACK_API_URI = "${positionStack.api.uri}";

        public static final String PROPERTY_GEOCODING_SERVICE_LAYER = "${geocoding.layer}";

        private Properties() {
            throw new IllegalStateException();
        }
    }

    private ApplicationConstants() {
        throw new IllegalStateException();
    }
}
