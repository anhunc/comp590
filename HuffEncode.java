package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws IOException {
		long start = System.nanoTime();
		
		String input_file_name = "/Users/c2/Desktop/decoded4.txt";
		String output_file_name = "/Users/c2/Desktop/recompressed2.txt";
		
		System.out.println("encoding file: " + input_file_name);

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		
		// Read in each symbol (i.e. byte) of input file and 
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols 
		// (i.e., length of file) as num_symbols
		int next_byte = fis.read();
		while (next_byte != -1) {
			symbol_counts[next_byte]++;
			num_symbols++;
			
			next_byte = fis.read();
		}

		// Close input file
		fis.close();

		// Create array of symbol values
		
		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
		}
		
		// Create encoder using symbols and their associated counts from file.
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);
		
		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);
		
		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < symbols.length; i++) {
			bit_sink.write(encoder.getCode(symbols[i]).length(), 8);
		}
		
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 32);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
        // out to output file.
		for (int i = 0; i < num_symbols; i++) {
			int next_symbol = fis.read();
			bit_sink.write(encoder.getCode(next_symbol));
		}
		

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
		
		System.out.println("done, outputted to: " + output_file_name);
		
		long end = System.nanoTime();
		double totalTime = (end - start) / (double) 1000000000;
		System.out.println("encoder run time: " + totalTime + "s");
	}
}
