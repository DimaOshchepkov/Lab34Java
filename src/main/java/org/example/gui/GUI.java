package org.example.gui;

import org.example.ImageLoader;
import org.example.ParserWorker;
import org.example.parsers.Completed;
import org.example.parsers.NewData;
import org.example.parsers.habr.HabrParser;
import org.example.parsers.habr.HabrSettings;
import org.example.parsers.habr.model.Article;
import org.jetbrains.annotations.NotNull;

import lombok.val;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements ParserWorker.OnNewDataHandler<ArrayList<Article>>, ParserWorker.OnCompleted {
    private ParserWorker<ArrayList<Article>> parser;
    private Thread downloadThread;
    private final JPanel panel = new JPanel();
    private int count = 0;
    private ImageLoader imageLoader = new ImageLoader();

    public GUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("АААОООААА");
        setSize(1200, 800);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel rightPanel = new JPanel();
        GridLayout layout = new GridLayout(10, 1, 5, 12);
        rightPanel.setLayout(layout);
        add(rightPanel, BorderLayout.EAST);

        JLabel jlStart = new JLabel("Первая страница");
        jlStart.setHorizontalAlignment(SwingConstants.CENTER);
        jlStart.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlStart);

        JTextField jtfStart = new JTextField();
        jtfStart.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfStart);

        JLabel jlEnd = new JLabel("Последняя страница");
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlEnd);

        JTextField jtfEnd = new JTextField();
        jtfEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfEnd);

        JButton startButton = new JButton("Старт");
        JButton abortButton = new JButton("Стоп");
        startButton.setSize(50, 20);
        rightPanel.add(startButton);
        rightPanel.add(abortButton);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane jsp = new JScrollPane(panel);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(jsp);
        startButton.addActionListener(e -> {
            if (!isCorrectInputText(jtfStart.getText(), jtfEnd.getText())) {
                return;
            }

            panel.removeAll();

            int start = Integer.parseInt(jtfStart.getText());
            int end = Integer.parseInt(jtfEnd.getText());

            parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

            parser.getOnCompletedList().add(GUI.this);
            parser.getOnNewDataList().add(GUI.this);

            parser.getOnCompletedList().add(new Completed());
            parser.getOnNewDataList().add(new NewData());

            parser.execute();

        });

        abortButton.addActionListener(e -> {
            parser.cancel(true);
        });
    }

    private boolean isCorrectInputText(@NotNull String startText, @NotNull String endText) {
        if (startText.isEmpty()) {
            showError("Введите начало пагинации");
            return false;
        }

        if (endText.isEmpty()) {
            showError("Введите конец пагинации");
            return false;
        }

        int start, end;
        try {
            start = Integer.parseInt(startText);
            end = Integer.parseInt(endText);
        } catch (NumberFormatException e) {
            showError("Введите целочисленное значение");
            return false;
        }

        if (start < 1) {
            showError("Введено неккоректное значение (start < 1)");
            return false;
        }

        if (end < 1) {
            showError("Введено неккоректное значение (end < 1)");
            return false;
        }

        if (end < start) {
            showError("Введено неккоректное значение (start > end)");
            return false;
        }

        return true;
    }

    private void showError(@NotNull String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void onNewData(Object sender, ArrayList<Article> articles) {

        for (val article : articles) {
            JTextArea textArea = getInitTextArea();
            textArea.append("Статья " + ++count + "\n" + article.getTitle() + "\n" + article.getText() + "\n");

            JLabel label = new JLabel();
            if (article.getImageUrl() != null) {
                String imageName = article.getImageUrl().substring(article.getImageUrl().lastIndexOf("/") + 1);
                BufferedImage bufImage;
                try {
                    bufImage = ImageIO.read(new File("src/images/" + imageName));
                    Image image = bufImage.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
                    label.setIcon(new ImageIcon(image));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                } catch (IOException e) {
                    textArea.append("Ошибка загрузки изображения\n");
                }

            } else {
                textArea.append("Изображение отсутствует\n");
            }

            panel.add(textArea);
            panel.add(label);
            panel.updateUI();

        }
    }

    private @NotNull JTextArea getInitTextArea() {
        JTextArea textArea = new JTextArea();

        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setSize(panel.getWidth(), panel.getHeight());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        return textArea;
    }

    @Override
    public void onCompleted(Object sender) {
        JOptionPane.showMessageDialog(this, "Загрузка закончена");
    }

}
