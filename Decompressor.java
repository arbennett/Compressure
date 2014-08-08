import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	 * 
	 * @param f
	 */
	public void Decompress(File f){
		try{
			BinaryReader bitreader = new BinaryReader( new FileInputStream(f) );
			for(int i=0; i<8; i++) 
				bitreader.read();
			
			HuffmanTree<Character> huffTree = buildTree(bitreader);
			HashMap<String,Character> codeMap = buildMap(new HashMap<String,Character>(), huffTree, new StringBuilder());
			System.out.println();
			System.out.println(printInfo(codeMap));
			
			for(int i=0; i<9; i++) 
				bitreader.read();
			
			String decoded = decode(bitreader, codeMap, new StringBuilder());
			
			System.out.println();
			System.out.println(decoded);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param bitreader
	 * @return
	 */
	private HuffmanTree<Character> buildTree(BinaryReader bitreader){
		try{
			//System.out.println();
			int bit = 0;
			int symbol;
			do{
				bit = bitreader.read();
				System.out.print(bit);
				if(bit == 1){
					//Found a leaf node
					symbol = bitreader.readByte();
					if(symbol > 0){
						System.out.println();
						System.out.println(symbol);
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
	 * 
	 * @param codeMap
	 * @param huffTree
	 * @param code
	 * @return
	 */
	private HashMap<String,Character> buildMap(HashMap<String,Character> codeMap, HuffmanTree<Character> huffTree, StringBuilder code){
		if (huffTree.symbol != null){
			// Put the <Symbol,Code> pair in the map
			//System.out.println(huffTree.symbol);
			//System.out.println(code.toString());
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
	 * 
	 * @param bitreader
	 * @param codeMap
	 * @param stringBuilder
	 * @return
	 */
	private String decode(BinaryReader bitreader,HashMap<String, Character> codeMap, StringBuilder decoded) {
		StringBuilder code = new StringBuilder();
		int bit = bitreader.read();
		do{
			code.append(bit);
			if (codeMap.containsKey(code.toString())){
				decoded.append(codeMap.get(code.toString()));
				code.setLength(0);
			}
			bit = bitreader.read();
		}while(bit!=-1);
		// TODO Auto-generated method stub
		return decoded.toString();
	}
	
	
	/**
	 * Returns a string with some information about the compression that has been done.
	 * @return
	 */
	public String printInfo(HashMap<String,Character> codeMap){
		StringBuilder info = new StringBuilder();
		//info.append("Input file size:\t" + infile.length() + "\nOutput file size:\t" + outfile.length() + "\nCompression ratio:\t" + (double) (outfile.length())/infile.length() + "\n\n"); 
		Iterator<Entry<String,Character>> it = codeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Character> pairs = (Map.Entry<String,Character>)it.next();
			info.append("  " + pairs.getKey() + "\t" + pairs.getValue() + "\n");
		}
		return info.toString();
	}
}
