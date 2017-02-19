package com.peafowltrading.logviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Garrison Peacock
 */
public class Window extends JFrame implements ActionListener
{
    private JLabel  _logs;
    private JButton _loadButton;
    private JLabel  _currentFile;
    private JScrollPane _logsPane;

    public Window()
    {
        super("Log Viewer");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        _currentFile = new JLabel("");

        _logs = new JLabel();
        _logs.setPreferredSize(new Dimension(800, 600));
        _logs.setBackground(Color.white);

        _loadButton = new JButton("Load File");
        _loadButton.addActionListener(this);
        _loadButton.setActionCommand("loadFile");

        _logsPane = new JScrollPane(_logs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        _logsPane.setPreferredSize(new Dimension(800, 600));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(_currentFile, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(_loadButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(_logsPane,constraints);

        _currentFile.setVisible(true);
        _loadButton.setVisible(true);
        _logs.setVisible(true);

        this.pack();
    }

    public void SetCurrentFileString(String string)
    {
        _currentFile.setText("Current File: " + string);
    }

    public void SetLogsString(String string)
    {
        _logs.setText(string);
    }

    public String LoadFile()
    {
        FileDialog fileDialog = new FileDialog(this, "Choose a log", FileDialog.LOAD);
        fileDialog.setVisible(true);

        return fileDialog.getDirectory() + fileDialog.getFile();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        switch (event.getActionCommand())
        {
            case "loadFile":

                String filePath = LoadFile();
                String fileContents = "";

                try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = reader.readLine();

                    while (line != null)
                    {
                        stringBuilder.append(line);
                        stringBuilder.append("<br>");
                        line = reader.readLine();
                    }

                    fileContents = stringBuilder.toString();

                    reader.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                String result = "<html><body>" + fileContents + "</body></html>";

                SetCurrentFileString(filePath);
                SetLogsString(result);

                break;
        }
    }
}
