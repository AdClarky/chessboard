import ChessBoard.Board;
import ChessBoard.BoardListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements BoardListener {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final Board board;

    public Client(String hostName, int portNumber, Board board) {
        this.board = board;
        board.addBoardListener(this);
        try{
            this.socket = new Socket(hostName, portNumber);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void boardChanged(int oldX, int oldY, int newX, int newY) {
        try{
            if(socket.isConnected()){
                bufferedWriter.write(oldX + "," + oldY + "," + newX + "," + newY);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                char[] move;

                while(socket.isConnected()){
                    try {
                        move = bufferedReader.readLine().toCharArray();
                        board.movePiece(move[0]-'0', move[2]-'0', move[4]-'0', move[6]-'0');
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
