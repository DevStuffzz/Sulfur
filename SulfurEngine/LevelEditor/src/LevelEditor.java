import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LevelEditor extends JFrame {
    private int[][] map;
    private JPanel[][] panels;
    private JPanel imageSelectorPanel;
    private JFileChooser fileChooser;
    private Map<Integer, Image> imageMap;
    private int selectedImageIndex = -1;

    public LevelEditor() {
        setTitle("Level Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        map = new int[20][20]; // Initial map size

        // Create panels
        panels = new JPanel[20][20];
        JPanel gridPanel = new JPanel(new GridLayout(20, 20));
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                panels[y][x] = new JPanel();
                panels[y][x].setPreferredSize(new Dimension(40, 40));
                panels[y][x].setBackground(map[y][x] == 0 ? Color.WHITE : Color.GREEN); // Default color
                final int finalX = x;
                final int finalY = y;
                panels[y][x].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Set the selected image to the clicked position
                        if (selectedImageIndex != -1) {
                            map[finalY][finalX] = selectedImageIndex;
                            ImageIcon icon = new ImageIcon(imageMap.get(selectedImageIndex));
                            JLabel label = new JLabel(icon);
                            panels[finalY][finalX].removeAll();
                            panels[finalY][finalX].add(label);
                            panels[finalY][finalX].revalidate();
                            panels[finalY][finalX].repaint();
                        } else {
                            JOptionPane.showMessageDialog(LevelEditor.this, "Please select an image first.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                gridPanel.add(panels[y][x]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Image selector panel
        imageSelectorPanel = new JPanel();
        imageSelectorPanel.setLayout(new FlowLayout());
        add(imageSelectorPanel, BorderLayout.NORTH);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportMapToFile();
            }
        });
        fileMenu.add(exportMenuItem);

        JMenu imageMenu = new JMenu("Images");
        JMenuItem selectImagesMenuItem = new JMenuItem("Select Images");
        selectImagesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImages();
            }
        });
        imageMenu.add(selectImagesMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(imageMenu);
        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selectImages() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            imageMap = new HashMap<>();
            selectedImageIndex = -1;
            for (int i = 0; i < selectedFiles.length; i++) {
                ImageIcon icon = new ImageIcon(selectedFiles[i].getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                imageMap.put(i, scaledImage);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                final int index = i;
                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        selectedImageIndex = index;
                    }
                });
                imageSelectorPanel.add(imageLabel);
            }
            imageSelectorPanel.revalidate();
            imageSelectorPanel.repaint();
        }
    }

    private void exportMapToFile() {
        try {
            if (imageMap == null || imageMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select images first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("level.txt"));
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    writer.write(map[y][x] + " ");
                }
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(this, "Map exported to level.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting map: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LevelEditor();
            }
        });
    }
}
