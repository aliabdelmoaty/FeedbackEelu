package constants;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class ActionButton extends JButton {
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Color PRIMARY_BUTTON_COLOR = new Color(41, 128, 185);
    private static final Color HOVER_BUTTON_COLOR = new Color(52, 152, 219);

    public ActionButton(String text, ActionListener actionListener) {
        super(text);
        setFont(BUTTON_FONT);// font of the button
        setForeground(Color.WHITE); // color of the text
        setBackground(PRIMARY_BUTTON_COLOR); // color of the button
        setFocusPainted(false); // remove the focus border
        setBorderPainted(false);// remove the border
        setPreferredSize(new Dimension(150, 40)); // size of the button

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_BUTTON_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(PRIMARY_BUTTON_COLOR);
            }
        });
        addActionListener(actionListener);

    }

}
