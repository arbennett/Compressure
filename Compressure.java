import java.io.File;

/*
 * Compressure is a simple application to do some 
 * Huffman Encoding on a text file.  
 * 
 * @author: Andrew Bennett
 * @date: 8 June 2014
 * 
 */

public class Compressure {

	public static void main(String [] args){
		// create the gui
		System.out.println("Building Compressure...");
		//HuffWindow theWindow = new HuffWindow();
		
		// testing the compression
		File file;
		Compressor compress;
		try {
			file = new File("test.txt");
			compress = new Compressor();
			compress.Compress(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
