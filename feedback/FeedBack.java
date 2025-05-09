package feedback;

import constants.Actions;
import showAll.ShowAllFeedback;
import constants.ActionButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FeedBack extends JFrame {
    private final String studentId;
    // this field is used to store the student id
    private JTextArea feedbackArea;
    // this field is used to store the feedback area
    private JPanel feedbackListPanel;
    // this field is used to store the feedback list panel
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    // this field is used to store the primary color
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    // this field is used to store the background color

    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private ActionButton addButton, showAllButton;

    public FeedBack(String studentId) {
        this.studentId = studentId;
        // this field is used to store the student id
        setTitle("Student Feedback - " + studentId);
        // this field is used to set the title of the frame
        setSize(800, 600);
        // this field is used to set the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this field is used to set the default close operation of the frame
        setLocationRelativeTo(null);
        // this field is used to set the location of the frame
        getContentPane().setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the frame
        initializeComponents();
        // this field is used to initialize the components of the frame
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        // this field is used to create a new panel with a border layout
        mainPanel.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the panel
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        // this field is used to set the border of the panel

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        // this field is used to create a new panel with a border layout
        titlePanel.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the panel
        JLabel titleLabel = new JLabel("Add Your Feedback", SwingConstants.CENTER);
        // this field is used to create a new label with the text "Add Your Feedback"
        // and the text is centered
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        // this field is used to set the font of the label
        titleLabel.setForeground(PRIMARY_COLOR);
        // this field is used to set the foreground color of the label
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        // this field is used to add the label to the panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        // this field is used to add the panel to the main panel

        // Feedback input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        // this field is used to create a new panel with a border layout
        inputPanel.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the panel

        feedbackArea = new JTextArea(5, 40);
        // this field is used to create a new text area with 5 rows and 40 columns
        feedbackArea.setLineWrap(true);
        // this field is used to set the line wrap of the text area
        feedbackArea.setWrapStyleWord(true);
        // this field is used to set the wrap style word of the text area
        feedbackArea.setFont(LABEL_FONT);
        // this field is used to set the font of the text area
        feedbackArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        // this field is used to set the border of the text area

        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        // this field is used to create a new scroll pane with the text area
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // this field is used to set the border of the scroll pane
        inputPanel.add(scrollPane, BorderLayout.CENTER);
        // this field is used to add the scroll pane to the panel

        // Feedback list panel
        feedbackListPanel = new JPanel();
        // this field is used to create a new panel
        feedbackListPanel.setLayout(new BoxLayout(feedbackListPanel, BoxLayout.Y_AXIS));
        // this field is used to set the layout of the panel
        feedbackListPanel.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the panel

        JScrollPane listScrollPane = new JScrollPane(feedbackListPanel);
        // this field is used to create a new scroll pane with the panel
        listScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // this field is used to set the border of the scroll pane
        listScrollPane.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the scroll pane

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        // this field is used to create a new panel with a flow layout
        buttonPanel.setBackground(BACKGROUND_COLOR);
        // this field is used to set the background color of the panel

        addButton = new ActionButton("Add Feedback", e -> Actions.addFeedback(studentId, feedbackArea.getText(), this));
        // this field is used to create a new button with the text "Add Feedback" and
        // the action is the addFeedback method
        showAllButton = new ActionButton("Show Feedbacks", e -> {
            ShowAllFeedback showAllFeedback = new ShowAllFeedback(studentId);
            showAllFeedback.setVisible(true);
        });
        // this field is used to create a new button with the text "Show All Feedback"
        // and the action is the showAllFeedback method

        buttonPanel.add(addButton); // this field is used to add the button to the panel
        buttonPanel.add(showAllButton); // this field is used to add the button to the panel
        inputPanel.add(buttonPanel, BorderLayout.SOUTH); // this field is used to add the panel to the main panel

        // Add panels to main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, listScrollPane);
        // this field is used to create a new split pane with the input panel and the
        // list scroll pane
        splitPane.setDividerLocation(200);
        // this field is used to set the divider location of the split pane
        splitPane.setDividerSize(5);
        // this field is used to set the divider size of the split pane
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        // this field is used to set the border of the split pane
        mainPanel.add(splitPane, BorderLayout.CENTER);
        // this field is used to add the split pane to the main panel

        add(mainPanel); // this field is used to add the main panel to the frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FeedBack("test").setVisible(true);
        });
    }
}
