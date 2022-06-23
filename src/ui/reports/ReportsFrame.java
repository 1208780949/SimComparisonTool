package ui.reports;

import javax.swing.*;
import java.awt.*;

public class ReportsFrame extends JFrame {

    private JTable reportsTable;
    static final int NUM_ROWS = 3;
    static final int NUM_COLS = 13;
    static final int COL_WIDTH = 60;

    public ReportsFrame() {
        super("Reports");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initReportsTable();
        this.setContentPane(initContentPane());
        this.initReportsTable();
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initializes the reports table and fill in the header
     */
    private void initReportsTable() {
        // initialize
        this.reportsTable = new JTable(NUM_ROWS, NUM_COLS) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // set header
        reportsTable.getColumnModel().getColumn(0).setHeaderValue("Cl");
        reportsTable.getColumnModel().getColumn(1).setHeaderValue("Cd");
        reportsTable.getColumnModel().getColumn(2).setHeaderValue("L/D");
        reportsTable.getColumnModel().getColumn(3).setHeaderValue("% Front");
        reportsTable.getColumnModel().getColumn(4).setHeaderValue("mDot");
        reportsTable.getColumnModel().getColumn(5).setHeaderValue("FW Cl");
        reportsTable.getColumnModel().getColumn(6).setHeaderValue("FW Cd");
        reportsTable.getColumnModel().getColumn(7).setHeaderValue("RW Cl");
        reportsTable.getColumnModel().getColumn(8).setHeaderValue("RW Cd");
        reportsTable.getColumnModel().getColumn(9).setHeaderValue("UT Cl");
        reportsTable.getColumnModel().getColumn(10).setHeaderValue("UT Cd");
        reportsTable.getColumnModel().getColumn(11).setHeaderValue("SW Cl");
        reportsTable.getColumnModel().getColumn(12).setHeaderValue("SW Cd");

        // set column width
        reportsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < NUM_COLS; i++) {
            reportsTable.getColumnModel().getColumn(i).setPreferredWidth(COL_WIDTH);
        }

        // more settings
        reportsTable.getTableHeader().setReorderingAllowed(false); // no moving around
        reportsTable.getTableHeader().setResizingAllowed(false); // no resizing
    }

    /**
     * I want to display column headers, but I don't want a
     * scroll pane. So I have to do this to get around it
     * @return contentPane
     */
    private JPanel initContentPane() {

        // dealing with report table
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(reportsTable.getTableHeader(), BorderLayout.NORTH);
        contentPane.add(reportsTable, BorderLayout.CENTER);

        // set border
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return contentPane;
    }

}
