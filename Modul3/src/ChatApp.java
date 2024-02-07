import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class ChatApp extends JFrame {
    private DefaultListModel<Contact> contactListModel;
    private String imagePathYou;

    public class DummyContactGenerator {
        public static List<Contact> generateDummyContacts() {
            List<Contact> dummyContacts = new ArrayList<>();

            String name1 = "Nabila";
            String number1 = "123-456-7890";
            String imagePath1 = "D:\\Kuliah GE (Semester 5)\\PBO\\Praktikum\\Modul3\\src\\FotoUser\\Nabila.jpg";
            Contact contact1 = new Contact(name1, number1, imagePath1);
            contact1.addChatMessage("You", "Bil");
            contact1.addChatMessage("Nabila", "Ya??");
            contact1.addChatMessage("You", "Mau cerita");
            contact1.addChatMessage("Nabila", "Cerita apa?");
            contact1.addChatMessage("You", "tapi.. gajadi deh");
            contact1.addChatMessage("Nabila", "ishhh sukanya gitu");
            contact1.addChatMessage("Nabila", "Kasi tau cepet");
            contact1.addChatMessage("You", "iyee hehe jadi gini.... ");
            contact1.addChatMessage("You", "gue lagi kesepian");
            contact1.addChatMessage("Nabila", "Kesepian knp?");
            contact1.addChatMessage("You", "dia ga bales chat sibuk kali ya... padahal mau caper");
            contact1.addChatMessage("Nabila", "heeeee yang sabar yaa haha");

            dummyContacts.add(contact1);

            String name2 = "Dara";
            String number2 = "987-654-3210";
            String imagePath2 = "D:\\Kuliah GE (Semester 5)\\PBO\\Praktikum\\Modul3\\src\\FotoUser\\Dara.jpg";
            Contact contact2 = new Contact(name2, number2, imagePath2);
            contact2.addChatMessage("Dara", "kak");
            contact2.addChatMessage("You", "ya kenapa?");
            contact2.addChatMessage("Dara", "enaknya makan apa ya?");
            contact2.addChatMessage("You", "eee... makan Nasi Goreng Jakarta enak");
            contact2.addChatMessage("You", "atoo lalapan ayam ehe");
            contact2.addChatMessage("Dara", "hhmmm bosen gamauu");
            contact2.addChatMessage("You", "makan mie enak juga");
            contact2.addChatMessage("Dara", "ishhh ga pekaaa");
            contact2.addChatMessage("You", "eh kenapaaaa?");

            dummyContacts.add(contact2);

            return dummyContacts;
        }
    }

    private Font loadFont(String path) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, 12);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    public ChatApp() {
        setTitle("WhatsDown");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        Font poppinsFont = loadFont("D:\\Kuliah GE (Semester 5)\\PBO\\Praktikum\\Modul3\\src\\Font\\Poppins-Bold.ttf");
        setUIFont(new FontUIResource(poppinsFont));

        contactListModel = new DefaultListModel<>();
        JList<Contact> contactList = new JList<>(contactListModel);
        contactList.setCellRenderer(new ContactListCellRenderer());
        contactList.setBackground(Color.decode("#67f9a8"));
        JScrollPane contactScrollPane = new JScrollPane(contactList);

        JButton newChatButton = new JButton("New Chat");
        newChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContactForm contactForm = new ContactForm(contactListModel);
                contactForm.setVisible(true);
            }
        });

        imagePathYou = "D:\\Kuliah GE (Semester 5)\\PBO\\Praktikum\\Modul3\\src\\FotoUser\\Ge.jpg";

        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.add(newChatButton, BorderLayout.NORTH);
        contactPanel.add(contactScrollPane, BorderLayout.CENTER);
        contactList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contactPanel.setPreferredSize(new Dimension(250, getHeight()));
        ChatPanel chatPanel = new ChatPanel();
        add(contactPanel, BorderLayout.WEST);
        add(chatPanel, BorderLayout.CENTER);

        contactPanel.setBackground(Color.decode("#67f9a8"));
        chatPanel.setBackground(Color.decode("#67f9a8"));

        List<Contact> dummyContacts = DummyContactGenerator.generateDummyContacts();
        for (Contact dummyContact : dummyContacts) {
            contactListModel.addElement(dummyContact);
        }

        contactList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Contact selectedContact = contactList.getSelectedValue();
                chatPanel.setActiveContact(selectedContact);
            }
        });

        setVisible(true);
    }

    class ChatPanel extends JPanel {
        private JLabel contactNameLabel;
        private JPanel chatHistoryPanel;
        private JScrollPane chatScrollPane;
        private JPanel messagePanel;
        private JTextField messageField;
        private JButton sendButton;
        private Contact activeContact;

        public ChatPanel() {
            setLayout(new BorderLayout());
            contactNameLabel = new JLabel("No Contact Selected");
            JPanel contactLabelPanel = new JPanel(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;

            contactLabelPanel.add(contactNameLabel, gbc);
            contactLabelPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            add(contactLabelPanel, BorderLayout.NORTH);

            chatHistoryPanel = new JPanel();
            chatHistoryPanel.setLayout(new BoxLayout(chatHistoryPanel, BoxLayout.Y_AXIS));
            chatScrollPane = new JScrollPane(chatHistoryPanel);
            chatScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            add(chatScrollPane, BorderLayout.CENTER);

            messagePanel = new JPanel(new BorderLayout());
            messageField = new JTextField();
            sendButton = new JButton("Send");

            messagePanel.add(messageField, BorderLayout.CENTER);
            messagePanel.add(sendButton, BorderLayout.EAST);

            add(messagePanel, BorderLayout.SOUTH);

            messageField.setFocusTraversalKeysEnabled(false);

            messageField.addActionListener(e -> sendMessage());
            sendButton.addActionListener(e -> sendMessage());
        }

        public void displayChatHistory() {
            if (activeContact != null) {
                chatHistoryPanel.removeAll();

                List<ChatMessage> chatHistory = activeContact.getChatHistory();
                for (ChatMessage chatMessage : chatHistory) {
                    addChatMessage(chatMessage.getSender(), chatMessage.getMessage(), chatMessage.getTimestamp());
                }

                revalidate();
                repaint();

                JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        }

        public void setActiveContact(Contact contact) {
            this.activeContact = contact;
            updateContactLabel();
            clearChat();
            displayChatHistory();
        }

        private void clearChat() {
            chatHistoryPanel.removeAll();
        }

        private void updateContactLabel() {
            if (activeContact != null) {
                contactNameLabel.setText(activeContact.getName());
            } else {
                contactNameLabel.setText("No Contact Selected");
            }
        }

        private void sendMessage() {
            String message = messageField.getText();
            if (!message.isEmpty() && activeContact != null) {
                activeContact.addChatMessage("You", message);
                displayChatHistory();
                messageField.setText("");
            }
        }

        private void addChatMessage(String sender, String message, Date timestamp) {
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel(new BorderLayout());
            JLabel senderLabel = new JLabel(sender);
            JLabel senderPhotoLabel = new JLabel();
            JLabel timestampLabel = new JLabel(new SimpleDateFormat("HH:mm").format(timestamp));
            senderLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            setSenderPhoto(sender, senderPhotoLabel);

            topPanel.add(senderPhotoLabel, BorderLayout.WEST);
            topPanel.add(senderLabel, BorderLayout.CENTER);
            topPanel.add(timestampLabel, BorderLayout.EAST);

            JTextArea messageArea = new JTextArea(message);
            messageArea.setEditable(false);
            messageArea.setLineWrap(true);
            messageArea.setWrapStyleWord(true);

            topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            messageArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
            messagePanel.add(topPanel, BorderLayout.NORTH);
            messagePanel.add(messageArea, BorderLayout.CENTER);

            chatHistoryPanel.add(messagePanel);

            revalidate();
            repaint();

            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }

        private void setSenderPhoto(String sender, JLabel senderPhotoLabel) {
            if (activeContact != null) {
                String imagePath;
                if (sender.equals("You")) {
                    imagePath = imagePathYou;
                } else {
                    imagePath = activeContact.getImagePath();
                }

                if (imagePath != null && !imagePath.isEmpty()) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    ImageIcon scaledIcon = getScaledRoundIcon(originalIcon, 20);

                    if (sender.equals("You")) {
                        senderPhotoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                    } else {
                        senderPhotoLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    }

                    senderPhotoLabel.setIcon(scaledIcon);
                }
            }
        }

        private ImageIcon getScaledRoundIcon(ImageIcon originalIcon, int size) {
            Image originalImage = originalIcon.getImage();
            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
            g2d.setClip(circle);
            g2d.drawImage(originalImage, 0, 0, size, size, null);

            g2d.dispose();

            return new ImageIcon(bufferedImage);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatApp());
    }
}

