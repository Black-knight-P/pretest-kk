package com.pretest.dott.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommandDto {

    public static class Login {
        @ToString
        @Getter
        public static class Request {
            private String email;
            private String password;
        }
        @ToString
        @Getter
        @Builder
        public static class Response {
            private String token;
        }
    }


    @ToString
    @Getter
    public static class Register {
        private String email;
        private String password;
        private String name;
    }

}
