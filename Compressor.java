import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.PriorityQueue;

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
	// Variables
	
	/* Compressor takes in a text file and calculates the frequencies
	 * of each character in the text */
	public Compressor(File f) throws Exception{
		PriorityQueue<HuffmanTree> huffTrees = new PriorityQueue<HuffmanTree>();
	}
	
	
	public void Compress(File f) throws Exception{
		try{
			// Calculate the frequency of each character
			String line = null;
			char[] charFreqs = new char[256];
			BufferedReader br = new BufferedReader( new FileReader(f) );
			
			// Calculate the frequency of each character
			while( (line = br.readLine()) != null ){
				for( char c : line.toCharArray() ){
					charFreqs[c]++;
				}
			}
			
		}catch(Exception e){
			// TODO: Upgrade error handling to print to the text field
			throw e;
		}
	}
}
