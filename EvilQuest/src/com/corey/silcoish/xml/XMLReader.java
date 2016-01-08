package com.corey.silcoish.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.corey.silcoish.entity.Actor;
import com.corey.silcoish.gamestate.MainGameState;
import com.corey.silcoish.levels.Door;
import com.corey.silcoish.levels.Level;
import com.corey.silcoish.tiles.Tile;

/*
 * The XMLReader is used to load the tiles from 
 * tiles.xml and actors  
 */

public class XMLReader {
	
	public HashMap<Integer, Tile> tiles;
	public HashMap<Integer, Level> level;
	public ArrayList<String> text;
	public MainGameState mainGameState;

	public XMLReader(MainGameState mainGameState) {
		this.mainGameState = mainGameState;
		
		LoadTiles();
		LoadText();
		LoadLevelsCSV();
		
	}
	
	public void LoadActors(Level lvl){
		//name,xsprite,ysprite,xtile,ytile,textStartLine,textEndLine,isDoor,newMap,newX,newY
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/maps/" + lvl.mapName + "/actors.txt")));
		try{
			String line = br.readLine();
			while(line != null){
				String[] temp = line.split(",");
				//If isDoor == 0, then it is an actor, otherwise, add it as a door!
				if(Integer.parseInt(temp[7]) == 0){
					Actor actor = new Actor(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), lvl);
					lvl.actors.add(actor);
					for(int i = Integer.parseInt(temp[5]); i <= Integer.parseInt(temp[6]); i++){
						actor.text.add(text.get(i));
					}
				}else{
					//It is a door
					Door door = new Door(Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), lvl, Integer.parseInt(temp[8]), Integer.parseInt(temp[9]), Integer.parseInt(temp[10]));
					lvl.actors.add(door);
				}
				line = br.readLine();
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
	
	public void LoadText(){
		text = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/text.txt")));
		try{
			String line = br.readLine();
			while(line != null){
				text.add(line);
				line = br.readLine();
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

	public void LoadTiles() {
		tiles = new HashMap<>();
		DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = bf.newDocumentBuilder();
			Document document = db.parse(XMLReader.class.getResourceAsStream("/tiles.xml"));
			document.normalize();

			NodeList rootNodes = document.getElementsByTagName("tiles");
			Node rootNode = rootNodes.item(0); 
			Element rootElement = (Element) rootNode;
			NodeList tileList = rootElement.getElementsByTagName("tile");

			for (int i = 0; i < tileList.getLength(); i++) {
				Node theLevel = tileList.item(i);
				Element levelElement = (Element) theLevel;
				System.out.println("Tile ID: " + levelElement.getAttribute("ID") + ", name " + levelElement.getAttribute("name") + ", x: " + levelElement.getAttribute("x") + ", y:" + levelElement.getAttribute("y"));
				tiles.put(Integer.parseInt(levelElement.getAttribute("ID")), new Tile(Integer.parseInt(levelElement.getAttribute("ID")), levelElement.getAttribute("name"), Integer.parseInt(levelElement.getAttribute("solid")), Integer.parseInt(levelElement.getAttribute("x")), Integer.parseInt(levelElement.getAttribute("y")), "test"));
				System.out.println("\n");
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	//TODO: Extract this to it's own class. CSV parsers shouldn't be in the XML class
	public void LoadLevelsCSV(){
		level = new HashMap<Integer, Level>();
		//id, name, width, height, defaultTile, fileName
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/levels.txt")));
	    try {
	        String line = br.readLine();

	        //This while loops through all the levels
	        while (line != null) {
	        	String[] temp = line.split(",");
	        	level.put(Integer.parseInt(temp[0]), new Level(this, mainGameState, Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), tiles.get(Integer.parseInt(temp[4]))));
	        	
	        	BufferedReader lvl1 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/maps/" + temp[1] + "/level.txt")));
	        	BufferedReader lvl2 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/maps/" + temp[1] + "/level2.txt")));
	        	String line1 = lvl1.readLine();
	        	String line2 = lvl2.readLine();
	        	String[] lvlArray1;
	        	String[] lvlArray2;
	        	ArrayList<String[]> allLines1 = new ArrayList<String[]>();
	        	ArrayList<String[]> allLines2 = new ArrayList<String[]>();
	        	//Loops through all the tiles in the level file
	        	while(line1 != null){
	        		lvlArray1 = line1.split(",");
	        		allLines1.add(lvlArray1);
	        		line1 = lvl1.readLine();
	        	}
	        	//Loops through all the tiles in the level file - Overlayer
	        	while(line2 != null){
	        		lvlArray2 = line2.split(",");
	        		allLines2.add(lvlArray2);
	        		line2 = lvl2.readLine();
	        	}
	        	Level lvlID = level.get(Integer.parseInt(temp[0]));
	        	int j = 0;
	        	int k = 0;
	        	//Sets all the tiles for the level
	        	for(int i = 0; i < (Integer.parseInt(temp[2]) * Integer.parseInt(temp[3])); i++){
	        		//System.out.println("[" + k + ", " + j + "]");
	        		lvlID.level[i] = tiles.get(Integer.parseInt(allLines1.get(k)[j]));
	        		//System.out.println(lvlID.level[i].tileName());
	        		j++;
	        		if(j == Integer.parseInt(temp[2])) {
	        			j = 0;
	        			k++;
	        		}
	        	}
	        	
	        	//sets all the overlay tiles
	        	j = 0;
	        	k = 0;
	        	//Sets all the tiles for the level
	        	for(int i = 0; i < (Integer.parseInt(temp[2]) * Integer.parseInt(temp[3])); i++){
	        		//System.out.println("[" + k + ", " + j + "]");
	        		lvlID.level2[i] = tiles.get(Integer.parseInt(allLines2.get(k)[j]));
	        		//System.out.println(lvlID.level[i].tileName());
	        		j++;
	        		if(j == Integer.parseInt(temp[2])) {
	        			j = 0;
	        			k++;
	        		}
	        	}
	        	
	        	//Load actors into the level
	        	LoadActors(lvlID);
	            line = br.readLine();
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
	
}
