package com.pretest.dott.security.utils;

import com.pretest.dott.common.enums.CustomErrorCode;
import com.pretest.dott.common.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class JwtSecurityUtils {

    public static void isValidRequest(Principal principal, String email) {
        if (!principal.getName().equals(email))
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
    }

    public static String getMemberEmail(Principal principal) {
        return principal.getName();
    }

    public static String getMemberEmail() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException npe) {
            throw new CustomException(CustomErrorCode.INVALID_REQUEST_JWT_INFO);
        }
    }
}
