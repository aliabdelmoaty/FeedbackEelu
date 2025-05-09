package constants;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class FeedbackListRenderer  extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof FeedbackEntry) {
                FeedbackEntry feedback = (FeedbackEntry) value;
                StringBuilder text = new StringBuilder();
                text.append("<html><div style='padding: 10px;'>"); // html is used to style the text

                // Only show updated indicator if feedback was actually updated
                if (feedback.getUpdatedAt() != null && !feedback.getUpdatedAt().equals(feedback.getCreatedAt())) {
                    text.append("<span style='color: #0066cc; font-weight: bold;'>(Updated)</span> ");
                }

                text.append("<span style='font-weight: bold;color: #0066cc; font-size: 14px;'>")
                        .append(feedback.getStudentName());

                text.append("<div style='margin: 5px 0; color: #333; font-size: 13px;'>")
                        .append(feedback.getText())
                        .append("</div>");

                text.append("<span style='color: #666; font-size: 12px;'>Posted on: ")
                        .append(feedback.getCreatedAt());

                // Only show update date if feedback was actually updated
                if (feedback.getUpdatedAt() != null && !feedback.getUpdatedAt().equals(feedback.getCreatedAt())) {
                    text.append(" | Updated on: ").append(feedback.getUpdatedAt());
                }

                text.append("</span></div></html>");
                setText(text.toString());

                // Add border and background
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

                if (isSelected) {
                    setBackground(new Color(0, 120, 212, 20));
                } else {
                    setBackground(Color.WHITE);
                }
            }
            return this;
        }
    }


