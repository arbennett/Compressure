import java.io.InputStream;

/**
 * A helper class so we can read compressed files in binary.
 * @author Andrew Bennett
 * @date July 31, 2014
 */
public class BinaryReader {

	private InputStream input;
	private int currentBit, theByte;
	
	public BinaryReader(InputStream in){
		try{
			input = in;
			currentBit = 0;
			theByte = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int read(){
		try{
			if(currentBit == 8){
				theByte = input.read();
				if(theByte == -1){
					return -1;
				}
				currentBit = 0;
			}
			currentBit++;
		}catch(Exception e){
			e.printStackTrace();
		}
		return (theByte >>> (8-currentBit)) & 1;
	}
	
	public int readByte(){
		int result = 0;
		try{
			for(int i=7 ; i>=0 ; i--){
				result+=read()*Math.pow(2, i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
