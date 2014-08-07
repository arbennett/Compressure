import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

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
	
	public Decompressor(){}
	
	public void Decompress(File f){
		try{
			BinaryReader bitreader = new BinaryReader( new FileInputStream(f) );
			HuffmanTree<Character> huffTree = buildTree(bitreader);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private HuffmanTree<Character> buildTree(BinaryReader bitreader){
		try{
			//System.out.println();
			Boolean eof = false;
			int bit = 0;
			int symbol;
			//bitreader.readByte();
			while(bit != -1){
				bit = bitreader.read();
				System.out.print(bit);
				if(bit == 1){
					//Found a leaf node
					symbol = bitreader.readByte();
					if(symbol > 0){
						//System.out.println((char) symbol);
						return new HuffmanTree<Character>((char) symbol, 0);
					}else{
						return null;
					}
				}else if (bit == 0 ){
					// Look further down the header
					HuffmanTree<Character> leftTree = buildTree(bitreader);
					HuffmanTree<Character> rightTree = buildTree(bitreader);
					return new HuffmanTree<Character>(leftTree,rightTree);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HuffmanTree<Character>('c',0);
	}
}
