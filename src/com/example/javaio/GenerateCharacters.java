package com.example.javaio;

import java.io.IOException;
import java.io.OutputStream;

// ASCII是一个7位字符集，所以每个字符都作为单字节发送。
public class GenerateCharacters {
	public static void generateCharacters(OutputStream os) throws IOException {
		int firstPrintableCharater = 33; // 第一个需要打印的字符
		int numberOfPrintableCharacters = 94;
		int numberOfCharacterPerLine = 72;// 每行需要打印多少个字符

		int start = firstPrintableCharater;

		byte[] line = new byte[numberOfCharacterPerLine + 2];
		
		while (true) {
			for (int i = start; i < start + numberOfCharacterPerLine; i++) {
				// os.write(((i - firstPrintableCharater) % numberOfPrintableCharacters) + firstPrintableCharater);
				line[i - start] = (byte)(((i - firstPrintableCharater) % numberOfPrintableCharacters) + firstPrintableCharater);
			}
			// os.write('\r');// 回车
			// os.write('\n');// 换行
			line[72] = '\r';
			line[73] = '\n';
			os.write(line);
			start = ((start + 1) - firstPrintableCharater) % numberOfPrintableCharacters + firstPrintableCharater;
		}
	}

	public static void main(String[] args) {
		try {
			generateCharacters(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
