package semcheck;

import structures.ex.*;
import structures.*;
import java.util.*;
import static structures.Node.Type.*;


public class SemCheck {
    private Program program;
    private Map<String, FunctionEx> definedFunctions = new HashMap<>();

    public ProgramEx check(final Program program) throws Exception {
        definedFunctions.clear();
        this.program = program;

        scanDefinedFunctions();
        checkMain();

        List<FunctionEx> functions = new LinkedList<>();
        for (final Function function : program.getFunctions()) {
            functions.add(checkFunction(function));
        }

        ProgramEx programEx = new ProgramEx(functions, this.program.getConfigBlock());
        return programEx;
    }


    private void checkMain() throws Exception {
        if (!definedFunctions.containsKey("main")) {
            throw new Exception("No \"main\" function defined");
        }
        if (definedFunctions.get("main").getScope().getVariables().size() != 0) {
            throw new Exception("\"main\" function should not have parameters");
        }
    }

    private void scanDefinedFunctions() throws Exception {
        for (final Function function : program.getFunctions()) {
            scanDefinedFunction(function);
        }
    }

    private void scanDefinedFunction(final Function function) throws Exception {
        if (definedFunctions.containsKey(function.getName())) {
            throw new Exception("Redefined function: " + function.getName());
        }
        FunctionEx functionEx = new FunctionEx();
        functionEx.setName(function.name);
        for (final String parameter : function.getParameters()) {
            if (!functionEx.getScope().addVariable(parameter)) {
                throw new Exception(new StringBuilder()
                        .append("Dupicated parameter : ")
                        .append(parameter)
                        .append("in function")
                        .append(function.getName()).toString());
            }
        }
        definedFunctions.put(function.getName(), functionEx);
    }

    private FunctionEx checkFunction(final Function function) throws Exception {
        FunctionEx functionEx = definedFunctions.get(function.getName());
        functionEx.getInstructions().add(checkBlock(functionEx.getScope(), function.getStatementBlock()));
        return functionEx;

    }

    private Block checkBlock(final Scope scope, final StatementBlock statementBlock) throws Exception {
        Block block = new Block();
        block.getScope().setParentScope(scope);
        for (final Node instruction : statementBlock.getInstructions()) {
            switch (instruction.getType()) {
                case AssignStatement:
                    final AssingStatement assingStatement = (AssingStatement) instruction;
                    block.addInstruction(checkAssignment(block.getScope(), assingStatement));
                    break;
                case ReturnStatement:
                    final ReturnStatement returnStatement = (ReturnStatement) instruction;
                    block.addInstruction(checkReturnStatement(block.getScope(), returnStatement));
                    break;
                case FunCall:
                    final FunCall call = (FunCall) instruction;
                    block.addInstruction(checkFunCall(block.getScope(), call));
                    break;
                case IfStatement:
                    final IfStatement ifStatement = (IfStatement) instruction;
                    block.addInstruction(checkIfStatement(block.getScope(), ifStatement));
                    break;
                case WhileStatement:
                    final WhileStatement whileStatement = (WhileStatement) instruction;
                    block.addInstruction(checkWhileStatement(block.getScope(), whileStatement));
                    break;
            }
        }
        return block;
    }

    private Instruction checkWhileStatement(final Scope scope, final WhileStatement whileStatement) throws Exception {
        While whileEx = new While();
        whileEx.setCondition(checkCondition(scope, whileStatement.getCondition()));
        whileEx.setBlock(checkBlock(scope, whileStatement.getStatementBlock()));
        return whileEx;
    }

    private Instruction checkAssignment(final Scope scope, final AssingStatement assingStatement) throws Exception {
        Assignment assignment = new Assignment();
        String variableName = assingStatement.getVariable().getName();
        if (!scope.hasVariable(variableName)) {
            scope.addVariable(variableName);
        }
        assignment.setName(variableName);
        assignment.setValue(checkExpression(scope, (Expression) assingStatement.getValue()));
        return assignment;
    }

