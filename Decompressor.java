import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*
 * Decompressor takes in a .huff file and writes out a 
 * .txt file with the decoded text intact.
 * 
 * @Author: Andrew Bennett
 * @Date: 8 June 2014
 * @ParamIn: File f - the file to be decompressed.
 * @ParamOut: File fout - the decompressed file.
 * 
 */
public class Decompressor {
	Boolean eof;
	
	/** Blank constructor */
	public Decompressor(){eof = false;}
	
	
	/**
	 * Decompress a file
	 * @param f : The file to decompress
	 * @return : Some info about where the decompressed file is
	 */
	public String Decompress(File f){
		String info = "Error decompressing your file.  I must stop, sorry...";
		try{
			File infile = f;
			String outFileName = infile.getPath().substring(0, infile.getPath().length() - 5) + "_decompressed.txt";
			File outfile = new File(outFileName);
			BinaryReader bitreader = new BinaryReader( new FileInputStream(infile) );
			bitreader.readByte();
			HuffmanTree<Character> huffTree = buildTree(bitreader);
			HashMap<String,Character> codeMap = buildMap(new HashMap<String,Character>(), huffTree, new StringBuilder());
			bitreader.readByte(); bitreader.read();
			String decoded = decode(bitreader, codeMap);
			writeFile(outfile, decoded);
			info = "Your file has been written to " + outFileName + ".";
		}catch(Exception e){
			e.printStackTrace();
		}
		return info;
	}
	
	
	/**
	 * Build the huffman tree from the header portion of the compressed file
	 * @param bitreader : Reader of the compressed file
	 * @return The reconstructed huffman tree
	 */
	private HuffmanTree<Character> buildTree(BinaryReader bitreader){
		try{
			//System.out.println();
			int bit = 0;
			int symbol;
			do{
				bit = bitreader.read();
				if(bit == 1){
					//Found a leaf node
					symbol = bitreader.readByte();
					if(symbol > 0){
						return new HuffmanTree<Character>((char) symbol, 0);
					}else{
						eof = true;
					}
				}else if (bit == 0){
					// Look further down the header
					HuffmanTree<Character> leftTree = buildTree(bitreader);
					HuffmanTree<Character> rightTree = buildTree(bitreader);
					return new HuffmanTree<Character>(leftTree,rightTree);
				}
			}while(bit!=-1 && eof!=true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HuffmanTree<Character>((char) 0,0);
	}
	
	
	/**
	 * Recursively build the map of characters and codes from the huffman tree
	 * @param codeMap: The key,value pairs for the codes and symbols (to be constructed)
	 * @param huffTree : A reconstruction of the huffmantree from the encoding
	 * @param code : Stringbuilder that reads in the code portion of the encoding
	 * @return The completed key,value pairs for the codes and symbols from the encoding
	 */
	private HashMap<String,Character> buildMap(HashMap<String,Character> codeMap, HuffmanTree<Character> huffTree, StringBuilder code){
		if (huffTree.symbol != null){
			// Put the <Symbol,Code> pair in the map
			codeMap.put(code.toString(),huffTree.symbol);
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
	 * Go through the data portion of the compresed file and use the codeMap to decode it 
	 * @param bitreader : The reader for the compressed file
	 * @param codeMap : A key,value pair for the codes and symbols of the compressed file
	 * @return The decoded string
	 */
	private String decode(BinaryReader bitreader,HashMap<String, Character> codeMap) {
		StringBuilder code = new StringBuilder();
		StringBuilder decoded = new StringBuilder();
		//int charsLeft = textLength;
		int bit = bitreader.read();
		// A do.. while just for the fun of it.
		do{
			code.append(bit);
			if (codeMap.containsKey(code.toString())){
				decoded.append(codeMap.get(code.toString()));
				code.setLength(0);
				//charsLeft--;
			}
			bit = bitreader.read();
		}while(bit!=-1);

		return decoded.toString();
	}
	
	/**
	 * Write out the decoded file
	 * @param outfile : The output file
	 * @param decoded : The resultant string from decompression
	 */
	private void writeFile(File outfile, String decoded){
		try{
			FileWriter fw = new FileWriter(outfile);
			fw.write(decoded);
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a string with some information about the compression that has been done.
	 * @return : String containing information about the symbols and their frequencies.
	 */
	public String printInfo(HashMap<String,Character> codeMap){
		StringBuilder info = new StringBuilder();
		Iterator<Entry<String,Character>> it = codeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Character> pairs = (Map.Entry<String,Character>)it.next();
			info.append("  " + pairs.getKey() + "\t" + pairs.getValue() + "\n");
		}
		return info.toString();
	}
}
