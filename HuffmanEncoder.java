package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
		
	private Map<Integer, String> _code_map;
	
	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;
		
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes
		
		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();
		
		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.
		for (int i = 0; i < symbols.length; i++) {
			node_list.add(new LeafHuffmanNode(symbols[i], symbol_counts[i]));
		}

		// Sort the leaf nodes
		node_list.sort(null);

		// While you still have more than one node in your list...
		while(node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			HuffmanNode smallest_node1 = node_list.get(0);
			HuffmanNode smallest_node2 = node_list.get(1);
			node_list.remove(0);
			node_list.remove(0);
			
			// Create a new internal node with those two nodes as children.
			HuffmanNode newNode = new InternalHuffmanNode(smallest_node1.count() + smallest_node2.count(),
					smallest_node1, smallest_node2);
			
			// Add the new internal node back into the list
			node_list.add(newNode);
			// Resort
			node_list.sort(null);
		}

		// Create a temporary empty mapping between symbol values and their code strings
		Map<Integer, String> cmap = new HashMap<Integer, String>();

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		traverseToEachLeaf(cmap, node_list.get(0), "");
		
		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in cmap).
		for (int i = 0; i < symbols.length; i++) {
			sym_with_length.add(new SymbolWithCodeLength(i, cmap.get(i).length()));
		}
		
		// Sort sym_with_lenght
		sym_with_length.sort(null);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor
		
		InternalHuffmanNode canonical_root = new InternalHuffmanNode();
		
		for (int i = 0; i < sym_with_length.size(); i++) {
			canonical_root.insertSymbol(sym_with_length.get(i).codeLength(),
					sym_with_length.get(i).value());
		}
		

		// If all went well, tree should be full.
		assert canonical_root.isFull();
		
		// Create code map that encoder will use for encoding
		
		_code_map = new HashMap<Integer, String>();
		
		// Walk down canonical tree forming code strings as you did before and
		// insert into map.		
		traverseToEachLeaf(_code_map, canonical_root, "");
	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

	private void traverseToEachLeaf(Map<Integer, String> cmap, HuffmanNode node, String codeword) {
		if (!node.isLeaf()) {
			traverseToEachLeaf(cmap, node.left(), codeword + "0");
			traverseToEachLeaf(cmap, node.right(), codeword + "1");
		} else {
			cmap.put(node.symbol(), codeword);
		}
	}
}