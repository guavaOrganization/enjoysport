package com.example.classicalalgorithm;

import java.util.Arrays;

public class SuitableImage {
	/**
	 * 实现以下方法，要求通过图片和容器宽高算出能使图片等比放入容器内的尺寸，返回宽高数值,
	 */
	public static int[] getSuitableImageSize(int imageWidth, int imageHeight, int containerWidth, int containerHeight) {
		int[] resultArr = new int[2];
		// 两者的长高比一致，返回容器的的宽和高
		if (imageWidth * containerHeight == imageHeight * containerWidth) {
			resultArr[0] = containerWidth;
			resultArr[1] = containerHeight;
			return resultArr;
		}
		//如果图片的imageHeight/imageWidth大于containerHeight/containerWidth,缩放之后的高度为containerHeight。只需要算出宽度来即可
		int newImageWidth = 0;
		int newImageHeight = 0;
		if (imageHeight * containerWidth > imageWidth * containerHeight) {
			newImageHeight = containerHeight;
			newImageWidth = containerHeight * imageWidth / imageHeight;
		} else {// 否则缩放之后的宽度为containerWidth
			newImageHeight = imageHeight * containerWidth / imageWidth;
			newImageWidth = containerWidth;
		}
		resultArr[0] = newImageWidth;
		resultArr[1] = newImageHeight;
		return resultArr;
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getSuitableImageSize(4, 8, 10, 5)));
	}
}
