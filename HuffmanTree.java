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
public class HuffmanTree<Symbol> implements Comparable<HuffmanTree> {
	public final int frequency;
	public final Symbol symbol;
	public final HuffmanTree<Symbol> left, right;
	
	/*
	 * Constructor for a node of the huffman tree.  Initializes
	 * the left and right subtrees as null.
	 * 
	 * @param sym: The data we are trying to (de)compress
	 * @param freq: The number of times the data occurs
	 * 
	 */
	public HuffmanTree(Symbol sym, int freq){
		this.left = null;
		this.right = null;
		this.frequency = freq;
		this.symbol = sym;
	}
	
	/*
	 * Constructor for non-leaf nodes.  Leaves the symbol null and
	 * updates the frequency based off of the inputs.
	 * 
	 * @param left: the left huffman subtree
	 * @param right: the right huffman subtree
	 */
	public HuffmanTree(HuffmanTree<Symbol> left, HuffmanTree<Symbol> right){
		this.left = left;
		this.right = right;
		this.frequency = left.frequency + right.frequency;
		this.symbol = null;
	}

	/*
	 * Simple comparison of the frequencies of a given root node.
	 * 
	 * @param tree: the huffman tree to compare nodes to
	 */
	public int compareTo(HuffmanTree tree) {
		return this.frequency - tree.frequency;
	}
	
	/*
	 * Print some useful information about the huffman tree.
	 * Mostly used for debugging purposes.
	 */
	public String toString(){
		return "Frequency:\t" + this.frequency + "\nSymbol:\t" + this.symbol;
	}



}
