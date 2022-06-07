package ui.subpanels.actionListeners;

import main.SimComparisonTool;
import ui.DispPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetAlphaListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        // frame
        JFrame alphaSetFrame = new JFrame("Set Transparency");
        alphaSetFrame.setResizable(false);
        alphaSetFrame.setContentPane(new JPanel(new GridLayout(3, 1)));
        alphaSetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // label
        alphaSetFrame.getContentPane().add(new JLabel("Set Transparency: "));

        // slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        alphaSetFrame.add(slider);

        // button
        JButton button = new JButton("Confirm");
        button.addActionListener(e1 -> confirm(slider, alphaSetFrame));
        alphaSetFrame.add(button);

        alphaSetFrame.pack();
        alphaSetFrame.setVisible(true);
    }

    private void confirm(JSlider slider, JFrame frame) {
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).getOverlayPanel().setAlpha((float) slider.getValue() / 100);
        frame.dispose();
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).getOverlayPanel().respond();
    }
}
