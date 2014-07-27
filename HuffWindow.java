import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.io.File;

public class HuffWindow {

	JLabel chosenFileLabel;
	JButton btnCompress, btnChooseAFile, btnDecompress;
	JTextPane txtpnWelcomeToCompressure;
	EncodeAction compListener;
	DecodeAction decompListener;
	boolean  hasCompListener, hasDecompListener;
	
	private JFrame frmCompressure;
	private File file;
	private Compressor compress;
	private Decompressor decompress;
	
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
		// Create a jframe and set some basics
		frmCompressure = new JFrame();
		frmCompressure.setTitle("Compressure");
		frmCompressure.setBounds(100, 100, 450, 300);
		frmCompressure.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		compListener = new EncodeAction();
		decompListener = new DecodeAction();
		hasCompListener = false;
		hasDecompListener = false;
		
		// Work out the layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 99, 0, 0, 31, 10, 2};
		gridBagLayout.rowHeights = new int[] {10, 0, 0, 0, 0, 10, 2};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmCompressure.getContentPane().setLayout(gridBagLayout);

		// Chosen file information.  Defaults to "No File Chosen" until a file is chosen
		chosenFileLabel = new JLabel("No File Chosen");
		GridBagConstraints gbc_chosenFileLabel = new GridBagConstraints();
		gbc_chosenFileLabel.insets = new Insets(0, 0, 5, 5);
		gbc_chosenFileLabel.gridx = 4;
		gbc_chosenFileLabel.gridy = 1;
		frmCompressure.getContentPane().add(chosenFileLabel, gbc_chosenFileLabel);
		
		// Compress button.  Grey'd out until a file is chosen
		btnCompress = new JButton("Compress");
		btnCompress.setEnabled(false);
		GridBagConstraints gbc_btnCompress = new GridBagConstraints();
		gbc_btnCompress.anchor = GridBagConstraints.WEST;
		gbc_btnCompress.insets = new Insets(0, 0, 5, 5);
		gbc_btnCompress.gridx = 1;
		gbc_btnCompress.gridy = 2;
		frmCompressure.getContentPane().add(btnCompress, gbc_btnCompress);
		
		// File chooser button information
		btnChooseAFile = new JButton("Choose a file");
		btnChooseAFile.addActionListener(new ChooserAction());
		
		// Layout information for the file chooser button
		GridBagConstraints gbc_btnChooseAFile = new GridBagConstraints();
		gbc_btnChooseAFile.gridwidth = 2;
		gbc_btnChooseAFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChooseAFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseAFile.gridx = 1;
		gbc_btnChooseAFile.gridy = 1; 
		frmCompressure.getContentPane().add(btnChooseAFile, gbc_btnChooseAFile);
		
		btnDecompress = new JButton("Decompress");
		btnDecompress.setEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		frmCompressure.getContentPane().add(btnDecompress, gbc_btnNewButton);
		
		txtpnWelcomeToCompressure = new JTextPane();
		txtpnWelcomeToCompressure.setText("Welcome to Compressure, a Huffman Encoding compression program.  "
				+ "To begin choose a .txt file using the 'Choose a file' button.  A .huff file will be produced in the same "
				+ "directory as the original file.  You can also use Compressure to decompress .huff files.  Simply choose a "
				+ ".huff file from the menu then hit decompress to write out a .txt file.");
		
		GridBagConstraints gbc_txtpnWelcomeToCompressure = new GridBagConstraints();
		gbc_txtpnWelcomeToCompressure.gridwidth = 4;
		gbc_txtpnWelcomeToCompressure.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnWelcomeToCompressure.fill = GridBagConstraints.BOTH;
		gbc_txtpnWelcomeToCompressure.gridx = 1;
		gbc_txtpnWelcomeToCompressure.gridy = 4;
		frmCompressure.getContentPane().add(txtpnWelcomeToCompressure, gbc_txtpnWelcomeToCompressure);
	}
	
	/* Room for private classes */
	
	/*
	 * Action to take if we need to encode a file
	 */
	private class EncodeAction extends AbstractAction {
		/* Constructor */
		public EncodeAction() {
			putValue(NAME, "Encode");
			putValue(SHORT_DESCRIPTION, "Encode a .txt file.");
		}

		/*
		 * Create a new compressor object and then
		 * call the compress method.
		 */
		public void actionPerformed(ActionEvent e) {
			compress = new Compressor();
			try {
				txtpnWelcomeToCompressure.setText(compress.Compress(file));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // End try/catch
		} // End actionPerformed
	} // End EncodeAction

	
	/*
	 * Action to take if we need to decode a file
	 */
	private class DecodeAction extends AbstractAction {
		/* Constructor */
		public DecodeAction() {
			putValue(NAME, "Decode");
			putValue(SHORT_DESCRIPTION, "Decode a .huff file.");
		}

		/*
		 * Create a new compressor object and then
		 * call the compress method.
		 */
		public void actionPerformed(ActionEvent e) {
			decompress = new Decompressor();
			try {
				decompress.Decompress(file);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // End try/catch
		} // End actionPerformed
	} // End DecodeAction
	
	
	/*
	 * ChooserAction is the definition of the actions to be taken upon
	 * selectin a file.  Handles all of the logic for whether we should
	 * try to encode or decode a file.
	 */
	private class ChooserAction extends AbstractAction {
		/* Constructor */
		public ChooserAction() {
			putValue(NAME, "Encode/Decode");
			putValue(SHORT_DESCRIPTION, "Encode .txt or decode .huff files.");
		}
		
		/* If the button is pressed we need to decide the
		 * main logic of the rest of the program.  
		 * 
		 * If we choose a file that should be decoded, then
		 * make the next button decode it.  If we choose a 
		 * file that should be encoded, make the next button 
		 * encode it.  If we're not sure what kind of file it
		 * is we should probably ask th user
		 */
		public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(btnCompress);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	file = fc.getSelectedFile();
		        	String fileName = file.getName();
		        	chosenFileLabel.setText(fileName);
		        	
		        	// Determine what action should be taken
		        	if( fileName.endsWith(".txt") ){
						btnCompress.setText("Compress");
						btnCompress.setEnabled(true);
						btnDecompress.setEnabled(false);
						// Now we can define the action for the button
						btnCompress.addActionListener(compListener);
						hasCompListener = true;
		        	}else if ( fileName.endsWith(".huff")){
		        		decompress = new Decompressor();
		        		btnDecompress.setEnabled(true);
		        		btnCompress.setEnabled(false);
		        		// Now we can define the action for the button
						btnDecompress.addActionListener(decompListener);
						hasDecompListener = true;
		        	}else{
		        		/*
		        		 * IF THE FILE DOESNT END WITH A KNOWN 
		        		 * SUFFIX ASK THE USER WHAT THEY'D LIKE
		        		 * TO TRY TO DO WITH IT.
		        		 */
		        	} // End if fileName.endsWith
		        } // End APPROVE_OPTION
			} // End actionPerformed
		
		} // End chooserOption
}

