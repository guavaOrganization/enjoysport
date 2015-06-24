package com.example.classicalalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Card {
	public int color;
	public int number;

	@Override
	public String toString() {
		String colorString = "";
		String numberString = "";
		switch (color) {
		case 0:
			colorString = "♠";
			break;
		case 13:
			colorString = "♥";
			break;
		case 26:
			colorString = "♣";
			break;
		case 39:
			colorString = "♦";
			break;
		}
		switch (number) {
		case 11:
			numberString = "J";
			break;
		case 12:
			numberString = "Q";
			break;
		case 13:
			numberString = "K";
			break;
		case 14:
			numberString = "A";
			break;
		default:
			numberString = String.valueOf(number);
			break;
		}
		return colorString + numberString;
	}

	public static List<Card> createCards() {
		List<Card> cards = new ArrayList<Card>(52);
		for (int i = 0; i < 52; i++) {
			Card card = new Card();
			card.number = i % 13 + 2;
			card.color = i / 13 * 13;
			cards.add(card);
		}
		Collections.shuffle(cards);
		return cards;
	}

	public static void main(String[] args) {
		List<Card> cards = createCards();
		System.out.println("*********排序前*********");
		Iterator<Card> cardsIter = cards.iterator();
		while (cardsIter.hasNext()) {
			System.out.print(cardsIter.next());
		}
		
		quickSort(cards, 0, cards.size() - 1);
		cardsIter = cards.iterator();
		System.out.println();
		System.out.println("*********排序后*********");
		while (cardsIter.hasNext()) {
			System.out.print(cardsIter.next());
		}
	}

	public static int partition(List<Card> list, int low, int high) {
		int tmpColor = list.get(low).color;
		int tmpNumber = list.get(low).number;
		int weight = list.get(low).color + list.get(low).number;
		while (low < high) {
			while (low < high && list.get(high).color + list.get(high).number > weight) {
				high--;
			}

			list.get(low).color = list.get(high).color;
			list.get(low).number = list.get(high).number;
			while (low < high && list.get(low).color + list.get(low).number < weight) {
				low++;
			}

			list.get(high).color = list.get(low).color;
			list.get(high).number = list.get(low).number;
		}
		
		list.get(low).color = tmpColor;
		list.get(low).number = tmpNumber;

		return low;
	}

	// quick sort
	public static void quickSort(List<Card> list, int low, int high) {
		if (low < high) {
			int middle = partition(list, low, high);
			quickSort(list, low, middle - 1);
			quickSort(list, middle + 1, high);
		}
	}
}
