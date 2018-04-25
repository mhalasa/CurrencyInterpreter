package lexer;

public class Position {
    public int line;
    public int column;

    public Position() {
        this.line = 0;
        this.column = 0;
    }

    public Position(Position position){
        this.line = position.line;
        this.column = position.column;
    }


    public Position(int line, int column){
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "(" + line +"," + column + ")";
    }
}
