package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.CommandManager;

public class AuthScreen extends JFrame {
    public AuthScreen(CommandManager commandManager) {
        // Настройка окна
        setTitle("Авторизация");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        
        // Элементы интерфейса
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Войти");
        JButton registerButton = new JButton("Зарегистрироваться");
        
        // Добавление элементов
        add(new JLabel("Логин:"));
        add(usernameField);
        add(new JLabel("Пароль:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);
        
        // Обработчики событий
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            commandManager.executeCommand("login " + username + " " + password);
            
            if (commandManager.isLogged()) {
                new MainScreen(commandManager).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка входа");
            }
        });
        
        registerButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            commandManager.executeCommand("register " + username + " " + password);
            
            if (commandManager.isLogged()) {
                new MainScreen(commandManager).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка регистрации");
            }
        });
    }
}