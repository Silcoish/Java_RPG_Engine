package com.corey.silcoish.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * This is the loading and saving of save files
 */

public class Data {

	// Player maxHealth
	// Player Health
	// Invincible
	// Rupees
	// Keys first Dungeon
	
	private static final String DIR = "epicgame";

	public int maxHp = 0;
	public int hp = 0;
	public int invi = 0;
	public int fly = 0;
	public int rupees = 0;
	public int keys = 0;
	public int bombs = 0;
	public int playerX = 0;
	public int playerY = 0;
	public int map = 0;
	public int spiritLevel = 0;
	public long exp = 0;
	public int gameOverX = 0;
	public int gameOverY = 0;
	public int bombBag = 0;
	private int i = 0;

	//loading the savefile into memory
	public Data() {
		String location = System.getenv("APPDATA");
		File file = new File(location + "/" + DIR + "/player.dat");
		if (!file.exists()) {
			System.out.println("Making Directory and save file...");
			new File(location + "/" + DIR).mkdirs();
			try {
				file.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				//Default Player Level
				out.write("1");
				out.newLine();
				//Defualt Player Exp
				out.write("0");
				out.newLine();
				//Default player health
				out.write("10");
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
					i++;
					int foo = Integer.parseInt(str);
					switch (i) {
					case 1:
						maxHp = foo;
						break;
					case 2:
						hp = foo;
						break;
					case 3:
						invi = 0;
						break;
					case 4:
						fly = foo;
						break;
					case 5:
						rupees = foo;
						break;
					case 6:
						keys = foo;
						break;
					case 7:
						bombs = foo;
						break;
					case 8:
						playerX = foo;
						break;
					case 9:
						playerY = foo;
						break;
					case 10:
						map = foo;
						break;
					case 11:
						spiritLevel = foo;
						System.out.println(spiritLevel);
						break;
					case 12:
						exp = Long.parseLong(str);
						break;
					case 13:
						gameOverX = foo;
						break;
					case 14:
						gameOverY = foo;
						break;
					case 15:
						bombBag = foo;
						break;
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Error with the data! Please check the save data for errors!");
					System.exit(0);
				}

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
		File file = new File(location + "/" + DIR + "/player.dat");
		file.delete();
		if (!file.exists()) {
			System.out.println("SAVING...!");
			new File(location + "/" + DIR).mkdirs();
			try {
				file.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				String fooMaxHp = Integer.toString(maxHp);
				out.write(fooMaxHp);
				out.newLine();
				String fooHp = Integer.toString(hp);
				out.write(fooHp);
				out.newLine();
				String fooInvi = Integer.toString(invi);
				out.write(fooInvi);
				out.newLine();
				String fooFly = Integer.toString(fly);
				out.write(fooFly);
				out.newLine();
				String fooRupees = Integer.toString(rupees);
				out.write(fooRupees);
				out.newLine();
				String fooKeys = Integer.toString(keys);
				out.write(fooKeys);
				out.newLine();
				String bombs1 = Integer.toString(bombs);
				out.write(bombs1);
				out.newLine();
				String playerX1 = Integer.toString(playerX);
				out.write(playerX1);
				out.newLine();
				String playerY1 = Integer.toString(playerY);
				out.write(playerY1);
				out.newLine();
				String map1 = Integer.toString(map);
				out.write(map1);
				out.newLine();
				String spiritLevel1 = Integer.toString(spiritLevel);
				out.write(spiritLevel1);
				out.newLine();
				String exp1 = Long.toString(exp);
				out.write(exp1);
				out.newLine();
				String gameOverX1 = Integer.toString(gameOverX);
				out.write(gameOverX1);
				out.newLine();
				String gameOverY1 = Integer.toString(gameOverY);
				out.write(gameOverY1);
				out.newLine();
				String bombBag1 = Integer.toString(bombBag);
				out.write(bombBag1);
				out.newLine();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
