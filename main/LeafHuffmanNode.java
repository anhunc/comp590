package main;

public class LeafHuffmanNode implements HuffmanNode {
	
	private int symbol;
	private int count;
	
	public LeafHuffmanNode(int symbol, int count) {
		this.symbol = symbol;
		this.count = count;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public int symbol() {
		return symbol;
	}

	@Override
	public int height() {
		return 0;
	}

	@Override
	public boolean isFull() {
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		return false;
	}

	@Override
	public HuffmanNode left() {
		return null;
	}

	@Override
	public HuffmanNode right() {
		return null;
	}
	
}
