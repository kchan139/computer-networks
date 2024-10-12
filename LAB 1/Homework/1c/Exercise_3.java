import java.io.*;
import java.net.*;

class MultiThreadedChatServer {
    public static void main(String[] args) {
        try (ServerSocket svSocket = new ServerSocket(12345)) {
            System.out.println("Multithreaded Server is listening on port 12345...");
            while (true) {
                Socket socket = svSocket.accept();
                System.out.println("New client connected");
                new ClientHandler(socket).start();
            }
        } 
        catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Client: " + msg);
                out.println("Server: " + msg);
            }
            socket.close();
        } 
        catch (IOException e) {
            System.out.println("Handler error: " + e.getMessage());
        }
    }
}