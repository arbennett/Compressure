/*
 * The HuffmanTree class is an implementation of the
 * Huffman Tree data structure.  
 * 
 * Huffman Trees are constructed with each of the symbols
 * and their respective frequencies at the leaf nodes. 
 * Each non-leaf node contains the sum of the frequencies
 * of the children.  
 * 
 * To get a symbol's Huffman code simply traverse to 
 * the leaf containing that symbol.  Each left adds a 0
 * and each right adds a 1.  
 * 
 * @Author: Andrew Bennett
 * @Date: 8 June 2014
 * 
 * TODO: Should symbol just be an interpreted int or should it be left as a char?
 */
public class HuffmanTree {
	
	HuffmanNode root, left, right, parent;
	
	public HuffmanTree(){
		root = new HuffmanNode();
	}
	
	/*
	 * The HuffmanNode class is what Huffman 
	 * Trees are made out of.
	 */
	private static class HuffmanNode implements Comparable<HuffmanNode>{
		char symbol;
		int frequency;
		HuffmanNode left, right, parent;
		
		/*
		 * Null constructor 
		 */
		private HuffmanNode(){}
		
		/*
		 *  Constructor to build leaf nodes in tree
		 *  @param symbol - the character from the text being compressed
		 *  @param frequency - the number of times the symbol occurred in the text
		 */
		private HuffmanNode(char symbol, int frequency){
			this.symbol = symbol;
			this.frequency = frequency;
		}
		
		/*
		 *  Construct a non-leaf node.  This constructor automatically
		 *  orders nodes so that left.frequency > right.frequency.
		 *  
		 *  @param left - the left node
		 *  @param right - the right node
		 */
		private HuffmanNode(HuffmanNode left, HuffmanNode right){
			if (left.compareTo(right) > 0){
				this.left = left;
				this.right = right;
				this.frequency = left.frequency + right.frequency;
			}else{
				this.right = left;
				this.left = right;
				this.frequency = left.frequency + right.frequency;
			}
		}

		// Return the difference between frequencies
		// Will use the sign of this to determine where 
		// to put nodes in the tree.
		public int compareTo(HuffmanNode node) {
			return this.frequency - node.frequency;
		}
		
	}

}
