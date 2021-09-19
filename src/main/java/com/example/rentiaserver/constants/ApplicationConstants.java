package com.example.rentiaserver.constants;

public class ApplicationConstants {

    public static class Urls {
        public static final String BASE_API_URL = "/api";
        private Urls() {}
    }

    public static class Origins {
        public static final String LOCALHOST_ORIGIN = "http://localhost:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.0.115:3000";
//        public static final String LOCALHOST_ORIGIN = "http://192.168.2.103:3000";
        private Origins() {}
    }
    private ApplicationConstants() {}
}
