package com.api.TaveShot.global.util;

import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidator {
    public static Long extractNumberFromBojString(String input) {
        Pattern pattern = Pattern.compile("백준 (\\d+)번");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        throw new ApiException(ErrorType._PROBLEM_NOT_FOUND);
    }

    public static Long extractNumberFromRecommendString(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            throw new ApiException(ErrorType._PROBLEM_NOT_FOUND);
        }
    }
}
