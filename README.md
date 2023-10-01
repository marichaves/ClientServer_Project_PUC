# File Sharing Application - Server and Client

This is an example Java application for file sharing between a server and multiple clients. The application allows clients to upload files to the server, browse the list of available files on the server, and download files from the server.

## Features

The application includes the following features:

1. **Upload Files to the Server**: Clients can upload files to the server, where they are stored for future access.

2. **List Files on the Server**: Clients can request a list of files available on the server. The server responds with a list of file names.

3. **Download Files from the Server**: Clients can request to download a specific file from the server. The server sends the requested file to the client.

4. **Connection Termination**: Clients can terminate their connection with the server at any time.

## Project Structure

The project is divided into two main parts: the server and the client.

### Server

The server component is responsible for:

- Accepting connections from clients.
- Managing files uploaded by clients.
- Responding to requests for file lists and downloads.

### Client

The client component allows users to:

- Upload files to the server.
- Request lists of files.
- Download files from the server.

## Configuration

Before running the application, ensure that you have the Java JDK installed on your system. Additionally, configure the folders where files will be stored on the server and clients.

### Server Configuration

1. Clone this repository on your server.
2. Ensure that port 12345 is available for use.
3. Configure the folder where files uploaded by clients will be stored. By default, it's set to "ServerFiles." You can change this setting in the source code if needed.

### Client Configuration

1. Clone this repository on your client machines.
2. Ensure that the server is running and accessible from the client machines.
3. Configure the folder where downloaded files from the server will be stored. By default, it's set to "Downloaded." You can change this setting in the source code if needed.

## Running the Application

### Server

1. On the server, navigate to the project's root folder.
2. Compile the `Server.java` file using the `javac Server.java` command.
3. Run the server with `java Server`.

### Client

1. On each client machine, navigate to the project's root folder.
2. Compile the `Client.java` file using the `javac Client.java` command.
3. Run the client with `java Client`.

## Usage

After starting the server and clients, you can use the following options:

- **Upload Files**: Run the client and select the file upload option. Choose a local file to send to the server.
- **List Files**: Run the client and select the list files option. The client will receive a list of files available on the server.
- **Download Files**: Run the client and select the download files option. Choose a file from the list to download from the server.
- **Connection Termination**: Run the client and select the connection termination option to disconnect from the server.

## Contribution

Feel free to contribute to this project by suggesting improvements, reporting bugs, or adding new features. You can create issues and submit pull requests.

Happy file sharing!
