package com.example.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GenerateCharacter {
	public static void generatorCharacters(OutputStream os) throws IOException {
		int firstPrintableCharacter = 33;
		int numberPrintableCharacters = 94;
		int numbercharactersPerLine = 72;

		int start = firstPrintableCharacter;

		for (int i = start; i < start + numbercharactersPerLine; i++) {
			os.write(((i - firstPrintableCharacter) % numberPrintableCharacters)
					+ firstPrintableCharacter);
		}
		os.write('\r');// 回车
		os.write('\n');// 换行
		start = ((start + 1) - firstPrintableCharacter)
				% numberPrintableCharacters + firstPrintableCharacter;
		System.out.println(os.toString());
	}

	public static void main(String[] args) throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		for (int i = 33; i < 126; i++) {
			os.write(i);
		}
		os.write('\r');// 回车
		os.write('\n');// 换行
		System.out.println(os.toString());
		// generatorCharacters(new ByteArrayOutputStream());
	}
}
