package com.ipaylinks.test.mock.server.util.function;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class ExpressionParserUtils {
    public static final ExpressionParser parser = new SpelExpressionParser();
    public static final ParserContext templateParserContext =new TemplateParserContext();
}
