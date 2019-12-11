package com.lee.owner.io;

import java.io.*;
import java.net.Socket;

/**
 * @author joseph.li
 * @date 2019-11-27 12:05
 */
public class IOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 3333);
        PrintStream outputStream = new PrintStream(socket.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("输入信息：");
            String inputLine = input.readLine();
            outputStream.println(inputLine);
        }
    }


}