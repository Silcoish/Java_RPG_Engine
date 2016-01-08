package com.corey.silcoish.flags;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Flags are used to track 1-time events
 */

public class Flags {

	private int dataSlots = 500;
	public int[] flags = new int[dataSlots];
	private int i = 0;
	private String fileName = "/a/save.dat";
	private String defaultText = "Flags: ";

	// 0 - Unused
	// 1 - Chest - Info Room
	// 2 - Chest - Info Room
	// 3 - Chest - Overworld
	// 4 - Chest - Overworld
	// 5 - Chest - Overworld
	// 6 - Door - Level 1
	// 7 - Door - Level 1
	// 8 - Chest - Level 1
	// 9 - Chest - level 1
	//10 - Key in bat - Level 1
	//11 - USED
	//12 - Bomb bad - Level 1
	// 99 - Test Heart Container - Info Room

	//Load all the flags into the flags array, ready to be polled
	public Flags() {
		for (int o = 0; o < dataSlots; o++) {
			flags[o] = -1;
		}

		String location = System.getenv("APPDATA");
		File file = new File(location + fileName);
		if (!file.exists()) {
			System.out.println(defaultText + "Making directory and file");
			new File(location + "/a").mkdirs();
			try {
				file.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				for (int l = 0; l < dataSlots; l++) {
					out.write("0");
					out.newLine();
				}
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String str;
			while ((str = br.readLine()) != null) {
				try {
					int foo = Integer.parseInt(str);
					flags[i] = foo;
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(defaultText + "Error with the data! Please check the save data for errors!");
					System.exit(0);
				}

				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		String location = System.getenv("APPDATA");
		File file = new File(location + fileName);
		file.delete();
		if (!file.exists()) {
			System.out.println(defaultText + "saving");
			new File(location + "/lozA").mkdirs();
			try {
				file.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				for (int l = 0; l < 100; l++) {
					String foo = Integer.toString(flags[l]);
					out.write(foo);
					out.newLine();
				}
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
