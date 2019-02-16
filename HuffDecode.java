package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		long start = System.nanoTime();
		
		String input_file_name = "/Users/c2/Desktop/recompressed2.txt";
		String output_file_name = "/Users/c2/Desktop/decoded4.txt";
		
		System.out.println("decoding file: " + input_file_name);
		
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with each symbol as a 
		// SymbolWithCodeLength object and add to the list symbols_with_length.
		for (int i = 0; i < 256; i++) {
			symbols_with_length.add(new SymbolWithCodeLength(i, bit_source.next(8)));
		}
		
		// Then sort they symbols.
		symbols_with_length.sort(null);

		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);
		
		int num_symbols = 0;

		// Read in the next 32 bits from the input file  the number of symbols
		num_symbols = bit_source.next(32);

		try {

			// Open up output file.
			
			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int i=0; i<num_symbols; i++) {
				// Decode next symbol using huff_tree and write out to file.
				fos.write(huff_tree.decode(bit_source));
			}

			// Flush output and close files.
			
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("done, outputted to: " + output_file_name);
		
		long end = System.nanoTime();
		double totalTime = (end - start) / (double) 1000000000;
		System.out.println("decoder run time: " + totalTime + "s");
	}
}