class ContactListCellRenderer extends JPanel implements ListCellRenderer<Contact> {
    private JLabel nameLabel;
    private JLabel imageLabel;
    private JLabel newMessageLabel;

    public ContactListCellRenderer() {
        setLayout(new BorderLayout());

        nameLabel = new JLabel();
        imageLabel = new JLabel();
        newMessageLabel = new JLabel("New Message");

        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        add(imageLabel, BorderLayout.WEST);
        add(nameLabel, BorderLayout.CENTER);
        add(newMessageLabel, BorderLayout.EAST);

        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Contact> list, Contact value, int index,
            boolean isSelected, boolean cellHasFocus) {

        setOpaque(true);
        nameLabel.setText(value.getName());

        if (value.getImagePath() != null && !value.getImagePath().isEmpty()) {
            ImageIcon originalIcon = new ImageIcon(value.getImagePath());
            ImageIcon scaledIcon = getScaledRoundIcon(originalIcon, 50);

            imageLabel.setIcon(scaledIcon);
        } else {
            imageLabel.setIcon(null);
        }

        if (isSelected) {
            newMessageLabel.setText("");
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            if (!value.getChatHistory().isEmpty()
                    && value.getChatHistory().get(value.getChatHistory().size() - 1).getSender().equals("You")) {
            } else {
                newMessageLabel.setText("");
            }

            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

    private ImageIcon getScaledRoundIcon(ImageIcon originalIcon, int size) {
        Image originalImage = originalIcon.getImage();
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
        g2d.setClip(circle);
        g2d.drawImage(originalImage, 0, 0, size, size, null);

        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }
}

class ContactForm extends JFrame {
    private JTextField nameField;
    private JTextField numberField;
    private JButton profileImageButton;
    private Contact currentContact;

    private JButton addContactButton;
    private DefaultListModel<Contact> contactListModel;

    public ContactForm(DefaultListModel<Contact> contactListModel) {
        setTitle("New Contact");
        setSize(400, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        currentContact = new Contact("", "", "");

        this.contactListModel = contactListModel;

        nameField = new JTextField();
        numberField = new JTextField();
        profileImageButton = new JButton("Profile Image");
        addContactButton = new JButton("Add Contact");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Number:"), gbc);
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(numberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(profileImageButton, gbc);
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addContactButton, gbc);
        gbc.insets = new Insets(5, 10, 5, 10);

        JList<Contact> addedContactList = new JList<>(contactListModel);
        addedContactList.setCellRenderer(new ContactListCellRenderer());
        JScrollPane contactListScrollPane = new JScrollPane(addedContactList);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        contactListScrollPane.setPreferredSize(new Dimension(200, 400));

        add(contactListScrollPane, gbc);

        addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        profileImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadProfileImage();
            }
        });
    }

    private void uploadProfileImage() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg")
                        || f.getName().toLowerCase().endsWith(".jpeg")
                        || f.getName().toLowerCase().endsWith(".png")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Selected Image Path: " + imagePath);

            profileImageButton.setText(imagePath);

            currentContact.setImagePath(imagePath);
        }
    }

    private void addContact() {
        String name = nameField.getText();
        String number = numberField.getText();

        currentContact.setName(name);
        currentContact.setNumber(number);

        contactListModel.addElement(currentContact);

        nameField.setText("");
        numberField.setText("");
        profileImageButton.setText("");

        currentContact = new Contact("", "", "");
    }
}

class ChatMessage {
    private String sender;
    private String message;
    private Date timestamp;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = new Date();
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

class Contact {
    private String name;
    private String number;
    private String imagePath;
    private List<ChatMessage> chatHistory;

    public Contact(String name, String number, String imagePath) {
        this.name = name;
        this.number = number;
        this.imagePath = imagePath;
        this.chatHistory = new ArrayList<>();
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void addChatMessage(String sender, String message) {
        chatHistory.add(new ChatMessage(sender, message));
    }

    @Override
    public String toString() {
        return name;
    }
}