package com.lee.owner.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author joseph.li
 * @date 2019-11-27 12:06
 */
public class IOServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(3333);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ServerThread(socket)).start();
        }
    }

    public static class ServerThread implements Runnable {

        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println(inputReader.readLine());
                } catch (Exception e) {

                }

            }
        }
    }

}
