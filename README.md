# Java HTTP Server

This document provides an overview and instructions for a basic, single-threaded HTTP server implemented in Java. The server listens for incoming connections on port 8080 and handles GET and POST requests for specific routes.

## Key Features

- **HTTP Request Handling**: The server can parse a basic HTTP request line to extract the method (e.g., GET, POST) and the requested route.
  
- **Endpoint Routing**: It supports two distinct routes:
  - `GET /messages`: Responds with a simple "hello from httpserver" message.
  - `POST /login`: Processes a login attempt with a hardcoded username and password (Ayush and password@123).
  
- **Content-Length Parsing**: The server correctly reads the Content-Length header for POST requests to determine the size of the request body.
  
- **Error Handling**: Basic try-catch blocks are in place to handle IOExceptions during server startup and request processing.
  
- **Infinite Loop**: The main server loop runs indefinitely to handle multiple client connections.

## How to Run the Server

To run this server, you'll need a Java Development Kit (JDK) installed on your system.

1. **Save the Code**: Save the provided Java code in a file named `httpServer.java`.

2. **Compile the Code**: Open a terminal or command prompt and navigate to the directory where you saved the file. Compile the code using the Java compiler:
   ```
   javac httpServer.java
   ```

3. **Run the Server**: After successful compilation, run the server with the following command:
   ```
   java httpServer
   ```
   The server will start and print the message "Serve is running on 8080". It will now be ready to accept incoming requests.

## API Endpoints

You can test the server's functionality using a tool like curl or by writing a simple client.

1. **GET Request**  
   **Endpoint**: `/messages`  
   **Method**: GET  
   **Description**: This endpoint returns a simple text message.  
   **Example curl command**:
   ```
   curl -X GET http://localhost:8080/messages
   ```
   **Expected Response**:
   ```
   hello from httpserver
   ```

2. **POST Request**  
   **Endpoint**: `/login`  
   **Method**: POST  
   **Description**: This endpoint processes a login with form-encoded data. It checks for a specific username and password.  
   **Example curl command for a successful login**:
   ```
   curl -X POST -d "username=Ayush&password=password@123" http://localhost:8080/login
   ```
   **Expected Response (Successful)**:
   ```
   login successful
   ```
   **Example curl command for a failed login**:
   ```
   curl -X POST -d "username=invalid&password=wrongpassword" http://localhost:8080/login
   ```
   **Expected Response (Failed)**:
   ```
   login failed
   ```

## Code Structure

The server's logic is divided into three main methods:

- `main(String[] args)`: The entry point of the application. It creates a ServerSocket and enters an infinite loop to listen for new connections.

- `HandleRequest(Socket socket)`: Manages the request/response lifecycle for a single client connection. It reads the incoming request, parses the headers, and delegates to the appropriate response method based on the request method and route.

- `writeResponse(OutputStream outputStream)`: Composes and sends an HTTP response for GET requests.

- `writeResponsePost(OutputStream outputStream, String body)`: Parses the body of a POST request, handles the login logic, and sends back the corresponding HTTP response.
