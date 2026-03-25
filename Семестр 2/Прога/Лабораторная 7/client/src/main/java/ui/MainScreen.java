package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import main.CommandManager;

public class MainScreen extends JFrame {
    public MainScreen(CommandManager commandManager) {
        // Настройка окна
        setTitle("Главное меню");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Панель с кнопками команд
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));
        
        // Создание кнопок для всех команд
        String[] commands = {
            "info", "show", "insert", "update", 
            "remove_key", "clear", "execute_script", "exit",
            "remove_greater", "replace_if_greater", "remove_lower_key",
            "filter_greater_than_timezone", "print_unique_governor",
            "print_field_descending_governor"
        };
        
        for (String cmd : commands) {
            JButton button = new JButton(cmd);
            button.addActionListener((ActionEvent e) -> {
                commandManager.executeCommand(cmd);
                // Здесь можно добавить обработку результатов
            });
            buttonPanel.add(button);
        }
        
        // Добавление панели с кнопками в окно
        add(new JScrollPane(buttonPanel), BorderLayout.CENTER);
        
        // Кнопка выхода
        JButton logoutButton = new JButton("Выйти");
        logoutButton.addActionListener(e -> {
            new AuthScreen(commandManager).setVisible(true);
            dispose();
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}