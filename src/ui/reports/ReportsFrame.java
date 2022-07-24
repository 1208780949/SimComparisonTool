package ui.reports;

import main.SimComparisonTool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportsFrame extends JFrame {

    private JTable reportsTable;
    static final int NUM_ROWS = 3;
    static final int NUM_COLS = 13;
    static final int COL_WIDTH = 60;
    static final String[] COLS = {"Cl", "Cd", "L/D", "% Front", "mDot", "FW Cl", "FW Cd", "RW Cl", "RW Cd", "UT Cl", "UT Cd", "SW Cl", "SW Cd"};
    String[][] data = new String[NUM_ROWS][NUM_COLS];

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

        this.populateReportTable();

        // table model
        reportsTable.setModel(new DefaultTableModel(data, COLS));

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
     * Populates report table with numbers
     */
    private void populateReportTable() {
        for (int i = 0; i < NUM_COLS; i++) {
            // values
            Double sim1Val = Double.parseDouble(SimComparisonTool.sim1.getReportValueAt(i));
            Double sim2Val = Double.parseDouble(SimComparisonTool.sim2.getReportValueAt(i));
            Double diff = sim2Val - sim1Val;

            // set data
            data[0][i] = String.format("%.2f", sim1Val);
            data[1][i] = String.format("%.2f", sim2Val);
            data[2][i] = String.format("%.2f", diff);
        }
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
