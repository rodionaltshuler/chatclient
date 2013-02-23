package Weekend2.Sockets.Client;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 03.02.13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class SocketClient {

    Socket socket=null;
    OutputStream outputStream=null;
    InputStream inputStream=null;
    JTextArea textArea;

    String host = "192.168.1.116"; //Alexander
    int port = 30000;

    public SocketClient() {
        connect();
    }

    public SocketClient(String host, int port, JTextArea textArea) {
        this.host=host;
        this.port=port;
        this.textArea=textArea;
        connect();

        //start listener
        ListenerThread listener = new ListenerThread();
        listener.start();
    }

    private void connect() {

        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream= socket.getInputStream();
        } catch (Exception e ) {
            System.out.println(e.toString());
        }

    }


    public void disconnect() {
        try {
              outputStream.close();
              inputStream.close();
              socket.close();
        } catch (Exception e ) {
              System.out.println(e.toString());
        }
    }


    public void send(String message) {

        //SEND
        try {
            char chars[] = message.toCharArray();
            for (int i=0; i<chars.length;i++) {
                outputStream.write((int)chars[i]);
            }
            outputStream.flush();


         //READ SERVER RESPONSE
          /*
            System.out.println("Reading: ");
            StringBuffer buffer = new StringBuffer();
            int i=0;
            while ((char)i!='\n') {
                i = inputStream.read();
                buffer.append((char)i);
            }

            return buffer.toString();
         */

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //return null;

    }

    class ListenerThread extends Thread {

        @Override
        public void run() {

          System.out.println("RUNNED!");
          try {
            do {
                StringBuffer buffer = new StringBuffer();
                System.out.println("Buffer is new");
                int i=0;
                while (i!='\n') {
                    i = inputStream.read();
                    buffer.append((char)i);
                    //System.out.println("inside run Read char:" + (char)i);
                }
                //send message
                textArea.append(buffer.toString());

            } while(true);
          }catch (Exception e) {
              System.out.println(e.toString());
          }

        }
    }
}
