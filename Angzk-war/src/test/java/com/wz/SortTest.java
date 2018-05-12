package com.wz;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 *
 * @ClassName: SortTest
 * @Description:TODO 冒泡排序
 * @author: Angkz
 * @date: 2018年3月14日 下午4:48:50
 *
 */
public class SortTest {
	private static int[] aa =  new int[8]; 
	private static int[] bb = {1,3,2,8,10}; 

	public static void test1(int[] score) {
		for (int i = 0; i < score.length - 1; i++) {
			for (int j = 0; j < score.length - 1 - i; j++)// j开始等于0，
			{
				if (score[j] < score[j + 1]) {
					int temp = score[j];
					score[j] = score[j + 1];
					score[j + 1] = temp;
				}
			}
		}
	}
	public static void wztest(int[] array){
		// TODO 正向遍历数组
		for (int i = 0; i < array.length-1; i++) {
			for(int j = 0; j < array.length-1-i ; i++) {
				
			}
		}
	}
	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			 Random rand = new Random();
			 int j =  rand.nextInt(1000) + 1;
			  aa[i]=j ;
		}
		long begin = System.currentTimeMillis();
		// SortTest.test1(aa);
		SortTest.test3(bb);
		long end = System.currentTimeMillis();
		
		end = end - begin;
		System.err.println("执行耗时：====》"+end);
		
		System.err.println(Arrays.toString(aa));
	}

	public static void test2(int[] score) {
		for (int i = 0; i < score.length - 1; i++) {
			for (int j = (score.length - 2); j >= 0; j--) {
				if (score[j] < score[j + 1]) {
					int temp = score[j];
					score[j] = score[j + 1];
					score[j + 1] = temp;
				}
			}
		}
	}

	public static void test3(int[] arr) {
		int temp;
		for (int i = 0; i < arr.length; i++) {
			for (int j = arr.length - 1; j > i; j--) {
				if (arr[j] < arr[j - 1]) {
					temp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
				}
			}
		}
	}
}
