import java.io.*;
import java.net.*;

public class WebPageDownloader {
    public static void main (String [] args) {
        if (args.length == 0) {
            System.out.println("Please provide a website URL.");
            return;
        }

        String website = args[0];

        try {
            @SuppressWarnings("deprecation")
            URL url = new URL("http://" + website);
            String host = url.getHost();

            Socket socket = new Socket(host, 80);

            PrintWriter out = new PrintWriter (socket.getOutputStream(), true);

            out.println("GET / HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: Close");
            out.println();

            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            String line;
            BufferedWriter writer = new BufferedWriter(new FileWriter(host + "_homepage.html"));

            while ((line = in.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            in.close();
            socket.close();

            System.out.println("Downloaded");
        }
        catch (IOException e) {
            e.printStackTrace();;
        }
    }
}