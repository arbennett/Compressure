import java.io.File;

/*
 * The Compressor class will take care of compressing 
 * text files and outputting the .huff files.
 * 
 * @author: Andrew Bennett
 * @date 8 June 2014
 * 
 */

/*
 * 1. Parse file and count frequency of each symbol
 * 2. Leaf nodes are each symbol and it's associated frequency
 * 3. Parent node of each pair of nodes holds the sum of the frequencies
 * 4. Nodes linked to left are encoded by 0, nodes linked to right are encoded by 1
 * 5. Traverse in order to each leav node, keeping track of the path - this is the huffman code
 * 6. Write out huffman code in this format:
 * 		Symbol Code
 */

public class Compressor {
	// Variabless
	
	public Compressor(){
		// Do something here
	}
	
	public void Compress(File f){
		try{
			// Instantiate huffman tree and run algorithm
		}catch(Exception e){
			// TODO: Upgrade error handling to print to the text field
			throw e;
		}
	}
}
