package semcheck;

import structures.*;
import java.util.*;
import static structures.Node.Type.*;


public class SemCheck {
    private Program program;
    private Map<String, Function> definedFunctions = new HashMap<>();

    public void check(Program program) throws Exception {
        definedFunctions.clear();
        this.program = program;

        scanDefinedFunctions();
        checkMain();

        for (Map.Entry<String, Function> function : program.getFunctions().entrySet()) {
            checkFunction(function.getValue());
        }
    }


    private void checkMain() throws Exception {
        if (!definedFunctions.containsKey("main")) {
            throw new Exception("No \"main\" function defined");
        }
        if (definedFunctions.get("main").getStatementBlock().getScope().getVariables().size() != 0) {
            throw new Exception("\"main\" function should not have parameters");
        }
    }

    private void scanDefinedFunctions() throws Exception {
        for (Map.Entry<String, Function> function : program.getFunctions().entrySet()) {
            scanDefinedFunction(function.getValue());
        }
    }

    private void scanDefinedFunction(Function function) throws Exception {
        Function fun = new Function();
        fun.setName(function.getName());
        for (String parameter : function.getParameters()) {
            if (!fun.getStatementBlock().getScope().addVariable(parameter)) {
                throw new Exception(new StringBuilder()
                        .append("Dupicated parameter : ")
                        .append(parameter)
                        .append(" in function '")
                        .append(function.getName()).append("'").toString());
            }
        }
        definedFunctions.put(function.getName(), fun);
    }

    private void checkFunction(Function function) throws Exception {
        Function fun = definedFunctions.get(function.getName());
        checkBlock(fun.getStatementBlock().getScope(), function.getStatementBlock());
    }

    private void checkBlock(Scope scope, StatementBlock statementBlock) throws Exception {
        statementBlock.getScope().setParentScope(scope);
        for (Node instruction : statementBlock.getInstructions()) {
            switch (instruction.getType()) {
                case AssignStatement:
                    AssingStatement assingStatement = (AssingStatement) instruction;
                    checkAssignment(statementBlock.getScope(), assingStatement);
                    break;
                case ReturnStatement:
                    ReturnStatement returnStatement = (ReturnStatement) instruction;
                    checkReturnStatement(statementBlock.getScope(), returnStatement);
                    break;
                case FunCall:
                    FunCall call = (FunCall) instruction;
                    checkFunCall(statementBlock.getScope(), call);
                    break;
                case PrintStatement:
                    PrintStatement printStatement = (PrintStatement) instruction;
                    checkPrintStatemnt(statementBlock.getScope(), printStatement);
                    break;
                case IfStatement:
                    IfStatement ifStatement = (IfStatement) instruction;
                    checkIfStatement(statementBlock.getScope(), ifStatement);
                    break;
                case WhileStatement:
                    WhileStatement whileStatement = (WhileStatement) instruction;
                    checkWhileStatement(statementBlock.getScope(), whileStatement);
                    break;
            }
        }
    }

    private void checkPrintStatemnt(Scope scope, PrintStatement printStatement) throws Exception {
        checkVariable(scope, printStatement.getParameter());
    }

    private void checkWhileStatement(Scope scope, WhileStatement whileStatement) throws Exception {
        checkCondition(scope, whileStatement.getCondition());
        checkBlock(scope, whileStatement.getStatementBlock());
    }

    private void checkAssignment(Scope scope, AssingStatement assingStatement) throws Exception {
        String variableName = assingStatement.getVariable().getName();
        if (!scope.hasVariable(variableName)) {
            scope.addVariable(variableName);
        }
        checkExpression(scope, (AdditiveExpression) assingStatement.getValue());
    }

    private void checkExpression(Scope scope, AdditiveExpression expr) throws Exception {
        for (MuliplicativeExpression operand : expr.getOperands()) {
            checkMultiplicativeExpression(scope, operand);
        }
    }

    private void checkMultiplicativeExpression(Scope scope, MuliplicativeExpression expr) throws Exception {
        for (ConvertExpression operand : expr.getOperands()) {
            checkConvertExpression(scope, operand);
        }
    }

    private void checkConvertExpression(Scope scope, ConvertExpression expr) throws Exception {
        for (String currency : expr.getCurrencies()) {
            if (program.getConfigBlock() != null && !program.getConfigBlock().getExchangeRate().containsKey(currency)) {
                if (!program.getConfigBlock().getDefaultCurrency().equals(currency)) {
                    throw new Exception("Undefined currency \"" + currency + "\"");
                }
            }
            else if (program.getConfigBlock() == null) {
                throw new Exception("Undefined currency \"" + currency + "\"");
            }
        }
        PrimaryExpression operand = expr.getOperand();
        checkPrimaryExpression(scope, operand);

    }

    private void checkPrimaryExpression(Scope scope, PrimaryExpression expr) throws Exception {
        if (expr.getNodeType() == Literal) {
            checkLiteral((Literal) expr.getNode());
        } else if (expr.getNodeType() == AdditiveExpression){
            checkExpression(scope, (AdditiveExpression) expr.getNode());
        } else if (expr.getNodeType() == Variable) {
            checkVariable(scope, (Variable) expr.getNode());
        } else if (expr.getNodeType() == FunCall){
            checkFunCall(scope, (FunCall) expr.getNode());
        }
    }

    private void checkLiteral(Literal lit) {
        ;
    }

    private void checkVariable(Scope scope, Variable var) throws Exception {
        if (!scope.hasVariable(var.getName())) {
            throw new Exception("Usage of undefined variable: " + var.getName());
        }
    }

    private void checkFunCall(Scope scope, FunCall funCall) throws Exception {
        if (!definedFunctions.containsKey(funCall.getName())) {
            throw new Exception("Undefined function: " + funCall.getName());
        }
        Function function = definedFunctions.get(funCall.getName());
        if (function.getStatementBlock().getScope().getVariables().size() != funCall.getArguments().size()) {
            throw new Exception("Invalid arguments number for function " + funCall.getName()
                    + ". Expected " + function.getStatementBlock().getScope().getVariables().size()
                    + " but found " + funCall.getArguments().size());
        }

        for (Node argument : funCall.getArguments()) {
            checkExpression(scope, (AdditiveExpression) argument);
        }
    }

    private void checkReturnStatement(Scope scope, ReturnStatement returnStatement) throws Exception {
        checkExpression(scope, (AdditiveExpression) returnStatement.getReturnValue());
    }

    private void checkIfStatement(Scope scope, IfStatement ifStatement) throws Exception {
        checkCondition(scope, ifStatement.getCondition());
        checkBlock(scope, ifStatement.getTrueBlock());
        if (ifStatement.hasElseBlock()) {
            checkBlock(scope, ifStatement.getElseBlock());
        }
    }

    private void checkCondition(Scope scope, Condition condition) throws Exception {
        for (Node operand : condition.getOperands()) {
            if (Condition.equals(operand.getType())) {
                checkCondition(scope, (Condition) operand);
            } else if (Variable.equals(operand.getType())) {
                checkVariable(scope, (Variable) operand);
            } else if (Literal.equals(operand.getType())) {
                checkLiteral((Literal) operand);
            }
        }
    }

}
