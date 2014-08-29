import java.io.InputStream;

/**
 * A helper class so we can read compressed files in binary.
 * @author Andrew Bennett
 * @date July 31, 2014
 */
public class BinaryReader {

	private InputStream input;
	private int currentBit, theByte;
	
	
	/**
	 * Constructor takes an input stream and sets up some data
	 * @param in
	 */
	public BinaryReader(InputStream in){
		try{
			input = in;
			currentBit = 0;
			theByte = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Reads a bit
	 * @return
	 */
	public int read(){
		try{
			/* If we need to go to a new byte */
			if(currentBit == 8){
				theByte = input.read();
				if(theByte == -1){
					return -1;
				}
				currentBit = 0;
			}
			/* Record the bit that we are at */
			currentBit++;
		}catch(Exception e){
			e.printStackTrace();
		}
		/* Shift bits to get the current ones */
		return (theByte >>> (8-currentBit)) & 1;
	}
	
	/**
	 * Read a full byte from the current spot
	 * @return
	 */
	public int readByte(){
		int result = 0;
		int b;
		try{
			for(int i=7 ; i>=0 ; i--){
				b = read();
				if(b == -1){
					break;
				}
				/* A different way of shifting */
				result+=b*Math.pow(2, i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
