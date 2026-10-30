import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValutaApp extends JFrame {
    private JTextField amountField;
    private JTextField resultField;
    private JComboBox<String> currencyBox;
    private JCheckBox reverseCheckBox; 
    private static final String FILE_NAME = "elozmenyek.txt";

    public ValutaApp() {
        setTitle("Valuta Árfolyam Lekérdező & Váltó");
        setSize(460, 310);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        

        
        JLabel lblAmount = new JLabel("Mennyiség:");
        lblAmount.setBounds(30, 20, 100, 25);
        add(lblAmount);

        amountField = new JTextField("1"); 
        amountField.setBounds(120, 20, 100, 25);
        add(amountField);

        
        JLabel lblCurr = new JLabel("Deviza:");
        lblCurr.setBounds(240, 20, 60, 25);
        add(lblCurr);

        String[] currencies = {"EUR", "USD", "GBP", "CHF"};
        currencyBox = new JComboBox<>(currencies);
        currencyBox.setBounds(300, 20, 100, 25);
        add(currencyBox);

        
        reverseCheckBox = new JCheckBox("Fordítva (HUF -> Deviza)");
        reverseCheckBox.setBounds(120, 55, 220, 25);
        add(reverseCheckBox);

        
        JButton btnQuery = new JButton("Átváltás & Lekérdezés");
        btnQuery.setBounds(30, 95, 180, 30);
        add(btnQuery);

        JButton btnHistory = new JButton("Előzmények");
        btnHistory.setBounds(220, 95, 180, 30);
        add(btnHistory);

        
        resultField = new JTextField();
        resultField.setBounds(30, 145, 370, 30);
        resultField.setEditable(false);
        add(resultField);

        
        JLabel infoLabel = new JLabel("<html><i>A lekérdezések automatikusan mentésre kerülnek<br>az elozmenyek.txt fájlba.</i></html>");
        infoLabel.setBounds(30, 190, 370, 40);
        add(infoLabel);

        

        btnQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String base = (String) currencyBox.getSelectedItem();
                boolean isReverse = reverseCheckBox.isSelected();

                try {
                   
                    double amount = Double.parseDouble(amountField.getText().trim());

                    String rateStr;
                    double rate;
                    String resultText;

                    if (!isReverse) {
                      
                        rateStr = getExchangeRate(base, "HUF");
                        rate = Double.parseDouble(rateStr);
                        double total = amount * rate;
                        resultText = amount + " " + base + " = " + String.format("%.2f", total) + " HUF";
                    } else {
                       
                        rateStr = getExchangeRate("HUF", base);
                        rate = Double.parseDouble(rateStr);
                        double total = amount * rate;
                        resultText = amount + " HUF = " + String.format("%.2f", total) + " " + base;
                    }

                    resultField.setText(resultText);

                    
                    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String record = "[" + timeStamp + "] " + resultText;
                    saveToFile(record);

                } catch (NumberFormatException ex) {
                    resultField.setText("Hiba: Kérlek érvényes számot adj meg a mennyiségnél!");
                } catch (Exception ex) {
                    resultField.setText("Hiba történt az adatok lekérésekor!");
                }
            }
        });

        btnHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistoryWindow();
            }
        });
    }

    
    private String getExchangeRate(String from, String to) throws Exception {
        String urlStr = "https://api.frankfurter.app/latest?from=" + from + "&to=" + to;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String json = response.toString();
        int index = json.indexOf("\"" + to + "\":");
        if (index != -1) {
            int start = index + to.length() + 3;
            int end = json.indexOf("}", start);
            if (end == -1) end = json.indexOf(",", start);
            return json.substring(start, end).trim();
        }
        return "1";
    }

   
    private void saveToFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showHistoryWindow() {
        JFrame historyFrame = new JFrame("Korábbi lekérdezések (Előzmények)");
        historyFrame.setSize(480, 320);
        historyFrame.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                textArea.setText("Hiba történt az előzmények betöltésekor.");
            }
        } else {
            textArea.setText("Még nincsenek elmentett lekérdezések.");
        }

        historyFrame.add(new JScrollPane(textArea));
        historyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ValutaApp().setVisible(true);
        });
    }
}