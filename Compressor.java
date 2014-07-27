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
	PriorityQueue<HuffmanTree<Character>> huffTrees;
	
	/* Compressor takes in a text file and calculates the frequencies
	 * of each character in the text */
	public Compressor() throws Exception{
		huffTrees = new PriorityQueue<HuffmanTree<Character>>();
	}
	
	
	public void Compress(File f) throws Exception{
		try{
			// Calculate the frequency of each character
			String line = null;
			int[] charFreqs = new int[256];
			BufferedReader br = new BufferedReader( new FileReader(f) );
	
			// Calculate the frequency of each character
			while( (line = br.readLine()) != null ){
				for( char c : line.toCharArray() ){
					charFreqs[c]++;
				}
			}
			
			// Now put it into the huffman tree
			for(int i = charFreqs.length-1; i > -1; --i){
				if(charFreqs[i] > 0){
					huffTrees.offer(new HuffmanTree<Character>((char) i, charFreqs[i]));
				}
			}
			
			// Build the master tree
			while( huffTrees.size() > 1 ){
				HuffmanTree<Character> one = huffTrees.poll();
				HuffmanTree<Character> two = huffTrees.poll();
				HuffmanTree<Character> combine = new HuffmanTree<Character>(one, two);
				huffTrees.offer(combine);
			}
			
			StringBuilder code = new StringBuilder();
			writeCodes(huffTrees.poll(), code);
			
		}catch(Exception e){
			// TODO: Upgrade error handling to print to the text field
			throw e;
		}
	}
	
	private void writeCodes(HuffmanTree<Character> huffTree, StringBuilder code ){
		// Check if we are not at a leaf node
		if( huffTree.symbol == null ){
			// Add a 0 for going down the left path
			code.append('0');
			writeCodes(huffTree.left, code);
			code.deleteCharAt(code.length()-1);
			// Add a 1 for going down the right path
			code.append('1');
			writeCodes(huffTree.right, code);
			code.deleteCharAt(code.length()-1);
		// We are at a leaf and have a constructed code for this symbol
		}else{
			System.out.println(huffTree.symbol + "\t" + huffTree.frequency + "\t" + code.toString());
		}
	}
}
