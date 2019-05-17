package com.github.ruifengho.simplesecurity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public final class SpringElCheckUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpringElCheckUtil.class);
    private static ExpressionParser PARSER = new SpelExpressionParser();

    public static boolean check(EvaluationContext context, String expression) {
        Boolean result = PARSER.parseExpression(expression).getValue(context, Boolean.class);
        logger.info("expression = {}, eval result = {}", expression, result);
        return result != null ? result : false;
    }
}
