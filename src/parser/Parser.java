package parser;

import lexer.Lexer;
import structures.*;
import structures.Node;
import token.Token;
import token.TokenType;

import java.util.LinkedList;
import java.util.List;


public class Parser {
    private Lexer lexer;
    private Program program;
    private Token bufferedToken;

    public Parser(final Lexer lexer) {
        this.lexer = lexer;
    }

    private Token accept(final TokenType... tokenTypes) throws Exception {
        Token token;
        if (bufferedToken != null) {
            token = bufferedToken;
            bufferedToken = null;
        } else {
            token = lexer.getNextToken();
        }
        if (isAcceptable(token, tokenTypes)) {
            return token;
        } else {
            final StringBuilder sb = new StringBuilder()
                    .append("Unexpected token: ")
                    .append(token.getstringValue())
                    .append(" in line: ")
                    .append(token.getPosition())
                    .append(" Expected: ");
            for (final TokenType tokenType : tokenTypes) {
                sb.append(tokenType + " ");
            }
            System.out.println(sb.toString());
            throw new Exception(sb.toString());
        }
    }

    private boolean checkNextToken(final TokenType... tokenTypes) {
        if (bufferedToken == null) {
            bufferedToken = lexer.getNextToken();
        }
        return isAcceptable(bufferedToken, tokenTypes);
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        for (final TokenType tokenType : tokenTypes) {
            if (tokenType.equals(token.getTokenType())) {
                return true;
            }
        }
        return false;
    }

    public Program parse() throws Exception {
        bufferedToken = null;
        program = new Program();
        program.setConfigBlock(parseConfigBlock());
        Function function;
        while ((function = parseFunction()) != null) {
            if (program.getFunctions().get(function.getName()) != null)
                throw new Exception("Redefinition of function '" + function.getName() + "'");
            program.addFunction(function);
        }
        return program;
    }

    private ConfigBlock parseConfigBlock() throws Exception{
        ConfigBlock configBlock = new ConfigBlock();
        if (!checkNextToken(TokenType.DEF_CURRENCY))
            return null;

        accept(TokenType.DEF_CURRENCY);
        configBlock.setDefaultCurrency(accept(TokenType.ID).getstringValue());
        configBlock.addCurrency(configBlock.getDefaultCurrency(), 1);
        while (checkNextToken(TokenType.ID)){
            Token currency = accept(TokenType.ID);
            Token exchangeRate = accept(TokenType.NUMBER);
            if (configBlock.getExchangeRate().containsKey(currency.getstringValue())){
                throw new Exception("Redefinifion of currency '" + currency.getstringValue() + "'");
            }
            configBlock.addCurrency(currency.getstringValue(), exchangeRate.getDoubleValue());
        }
        return configBlock;
    }

    private Function parseFunction() throws Exception {
        Function function = new Function();
        Token startToken = accept(TokenType.FUNCTION, TokenType.END_OF_FILE);
        if (startToken.getTokenType() != TokenType.FUNCTION)
            return null;

        final Token functionName = accept(TokenType.ID);
        function.setName(functionName.getstringValue());
        function.setParameters(parseParameters());
        function.setStatementBlock(parseStatementBlock());
        return function;
    }

    private List<String> parseParameters() throws Exception {
        List<String> parameters = new LinkedList<>();
        accept(TokenType.PARENT_OPEN);
        Token tempToken = accept(TokenType.PARENT_CLOSE, TokenType.ID);
        if (tempToken.getTokenType() != TokenType.PARENT_CLOSE) {
            parameters.add(tempToken.getstringValue());
            while (true) {
                tempToken = accept(TokenType.PARENT_CLOSE, TokenType.COMMA);
                if (tempToken.getTokenType() == TokenType.PARENT_CLOSE) {
                    break;
                }
                tempToken = accept(TokenType.ID);
                parameters.add(tempToken.getstringValue());
            }
        }

        return parameters;
    }

