package com.map.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class JButtonTable  {
	private static final long serialVersionUID = 1L;
  //  private  JTable table;
	public JScrollPane test2() {
		//super(new BorderLayout());
		JTable table = new JTable(new Model());
		table.setDefaultRenderer(JButton.class, new JButtonRender());
		table.setPreferredScrollableViewportSize(new Dimension(70, 500));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		//add(scrollPane, BorderLayout.CENTER);
		return scrollPane;
	}

	class Model extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = { "编号", "HEIGHT", "WIDTH", "分辨率" };
		private Object[][] data = { { 1, "0", "0", "0" }, { 2, "0","0", "0" }, { 3, "0", "0", "0" }, { 4, "0", "0", "0" }, { 5, "0", "0", "0" }, { 6, "0", "0", "0" } };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}


		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}
}

class JButtonRender extends JButton implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public JButtonRender() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object jButton, boolean isSelected, boolean hasFocus,
			int row, int column) {

		setBorder(BorderFactory.createEmptyBorder());
		return this;
	}

}