import java.io.File;
import java.io.FileInputStream;
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
	
	public Decompressor(){
		
	}
	
	public void Decompress(File f){
		try{
			
		}catch(Exception e){
			throw e;
		}
	}
	
	private HuffmanTree<Character> buildTree(BinaryReader bitreader){
		try{
			Boolean eof = false;
			int bit;
			while(!eof){
				bit = bitreader.read();
				if(bit == 1){
					//Found a leaf node
					return new HuffmanTree<Character>((char) bitreader.readByte(), 0);
				}else if (bit == 0 ){
					// Look further down the header
					HuffmanTree<Character> leftTree = buildTree(bitreader);
					HuffmanTree<Character> rightTree = buildTree(bitreader);
					return new HuffmanTree<Character>(leftTree,rightTree);
				}else if (bit == -1){
					
				}else{
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HuffmanTree<Character>('c',0);
	}
}