    private ExpressionEx checkExpression(final Scope scope, final Expression expr) throws Exception {
        ExpressionEx expression = new ExpressionEx();
        expression.setOperations(expr.getOperations());
        for (final Node operand : expr.getOperands()) {
            if (operand.getType() == Expression) {
                expression.addOperand(checkExpression(scope, (Expression) operand));
            } else if (operand.getType() == ConvertExpression) {
                expression.addOperand(checkConvertExpression(scope, (ConvertExpression) operand));
            }
        }
        return expression;
    }

    private ConvertExpressionEx checkConvertExpression(final Scope scope, final ConvertExpression expr) throws Exception {
        ConvertExpressionEx expression = new ConvertExpressionEx();
        for (String currency : expr.getCurrencies()) {
            if (!program.getConfigBlock().getExchangeRate().containsKey(currency))
                if (!program.getConfigBlock().getDefaultCurrency().equals(currency))
                    throw new Exception("Undefined currency \"" + currency + "\"");
        }
        Node operand = expr.getOperand();
        if (operand.getType() == Literal) {
            expression.setOperand(checkLiteral((Literal) operand));
        } else if (operand.getType() == Expression){
            expression.setOperand(checkExpression(scope, (Expression) operand));
        } else if (operand.getType() == Variable) {
            expression.setOperand(checkVariable(scope, (Variable) operand));
        } else if (operand.getType() == FunCall){
            expression.setOperand(checkFunCall(scope, (FunCall) operand));
        }
        return expression;
    }

    private LiteralEx checkLiteral(final Literal lit) {
        LiteralEx literal = new LiteralEx();
        literal.setValue(lit.getValue());
        return literal;
    }

    private VariableEx checkVariable(final Scope scope, final Variable var) throws Exception {
        VariableEx variable = new VariableEx();
        if (!scope.hasVariable(var.getName())) {
            throw new Exception("Usage of undefined variable: " + var.getName());
        }
        variable.setName(var.getName());
        return variable;
    }

    private Call checkFunCall(final Scope scope, final FunCall funCall) throws Exception {
        if (!definedFunctions.containsKey(funCall.getName())) {
            throw new Exception("Undefined function: " + funCall.getName());
        }
        FunctionEx function = definedFunctions.get(funCall.getName());
        if ((PrintStatement.equals(funCall.getType()) && funCall.getArguments().size() > 1)) {
            throw new Exception("Invalid arguments number for function print"
                    + ". Expected : 1" + " but found " + funCall.getArguments().size());
        } else if (!PrintStatement.equals(funCall.getType()) &&
                function.getScope().getVariables().size() != funCall.getArguments().size()) {
            throw new Exception("Invalid arguments number for function " + funCall.getName()
                    + ". Expected " + function.getScope().getVariables().size()
                    + " but found " + funCall.getArguments().size());
        }
        Call call = new Call();
        funCall.setName(funCall.getName());
        for (final Node argument : funCall.getArguments()) {
            call.addArgument(checkExpression(scope, (Expression) argument));
        }
        return call;
    }

    private Instruction checkReturnStatement(final Scope scope,
                                             final ReturnStatement returnStatement) throws Exception {
        final Return returnSt = new Return();
        returnSt.setValue(checkExpression(scope, (Expression) returnStatement.getReturnValue()));
        return returnSt;
    }

    private Instruction checkIfStatement(final Scope scope,
                                         final IfStatement ifStatement) throws Exception {
        If ifSt = new If();
        ifSt.setCondition(checkCondition(scope, ifStatement.getCondition()));
        ifSt.setTrueBlock(checkBlock(scope, ifStatement.getTrueBlock()));
        if (ifStatement.hasElseBlock()) {
            ifSt.setElseBlock(checkBlock(scope, ifStatement.getElseBlock()));
        }
        return ifSt;
    }

    private ConditionEx checkCondition(final Scope scope, final Condition condition) throws Exception {
        ConditionEx cond = new ConditionEx();
        cond.setIsNegated(condition.isNegated());
        cond.setOperation(condition.getOperation());
        for (final Node operand : condition.getOperands()) {
            if (Condition.equals(operand.getType())) {
                cond.addOperand(checkCondition(scope, (Condition) operand));
            } else if (Variable.equals(operand.getType())) {
                cond.addOperand(checkVariable(scope, (Variable) operand));
            } else if (Literal.equals(operand.getType())) {
                cond.addOperand(checkLiteral((Literal) operand));
            }
        }
        return cond;
    }

}
