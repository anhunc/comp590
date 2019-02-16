package main;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {
	
	// Instance fields should be declared here.
	private int value;
	private int code_length;
	
	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		this.value = value;
		this.code_length = code_length;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		// Needs implementation
		return code_length;
	}

	// value() returns the symbol value of the symbol
	public int value() {
		// Needs implementation
		return value;
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength symWithCodeLength2) {
		// Needs implementation
		if (Integer.compare(code_length, symWithCodeLength2.codeLength()) != 0) {
			return Integer.compare(code_length, symWithCodeLength2.codeLength());
		}
		
		return Integer.compare(value, symWithCodeLength2.value());
	}
}