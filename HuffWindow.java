import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;


public class HuffWindow {

	private JFrame frmCompressure;
	private final Action openFileAction = new SwingAction();
	private final Action huffmanAction = new SwingAction_3();

	/**
	 * Create the application.
	 */
	public HuffWindow() {
		initialize();
		frmCompressure.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCompressure = new JFrame();
		frmCompressure.setTitle("Compressure\n");
		frmCompressure.setBounds(100, 100, 450, 300);
		frmCompressure.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 99, 0, 31, 10, 2};
		gridBagLayout.rowHeights = new int[] {10, 0, 0, 0, 0, 10, 2};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmCompressure.getContentPane().setLayout(gridBagLayout);
		
		JButton btnChooseAFile = new JButton("Choose a file");
		btnChooseAFile.setAction(openFileAction);
		GridBagConstraints gbc_btnChooseAFile = new GridBagConstraints();
		gbc_btnChooseAFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChooseAFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseAFile.gridx = 1;
		gbc_btnChooseAFile.gridy = 1;
		frmCompressure.getContentPane().add(btnChooseAFile, gbc_btnChooseAFile);
		
		JLabel lblNoFileChosen = new JLabel("No File Chosen");
		GridBagConstraints gbc_lblNoFileChosen = new GridBagConstraints();
		gbc_lblNoFileChosen.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoFileChosen.gridx = 3;
		gbc_lblNoFileChosen.gridy = 1;
		frmCompressure.getContentPane().add(lblNoFileChosen, gbc_lblNoFileChosen);
		
		JButton btnCompress = new JButton("Compress");
		btnCompress.setAction(huffmanAction);
		btnCompress.setEnabled(false);
		GridBagConstraints gbc_btnCompress = new GridBagConstraints();
		gbc_btnCompress.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCompress.insets = new Insets(0, 0, 5, 5);
		gbc_btnCompress.gridx = 1;
		gbc_btnCompress.gridy = 2;
		frmCompressure.getContentPane().add(btnCompress, gbc_btnCompress);
		
		JTextPane txtpnWelcomeToCompressure = new JTextPane();
		txtpnWelcomeToCompressure.setText("Welcome to Compressure, a Huffman Encoding compression program.  "
				+ "To begin choose a .txt file using the 'Choose a file' button.  A .huff file will be produced in the same "
				+ "directory as the original file.  You can also use Compressure to decompress .huff files.  Simply choose a "
				+ ".huff file from the menu then hit decompress to write out a .txt file.");
		
		GridBagConstraints gbc_txtpnWelcomeToCompressure = new GridBagConstraints();
		gbc_txtpnWelcomeToCompressure.gridwidth = 3;
		gbc_txtpnWelcomeToCompressure.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnWelcomeToCompressure.fill = GridBagConstraints.BOTH;
		gbc_txtpnWelcomeToCompressure.gridx = 1;
		gbc_txtpnWelcomeToCompressure.gridy = 4;
		frmCompressure.getContentPane().add(txtpnWelcomeToCompressure, gbc_txtpnWelcomeToCompressure);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "SwingAction_2");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "SwingAction_3");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