    private StatementBlock parseStatementBlock() throws Exception {
        StatementBlock block = new StatementBlock();
        accept(TokenType.BRACKET_OPEN);
        while (checkNextToken(TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ID, TokenType.PRINT)) {
            Token tempToken = bufferedToken;
            switch (tempToken.getTokenType()) {
                case IF:
                    block.addInstruction(parseIfStatement());
                    break;
                case WHILE:
                    block.addInstruction(parseWhileStatement());
                    break;
                case RETURN:
                    block.addInstruction(parseReturnStatement());
                    break;
                case PRINT:
                    block.addInstruction(parsePrintStatement());
                    break;
                case ID:
                    block.addInstruction(parseAssignStatementtOrFunnCall());
                    break;
                default:
                    break;
            }
        }
        accept(TokenType.BRACKET_CLOSE);
        return block;
    }

    private Node parsePrintStatement() throws Exception {
        PrintStatement printStatement = new PrintStatement();
        accept(TokenType.PRINT);
        accept(TokenType.PARENT_OPEN);
        Token token = accept(TokenType.ID);
        Variable parameter = new Variable();
        parameter.setName(token.getstringValue());
        printStatement.addParameter(parameter);
        accept(TokenType.PARENT_CLOSE);
        accept(TokenType.SEMICOLON);
        return printStatement;
    }

    private Node parseAssignStatementtOrFunnCall() throws Exception {
        Token tempToken = accept(TokenType.ID);
        Node instruction = parseFunnCall(tempToken.getstringValue());
        if (instruction == null) {
            final AssingStatement assingStatement = new AssingStatement();
            final Variable variable = new Variable();
            variable.setName(tempToken.getstringValue());
            assingStatement.setVariable(variable);
            accept(TokenType.ASSIGN);
            assingStatement.setValue(parseAdditiveExpression());
            instruction = assingStatement;
        }
        accept(TokenType.SEMICOLON);
        return instruction;
    }

    private Node parseFunnCall(final String funnName) throws Exception {
        if (!checkNextToken(TokenType.PARENT_OPEN)) {
            return null;
        }
        FunCall funnCall = new FunCall();
        funnCall.setName(funnName);
        accept(TokenType.PARENT_OPEN);
        if (checkNextToken(TokenType.PARENT_CLOSE)) {
            accept(TokenType.PARENT_CLOSE);
        } else {
            while (true) {
                funnCall.addArgument(parseAdditiveExpression());
                if (checkNextToken(TokenType.PARENT_CLOSE)) {
                    accept(TokenType.PARENT_CLOSE);
                    break;
                } else if (checkNextToken(TokenType.COMMA)) {
                    accept(TokenType.COMMA);
                } else {
                    accept(TokenType.PARENT_CLOSE, TokenType.COMMA);
                }
            }
        }
        return funnCall;
    }

    private Node parseAdditiveExpression() throws Exception {
        AdditiveExpression expression = new AdditiveExpression();
        expression.addOperand(parseMultiplicativeExpression());
        while (checkNextToken(TokenType.ADD, TokenType.SUB)) {
            Token token = accept(TokenType.ADD, TokenType.SUB);
            expression.addOperator(token.getTokenType());
            expression.addOperand(parseMultiplicativeExpression());
        }
        return  expression;
    }

    private MuliplicativeExpression parseMultiplicativeExpression() throws Exception {
        MuliplicativeExpression expression = new MuliplicativeExpression();
        expression.addOperand(parseConvertExpression());
        while (checkNextToken(TokenType.MUL, TokenType.DIV)) {
            Token token = accept(TokenType.MUL, TokenType.DIV);
            expression.addOperator(token.getTokenType());
            expression.addOperand(parseConvertExpression());
        }
        return expression;
    }

    private ConvertExpression parseConvertExpression() throws Exception{
        ConvertExpression convertExpression = new ConvertExpression();
        convertExpression.setOperand(parsePrimaryExpression());
        convertExpression.setCurrencies(parseCurrencies());
        return convertExpression;
    }

    private List<String> parseCurrencies() throws Exception{
        List<String> currencies = new LinkedList<>();
        while (checkNextToken(TokenType.ID)){
            Token currency = accept(TokenType.ID);
            currencies.add(currency.getstringValue());
        }
        return currencies;
    }

    private PrimaryExpression parsePrimaryExpression() throws Exception {
        Node node = null;
        if (checkNextToken(TokenType.PARENT_OPEN)) {
            accept(TokenType.PARENT_OPEN);
            node = parseAdditiveExpression();
            accept(TokenType.PARENT_CLOSE);
            return new PrimaryExpression(node);
        } else if (checkNextToken(TokenType.ID)) {
            node = parseVariableOrFunCall();
        } else {
            node = parseLiteral();
        }
        return new PrimaryExpression(node);
    }

