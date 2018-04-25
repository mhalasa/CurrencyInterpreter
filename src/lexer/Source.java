package lexer;

import java.io.*;


public class Source {

    private Position position;
    private FileReader inputStream;

    public Source(String fileName){
        position = new Position(1, 0);
        try {
            inputStream = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public char getNextChar() {

        try {
            int x = inputStream.read();
            if (x == -1)
                return 0;
            else{
                char c = (char) x;
                if (c == '\n'){
                    position.line++;
                    position.column = 0;
                }
                else
                    position.column ++;
                return c;
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Position getPosition() {
        return position;
    }
}
