package Weekend2.Sockets.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 03.02.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class ChatClient extends JFrame{

    JTextField message;
    JTextArea messages;
    SocketClient socketClient;

    public ChatClient(String title) {

        setSize(600,400);
        setLocation(200,200);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUI();
        socketClient = new SocketClient("localhost",30000, messages);

    }
    private void setUI() {
        JPanel messagesPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.GRAY);
        message = new JTextField();
        messages = new JTextArea();
        JButton button = new JButton("SEND");
        add(messagesPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        messagesPanel.add(messages);
        inputPanel.add(message, BorderLayout.CENTER);
        inputPanel.add(button, BorderLayout.EAST);

        button.addActionListener(new SendButtonListener());
        message.addKeyListener(new EnterListener());

        JScrollPane scroll = new JScrollPane(messages);
        messagesPanel.add(scroll);

    }

    class EnterListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)  sendMessage();
        }
    }

    class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

                sendMessage();
        }
    }

    void sendMessage() {
        //должен отправлять на сервер и получать ответ с сервера



        if(null!=message && message.getText().length()>0) {
            System.out.println("send() exec");
            socketClient.send(message.getText()+"\n");
            //messages.append(confirmedMessage);
            message.setText(null);
            message.requestFocus();
            System.out.println("Exit sendMessage");
        }

        //socketClient.disconnect();


    }

    public static void main(String args[]) {

        ChatClient chat = new ChatClient("Chat v0.1");
        chat.setVisible(true);

    }

}
