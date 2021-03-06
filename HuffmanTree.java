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
 */
public class HuffmanTree<Symbol> implements Comparable<HuffmanTree<Symbol>>{
	
	final Symbol symbol;
	final Integer frequency;
	final HuffmanTree<Symbol> left, right;
	
	/**
	 * Creates a leaf node with a given symbol and frequency
	 * @param symbol : The symbol being compressed
	 * @param frequency : The number of times it has occurred
	 */
	HuffmanTree(Symbol symbol, Integer frequency){
		this.symbol = symbol;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * Creates a non-leaf node with the frequency set to the sum of the children's frequencies
	 * @param left : Left sub-tree
	 * @param right : Right subtree
	 */
	HuffmanTree(HuffmanTree<Symbol> left, HuffmanTree<Symbol> right){
		symbol = null;
		this.left = left;
		this.right = right;
		frequency = left.frequency + right.frequency;
	}

	/**
	 * Returns the difference between the frequencies of two trees
	 * @param tree : The tree whose root node we wish to compare
	 */
	public int compareTo(HuffmanTree<Symbol> tree ) {
		return this.frequency - tree.frequency;
	}
	
	/**
	 * Prints the symbol and frequency for the root node.
	 */
	public String toString(){
		StringBuilder info = new StringBuilder("Symbol: " + this.symbol + "\t Frequency: " + this.frequency + "\n");
		return info.toString();

	}

}
