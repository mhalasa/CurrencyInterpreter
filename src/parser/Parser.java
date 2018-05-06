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
    private Token previousToken;

    public Parser(final Lexer lexer) {
        this.lexer = lexer;
    }

    public Program parse() throws Exception {
        resetBufferedToken();
        program = new Program();
        program.setConfigBlock(parseConfigBlock());
        Function lastParsedFunction;
        while ((lastParsedFunction = parseFunction()) != null) {
            program.addFunction(lastParsedFunction);
        }
        return program;
    }

    private Token accept(final TokenType... tokenTypes) throws Exception {
        Token token;
        if (isBufferedToken()) {
            token = previousToken;
            resetBufferedToken();
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

    private void resetBufferedToken() {
        previousToken = null;
    }

    private boolean isBufferedToken() {
        return previousToken != null;
    }

    private Token getBufferedToken() {
        return previousToken;
    }

    private boolean checkNextToken(final TokenType... tokenTypes) throws Exception {
        if (!isBufferedToken()) {
            previousToken = lexer.getNextToken();
        }
        return isAcceptable(previousToken, tokenTypes);
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        for (final TokenType tokenType : tokenTypes) {
            if (tokenType.equals(token.getTokenType())) {
                return true;
            }
        }
        return false;
    }

    private ConfigBlock parseConfigBlock() throws Exception{
        System.out.println("Start processing config block");
        ConfigBlock configBlock = new ConfigBlock();
        if (!checkNextToken(TokenType.DEF_CURRENCY))
            return null;

        accept(TokenType.DEF_CURRENCY);
        configBlock.setDefaultCurrency(accept(TokenType.ID).getstringValue());
        while (checkNextToken(TokenType.ID)){
            Token currency = accept(TokenType.ID);
            Token exchangeRate = accept(TokenType.NUMBER);
            if (configBlock.getExchangeRate().containsKey(currency.getstringValue())){
                throw new Exception("Redefinifion of currency \"" + currency.getstringValue() + "\'");
            }
            configBlock.addCurrency(currency.getstringValue(), exchangeRate.getDoubleValue());
        }
        return configBlock;
    }

    private Function parseFunction() throws Exception {
        System.out.println("Start processing function");
        final Function function = new Function();
        final Token startToken = accept(TokenType.FUNCTION, TokenType.END_OF_FILE);
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
        System.out.println("Parsing statement block...");
        final StatementBlock block = new StatementBlock();
        accept(TokenType.BRACKET_OPEN);
        while (true) {
            if (!checkNextToken(TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ID)) {
                break;
            }
            final Token tempToken = getBufferedToken();
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
                case ID:
                    block.addInstruction(parseAssignStatementtOrFunnCall());
                    break;
                case PRINT:
                    block.addInstruction(parsePrintStatement());
                    break;
                default:
                    break;
            }
        }
        accept(TokenType.BRACKET_CLOSE);
        return block;
    }

    private Node parsePrintStatement() throws Exception{
        PrintStatement printStatement = new PrintStatement();
        accept(TokenType.PRINT);
        accept(TokenType.PARENT_OPEN);
            Token token = accept(TokenType.ID);
            Variable parameter = new Variable();
            parameter.setName(token.getstringValue());
            printStatement.addParameter(parameter);
            return printStatement;
    }

    private Node parseAssignStatementtOrFunnCall() throws Exception {
        System.out.println("Parsing assignment or funn call...");
        final Token tempToken = accept(TokenType.ID);
        Node instruction = parseFunnCall(tempToken.getstringValue());
        if (instruction == null) {
            final AssingStatement assingStatement = new AssingStatement();
            final Variable variable = new Variable();
            variable.setName(tempToken.getstringValue());
            assingStatement.setVariable(variable);
            accept(TokenType.ASSIGN);
            assingStatement.setValue(parseExpression());
            instruction = assingStatement;
        }
        accept(TokenType.SEMICOLON);
        return instruction;
    }

    private Node parseFunnCall(final String funnName) throws Exception {
        System.out.print("Parsing funn call...");
        if (!checkNextToken(TokenType.PARENT_OPEN)) {
            System.out.println("It is not a funn call");
            return null;
        }
        final FunCall funnCall = new FunCall();
        funnCall.setName(funnName);
        accept(TokenType.PARENT_OPEN);
        if (checkNextToken(TokenType.PARENT_CLOSE)) {
            accept(TokenType.PARENT_CLOSE);
        } else {
            while (true) {
                funnCall.addArgument(parseExpression());
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

    private Node parseExpression() throws Exception {
        System.out.println("Parsing expression...");
        final Expression expression = new Expression();
        expression.addOperand(parseMultiplicativeExpression());
        while (checkNextToken(TokenType.ADD, TokenType.SUB)) {
            final Token token = accept(TokenType.ADD, TokenType.SUB);
            expression.addOperator(token.getTokenType());
            expression.addOperand(parseMultiplicativeExpression());
        }
        return  expression;
    }

    private Node parseMultiplicativeExpression() throws Exception {
        System.out.println("Parsing multiplicative expression...");
        final Expression expression = new Expression();
        expression.addOperand(parseConvertExpression());
        while (checkNextToken(TokenType.MUL, TokenType.DIV)) {
            final Token token = accept(TokenType.MUL, TokenType.DIV);
            expression.addOperator(token.getTokenType());
            expression.addOperand(parseConvertExpression());
        }
        return expression;
    }

    private Node parseConvertExpression() throws Exception{
        System.out.println("Parsing converting expression...");
        final ConvertExpression convertExpression = new ConvertExpression();
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

    private Node parsePrimaryExpression() throws Exception {
        System.out.println("Parsing primary expression...");
        Node expression = null;
        if (checkNextToken(TokenType.PARENT_OPEN)) {
            accept(TokenType.PARENT_OPEN);
            expression = parseExpression();
            accept(TokenType.PARENT_CLOSE);
            return expression;
        } else if (checkNextToken(TokenType.ID)) {
            expression = parseVariableOrFunCall();
        } else {
            expression = parseLiteral();
        }
        return expression;
    }

    private Node parseVariableOrFunCall() throws Exception {
        System.out.println("Parsing variable or funn call...");
        final Token tempToken = accept(TokenType.ID);
        Node instruction = parseFunnCall(tempToken.getstringValue());
        if (instruction == null) {
            Variable variable = new Variable();
            variable.setName(tempToken.getstringValue());
            instruction = variable;
        }
        return instruction;
    }

    private Node parseReturnStatement() throws Exception {
        System.out.println("Parsing return...");
        final ReturnStatement returnStatement = new ReturnStatement();
        accept(TokenType.RETURN);
        returnStatement.setReturnValue(parseExpression());
        accept(TokenType.SEMICOLON);
        return returnStatement;
    }

    private Node parseWhileStatement() throws Exception {
        System.out.println("Parsing whileStatement...");
        final WhileStatement whileStatement = new WhileStatement();
        accept(TokenType.WHILE);
        accept(TokenType.PARENT_OPEN);
        whileStatement.setCondition(parseCondition());
        accept(TokenType.PARENT_CLOSE);
        whileStatement.setStatementBlock(parseStatementBlock());
        return whileStatement;
    }

    private Node parseIfStatement() throws Exception {
        System.out.println("Parsing if statement...");
        final IfStatement ifStatement = new IfStatement();
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
        System.out.println("Parsing condition...");
        final Condition condition = new Condition();
        condition.addOperand(parseAndCondition());
        while (checkNextToken(TokenType.OR)) {
            accept(TokenType.OR);
            condition.setOperator(TokenType.OR);
            condition.addOperand(parseAndCondition());
        }
        return condition;
    }

    private Node parseAndCondition() throws Exception {
        System.out.println("Parsing and condition...");
        final Condition andCondition = new Condition();
        andCondition.addOperand(parseRelationalCondition());
        while (checkNextToken(TokenType.AND)) {
            accept(TokenType.AND);
            andCondition.setOperator(TokenType.AND);
            andCondition.addOperand(parseRelationalCondition());
        }
        return andCondition;
    }

    private Node parseRelationalCondition() throws Exception {
        System.out.println("Parsing relational condition...");
        final Condition relationalCondition = new Condition();
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
        System.out.println("Parsing primary condition...");
        final Condition primaryCondition = new Condition();
        if (checkNextToken(TokenType.UNARY)) {
            accept(TokenType.UNARY);
            primaryCondition.setNegated(true);
        }
        if (checkNextToken(TokenType.PARENT_OPEN)) {
            accept(TokenType.PARENT_OPEN);
            primaryCondition.addOperand(parseCondition());
            accept(TokenType.PARENT_CLOSE);
        } else if (checkNextToken(TokenType.ID)) {
            primaryCondition.addOperand(parseVariable(null));
        } else {
            primaryCondition.addOperand(parseLiteral());
        }
        return primaryCondition;
    }

    private Literal parseLiteral() throws Exception {
        System.out.println("Parsing variable...");
        final Literal literal = new Literal();
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

    private Variable parseVariable(final Token firstToken) throws Exception {
        System.out.println("Parsing literal...");
        final Variable variable = new Variable();
        if (firstToken == null) {
            final Token token = accept(TokenType.ID);
            variable.setName(token.getstringValue());
        } else {
            variable.setName(firstToken.getstringValue());
        }
        return variable;
    }
}
