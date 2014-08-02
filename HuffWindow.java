import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.filechooser.FileNameExtensionFilter;


public class HuffWindow {

	private JFrame frmCompressure;
	private JButton btnCompress;
	private JLabel lblFileChosen;
	private File chosenFile;
	private JTextPane txtpnWelcomeToCompressure;
	private final Action openFileAction = new OpenAction();
	private final Action compressAction = new CompressAction();
	private final Action decompressAction = new DecompressAction();
	private Compressor huffCompress;
	private Decompressor huffDecompress;

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
		// Make the window & Start the layout
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
		
		// File chooser button
		JButton btnChooseAFile = new JButton("Choose a file");
		btnChooseAFile.setAction(openFileAction);
		GridBagConstraints gbc_btnChooseAFile = new GridBagConstraints();
		gbc_btnChooseAFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChooseAFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseAFile.gridx = 1;
		gbc_btnChooseAFile.gridy = 1;
		frmCompressure.getContentPane().add(btnChooseAFile, gbc_btnChooseAFile);
		
		// Label telling us what file we have chosen
		lblFileChosen = new JLabel("No File Chosen");
		GridBagConstraints gbc_lblFileChosen = new GridBagConstraints();
		gbc_lblFileChosen.insets = new Insets(0, 0, 5, 5);
		gbc_lblFileChosen.gridx = 3;
		gbc_lblFileChosen.gridy = 1;
		frmCompressure.getContentPane().add(lblFileChosen, gbc_lblFileChosen);
		
		// Button for compressing/decompressing based on file type chosen
		btnCompress = new JButton("Compress/Decompress");
		btnCompress.setEnabled(false);
		GridBagConstraints gbc_btnCompress = new GridBagConstraints();
		gbc_btnCompress.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCompress.insets = new Insets(0, 0, 5, 5);
		gbc_btnCompress.gridx = 1;
		gbc_btnCompress.gridy = 2;
		frmCompressure.getContentPane().add(btnCompress, gbc_btnCompress);
		
		// Add an area to display some information
		txtpnWelcomeToCompressure = new JTextPane();
		JScrollPane scrpnWelcomeToCompressure = new JScrollPane(txtpnWelcomeToCompressure);
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
		frmCompressure.getContentPane().add(scrpnWelcomeToCompressure, gbc_txtpnWelcomeToCompressure);
	}

	/**
	 * Opens a file browser dialog.  Upon choosing a file the 
	 * action will determine the action to assign to the 
	 * compress button and activate it.
	 */
	private class OpenAction extends AbstractAction {
		public OpenAction() {
			putValue(NAME, "Choose a file...");
			putValue(SHORT_DESCRIPTION, "Find a file that you would like to encode/decode");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text & Huffman Compressed Files", "txt", "huff", "comp");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(frmCompressure);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	chosenFile = chooser.getSelectedFile();
		    	String fileName = chosenFile.getName();
		    	lblFileChosen.setText(fileName);
		    	// Determine the type of file and take appropriate action
		    	if  (fileName.endsWith("txt") ){
		    		btnCompress.setText("Compress");
		    		btnCompress.setEnabled(true);
		    		btnCompress.setAction(compressAction);
		    		huffCompress = new Compressor();
		    	} else if ( fileName.endsWith("huff") || fileName.endsWith("comp") ){
		    		btnCompress.setText("Deompress");
		    		btnCompress.setEnabled(true);
		    		btnCompress.setAction(decompressAction);
		    		huffDecompress = new Decompressor();
		    	}
		    	
		    }
		}
	}

	/**
	 * 
	 *
	 */
	private class CompressAction extends AbstractAction {
		public CompressAction() {
			putValue(NAME, "Compress");
			putValue(SHORT_DESCRIPTION, "Write .huff and .comp files");
		}
		public void actionPerformed(ActionEvent e) {
			huffCompress.Compress(chosenFile);
			txtpnWelcomeToCompressure.setText(huffCompress.printInfo());
		}
	}
	
	/**
	 * 
	 *
	 */
	private class DecompressAction extends AbstractAction {
		public DecompressAction() {
			putValue(NAME, "Decompress");
			putValue(SHORT_DESCRIPTION, "Write a .txt file from .huff and .comp files.");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("Decompress a file.");
		}
	}	
}
