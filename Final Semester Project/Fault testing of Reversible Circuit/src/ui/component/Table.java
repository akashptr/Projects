package ui.component;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Table extends JTable {

	private static final long serialVersionUID = 1L;

	public Table(int row, int col) {
		super(row, col);
		SetTable(col);
	}

	public void SetTable(int col) {
		setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, 16));
		setRowHeight(25);
		setCellSelectionEnabled(true);

		JTableHeader tableHeader = getTableHeader();
		tableHeader.setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, 20));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < col; i++)
			getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}
	
	public void loadTable(int[][] data) {
		int row = getRowCount();
		int col = getColumnCount();
		for(int r = 0; r < row; r++)
			for(int c = 0; c < col; c++) {
				setValueAt(Integer.valueOf(data[r][c]), r, c);
			}
	}
}
