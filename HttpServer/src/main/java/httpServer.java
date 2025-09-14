import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.net.ServerSocket;
public class httpServer {
    public static void main(String[] args){
        final int port =8080;
        try(  ServerSocket serverSocket = new ServerSocket(port)){//enables server side listing over tcp protocol
            System.out.println("Serve is running  on "+port);
           while(true){ //to make server run infinitely , to handle multiple request
               Socket socket= serverSocket.accept(); //waits for the clients to connect then throws a socket id/connection
               HandleRequest(socket);
           }

        }catch(IOException e){
            System.out.println("cannot connect to server"+e.getMessage());


        }
    }

    private static void HandleRequest(Socket socket){
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream =socket.getOutputStream();
        ){
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));//bufferedReader  is used with inputStream or reading file
            String line =bufferedReader.readLine();
            String[] parts =line.split(" "); //it is breaking standard http request line GET/POST/etc /route HTTP/1.1 Host:localhost
            String method= parts[0]; //extracting GET/POST
            String route =parts[1]; // route example /messages
            int contentLength = 0;
            while (!(line=bufferedReader.readLine()).isEmpty()){
                if(line.startsWith("Content-Length:")){
                    contentLength =Integer.parseInt(line.split(":")[1].trim());
                }
            }
            if("GET".equalsIgnoreCase(method) && "/messages".equalsIgnoreCase(route)){
                writeResponse(outputStream);
            }
            else if("POST".equalsIgnoreCase(method)&& "/login".equalsIgnoreCase(route)){
                char[] Bodychars = new char[contentLength];
                bufferedReader.read(Bodychars);
                String body = new String(Bodychars);
                writeResponsePost(outputStream,body);

            }

        } catch (IOException e){
            System.out.println("failed to handle request"+e.getMessage());

        }finally {
            try{
                socket.close();
            }catch (IOException e){
                System.out.println("failed to close socket"+e.getMessage());
            }

        }


    }

    private static void writeResponse(OutputStream outputStream){
        String message= "hello from httpserver";
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "\r\n" +
                message;


        try {
            outputStream.write(httpResponse.getBytes()); //getBytes converts string in byte array ,byte array is converting each character into a ASCII value
            outputStream.flush(); //ensures all pending bytes are send before closing
            outputStream.close();//closes outputStream

        }catch(IOException e){
            System.out.println("failed to write Response"+e.getMessage());
        }


    }
    private static void writeResponsePost(OutputStream outputStream,String body){
        String username =null;
        String password =null;
        String[] pairs =body.split("&");
        for(String pair:pairs){
            String[] keyValue =pair.split("=");
            if(keyValue.length==2){
                if(keyValue[0].equals("username")){
                    username=keyValue[1];
                }else if(keyValue[0].equals("password")){
                    password=keyValue[1];
                }

            }
        }
        String message =null;
        if("Ayush".equals(username)&& "password@123".equals(password)){
            message="login successful";
        }
        else {
            message ="login failed";
        }
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "\r\n" +
                message;


        try{
            outputStream.write(httpResponse.getBytes());
            outputStream.flush();
            outputStream.close();

        }catch(IOException e){
            System.out.println("failed to write Response"+e.getMessage());
        }


    }
}
