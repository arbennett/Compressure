import java.io.OutputStream;

/**
 * A helper class so we can write compressed files in binary.
 * @author Andrew Bennett
 * @date July 31, 2014
 */
public class BinaryWriter {

	private OutputStream output;
	private int currentBit, theByte;
	
	public BinaryWriter(OutputStream out){
		try{
			output = out;
			currentBit = 0;
			theByte = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* Adds the given bit to the end and writes
	 * out a byte if we have written 8 bits. */
	public void write(int bit){
		try{
			//System.out.print(bit);
			if( (bit != 0 && bit != 1)){
			   throw new IllegalArgumentException();
			}
			theByte = theByte << 1 | bit;
			currentBit++;
			if(currentBit == 8){
				output.write(theByte);
				currentBit = 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeByte(String b){
		try{
			char[] bits = b.toCharArray();
			for( int i=0; i<8-bits.length; i++){
				write(0);
			}
			for( int i=0 ; i<bits.length ; i++){
				write((int) (bits[i]-48)); //scale for ascii
			}
			System.out.println();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* Makes sure we don't quit in the middle of a
	 * byte, so fill it out with zeros	 */
	public void close(){
		try{
			while (currentBit != 0){
				write(0);
			}
			output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
