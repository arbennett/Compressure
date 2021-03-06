import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
	
	
	/** Blank constructor */
	public Compressor(){}
	
	
	/**
	 * Compress a file
	 * @param f : The file to compress
	 * @return : Some information about the compression
	 */
	public String Compress(File f){
		String info;
		try{
			// Instantiate huffman tree and run algorithm
			File infile = f;
			String outFileName = infile.getPath().substring(0, infile.getPath().length() - 4) + ".huff";
			File outfile = new File(outFileName);
			int[] charFreqs = countFrequency(infile);
			HuffmanTree<Character> huffTree = buildTree(charFreqs);
			HashMap<Character,String> codeMap = buildMap(new HashMap<Character,String>(), huffTree, new StringBuilder());
			writeFile(huffTree,codeMap, charFreqs[256], outfile, infile);
			info = printInfo(infile, outfile, codeMap);
		}catch(Exception e){
			// TODO: Upgrade error handling to print to the text field
			throw e;
		}
		return info;
	}
	
	
	/**
	 * Open up the file and count the number of time each character was found.  Return 
	 * the array corresponding to this count.
	 * 
	 * @param inFile : the file we are trying to compress
	 * @return charFreqs : the frequency of each character in the extended ascii alphabet
	 */
	private int[] countFrequency(File infile){
		int[] charFreqs = new int[257];
		try{
			BufferedReader reader = new BufferedReader(new FileReader(infile));
			String line;
			while( (line = reader.readLine()) != null ){
				for(char c : line.toCharArray()){
					if((int)c<257){
						charFreqs[c]++; // For now we will just count ascii characters.
						charFreqs[256]++;
					}else{
						throw new IllegalArgumentException("Illegal character: " + c);
					}
				}
			}
			reader.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return charFreqs;
	}
	
	
	/**
	 * Build the final huffman tree by inserting leaf huffman trees into a priority queue
	 * and then build our way up by combining these trees until we have one master tree
	 * @param charFreqs : The frequencies of each character in the original file
	 * @return the master tree
	 */
	private HuffmanTree<Character> buildTree(int[] charFreqs){
		PriorityQueue<HuffmanTree<Character>> huffQueue = new PriorityQueue<HuffmanTree<Character>>();
		HuffmanTree<Character> tempTree, leftTree, rightTree;
		// Put all of our trees into the queue
		for(int i = 0; i < charFreqs.length -1 ; i++){
			if(charFreqs[i] > 0){
				tempTree = new HuffmanTree<Character>((char) i , charFreqs[i]);
				huffQueue.offer(tempTree);
			}
		}
		
		// Build the master tree
		while(huffQueue.size() > 1){
			leftTree = huffQueue.poll();
			rightTree = huffQueue.poll();
			tempTree = new HuffmanTree<Character>(leftTree, rightTree);
			huffQueue.offer(tempTree);
		}
		return huffQueue.poll();
	}
	
	
	/**
	 * Recursively build a hashmap of the <symbol, huffman code> pairs.
	 * @param huffTree : the huffman tree of characters and frequencies
	 * @return codeMap : Key,value pairs of the symbol and codes
	 */
	private HashMap<Character, String> buildMap(HashMap<Character,String> codeMap, HuffmanTree<Character> huffTree, StringBuilder code){
		if (huffTree.symbol != null){
			// Put the <Symbol,Code> pair in the map
			codeMap.put(huffTree.symbol,code.toString());
		} else {
			// Traverse left
			code.append(0);
			codeMap = buildMap(codeMap, huffTree.left, code);
			code.deleteCharAt(code.length()-1);
			
			// Traverse right
			code.append(1);
			codeMap = buildMap(codeMap, huffTree.right, code);
			code.deleteCharAt(code.length()-1);
		}
		
		return codeMap;
	}
	
	/**
	 * Write out the header piece of the compressed file that 
	 * allows us to decode the file once it is read back in
	 * @param output : Writer for compressed file
	 * @param huffTree : The Huffman tree constructed from buildTree(..)
	 */
	private void writeHeader(BinaryWriter output, HuffmanTree<Character> huffTree){
		if(huffTree.symbol == null){
			output.write(0);
			if(huffTree.left != null)  writeHeader(output,huffTree.left);
			if(huffTree.right != null) writeHeader(output,huffTree.right);
		}else{
			output.write(1);
			Integer symbol = (int) huffTree.symbol.charValue();
			output.writeByte( Integer.toBinaryString(symbol) );
		}
	}
	
	/**
	 * Writes the compressed file in binary form.
	 * @param huffTree : The huffmanTree from buildTree
	 * @param codeMap : The key,value pairs of the symbols and their codes 
	 * @param textLength : How long the text was
	 * @param outfile : The output file
	 * @param infile : The input file
	 */
	private void writeFile(HuffmanTree<Character> huffTree, HashMap<Character,String> codeMap, int textLength, File outfile, File infile){
		Integer eof = 0;
		try{
			FileWriter decode = new FileWriter(outfile);
			BinaryWriter output = new BinaryWriter ( new FileOutputStream(outfile, true) );
			
			// Write out the header (see above method)
			writeHeader(output, huffTree);
			output.write(1); // Add a signal for the end of the header
			output.writeByte("0");
			
			// Write out the binary 
		    BufferedReader reader = new BufferedReader(new FileReader(infile));
			String line, code;
			while( (line = reader.readLine()) != null ){
				for(char codeChar : line.toCharArray()){
					// Get the code for each character
					code = codeMap.get(codeChar);
					//System.out.println(codeChar + " " + code);
					for( char bit : code.toCharArray() ){
						output.write((int) (bit - 48)); //Scaled for ascii
					}
				}
			}
			reader.close();
			output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a string with some information about the compression that has been done.
	 * @return some information about the encoding
	 */
	public String printInfo(File infile, File outfile, HashMap<Character,String> codeMap){
		StringBuilder info = new StringBuilder();
		info.append("Input file size:\t" + infile.length() + "\nOutput file size:\t" + outfile.length() + "\nCompression ratio:\t" + (double) (outfile.length())/infile.length() + "\n\n"); 
		Iterator<Entry<Character, String>> it = codeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Character, String> pairs = (Map.Entry<Character, String>)it.next();
			info.append("  " + pairs.getKey() + "\t" + pairs.getValue() + "\n");
		}
		return info.toString();
	}
}