    private Node parseVariableOrFunCall() throws Exception {
        Token tempToken = accept(TokenType.ID);
        Node instruction = parseFunnCall(tempToken.getstringValue());
        if (instruction == null) {
            Variable variable = new Variable();
            variable.setName(tempToken.getstringValue());
            instruction = variable;
        }
        return instruction;
    }

    private Node parseReturnStatement() throws Exception {
        ReturnStatement returnStatement = new ReturnStatement();
        accept(TokenType.RETURN);
        returnStatement.setReturnValue(parseAdditiveExpression());
        accept(TokenType.SEMICOLON);
        return returnStatement;
    }

    private Node parseWhileStatement() throws Exception {
        WhileStatement whileStatement = new WhileStatement();
        accept(TokenType.WHILE);
        accept(TokenType.PARENT_OPEN);
        whileStatement.setCondition(parseCondition());
        accept(TokenType.PARENT_CLOSE);
        whileStatement.setStatementBlock(parseStatementBlock());
        return whileStatement;
    }

    private Node parseIfStatement() throws Exception {
        IfStatement ifStatement = new IfStatement();
        accept(TokenType.IF);
        accept(TokenType.PARENT_OPEN);
        ifStatement.setCondition(parseCondition());
        accept(TokenType.PARENT_CLOSE);
        ifStatement.setTrueBlock(parseStatementBlock());
        if (checkNextToken(TokenType.ELSE)) {
            accept(TokenType.ELSE);
            ifStatement.setElseBlock(parseStatementBlock());
        }
        return ifStatement;
    }

    private Condition parseCondition() throws Exception {
        Condition condition = new Condition();
        condition.addOperand(parseAndCondition());
        while (checkNextToken(TokenType.OR)) {
            accept(TokenType.OR);
            condition.setOperator(TokenType.OR);
            condition.addOperand(parseAndCondition());
        }
        return condition;
    }

    private Node parseAndCondition() throws Exception {
        Condition andCondition = new Condition();
        andCondition.addOperand(parseRelationalCondition());
        while (checkNextToken(TokenType.AND)) {
            accept(TokenType.AND);
            andCondition.setOperator(TokenType.AND);
            andCondition.addOperand(parseRelationalCondition());
        }
        return andCondition;
    }

    private Node parseRelationalCondition() throws Exception {
        Condition relationalCondition = new Condition();
        relationalCondition.addOperand(parsePrimaryCondition());
        while (checkNextToken(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER,
                              TokenType.GREATER_EQUALS,TokenType.EQUALS, TokenType.NOT_EQUALS)) {
            final Token token = accept(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER,
                                       TokenType.GREATER_EQUALS, TokenType.EQUALS, TokenType.NOT_EQUALS);
            relationalCondition.setOperator(token.getTokenType());
            relationalCondition.addOperand(parsePrimaryCondition());
        }
        return relationalCondition;
    }

    private Node parsePrimaryCondition() throws Exception {
        Condition primaryCondition = new Condition();
        if (checkNextToken(TokenType.UNARY)) {
            accept(TokenType.UNARY);
            primaryCondition.setNegated(true);
        }
        if (checkNextToken(TokenType.PARENT_OPEN)) {
            accept(TokenType.PARENT_OPEN);
            primaryCondition.addOperand(parseCondition());
            accept(TokenType.PARENT_CLOSE);
        } else if (checkNextToken(TokenType.ID)) {
            primaryCondition.addOperand(parseVariable());
        } else {
            primaryCondition.addOperand(parseLiteral());
        }
        return primaryCondition;
    }

    private Literal parseLiteral() throws Exception {
        Literal literal = new Literal();
        boolean isNegative = false;
        if (checkNextToken(TokenType.SUB)) {
            accept(TokenType.SUB);
            isNegative = true;
        }
        final Token token = accept(TokenType.NUMBER);
        double value = token.getDoubleValue();
        if (isNegative)
            value *= -1;
        literal.setValue(value);

        return literal;
    }

    private Variable parseVariable() throws Exception {
        Variable variable = new Variable();
        Token token = accept(TokenType.ID);
        variable.setName(token.getstringValue());

        return variable;
    }
}
