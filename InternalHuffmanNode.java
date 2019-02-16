package main;

public class InternalHuffmanNode implements HuffmanNode {
	
	private int count;
	private int height;
	private boolean isFull;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public InternalHuffmanNode() { };
	
	public InternalHuffmanNode(int count, HuffmanNode left, HuffmanNode right) {
		this.count = count;
		this.left = left;
		this.right = right;
	}

	// AKA frequency of the symbol
	@Override
	public int count() {
		return count;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int symbol() {
		return -1;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public boolean isFull() {
		if (isFull) return true;
		// either child is null means it's not full
		else if (left == null || right == null) {
			isFull = false;
			return false;
		}
		else if (left.isLeaf() && right.isLeaf()) {
			isFull = true; // if both children are leaves, then it's full
			return isFull;
		}
		
		boolean isLeftFull = left.isFull();
		boolean isRightFull = right.isFull();
		
		if (isLeftFull && isRightFull) isFull = true;
		else if (isLeftFull && !isRightFull) { // if the right is internal node, recursive
			return isRightFull;
		}
		
		return isFull;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
//		System.out.println("Inserting: " + symbol);
		// Try to insert into the left side //
		if (left == null) {
			// If left is null and length is 1, then set left as the symbol
			if (length == 1) {
//				System.out.println("Length(" + length + ") = 1, left = null, making new leaf node at left\n\n");
				left = new LeafHuffmanNode(symbol, length);
			}
			// If left is null, make a new node until the length is 1
			else if (length > 1) {
//				System.out.println("Length(" + length + ") > 1, left = null, making new internal node at left");
				left = new InternalHuffmanNode();
				left.insertSymbol(length - 1, symbol);
			}
		}
		// If left isn't null, keep traversing down the tree
		else if (!left.isFull()) {
			if (length == 1) {
//				System.out.println("Something wrong with insert");
				return false; // means something wrong with tree
			}
			if (length > 1) {
//				System.out.println("Length(" + length + ") > 1, left != null, traversing down, " + length + " left to go");
				left.insertSymbol(length - 1, symbol);
			}
		}
		// if left is full, try the right side
		else if (right == null) {
			// If right is null and length is 1, then set right as the symbol
			if (length == 1) {
//				System.out.println("Length(" + length + ") = 1, right = null, making new leaf node at right\n\n");
				right = new LeafHuffmanNode(symbol, length);
			}
			// If right is null and length > 1, make a new node until length is 1
			else if (length > 1) {
//				System.out.println("Length(" + length + ") > 1, right = null, making new internal node at right");
				right = new InternalHuffmanNode();
				right.insertSymbol(length - 1, symbol);
			}
		}
		// If right isn't null, keep traversing down the tree
		else if (right != null && !right.isFull()) {
			if (length == 1) {
//				System.out.println("Something wrong with insert");
				return false; // means something is wrong with tree
			}
			if (length > 1) {
//				System.out.println("Length(" + length + ") > 1, right != null, traversing down, " + length + " left to go");
				right.insertSymbol(length - 1, symbol);
			}
		}

//		System.out.println("Inserted " + symbol + " successfully");
		return true;
	}

	@Override
	public HuffmanNode left() {
		return left;
	}

	@Override
	public HuffmanNode right() {
		return right;
	}
}