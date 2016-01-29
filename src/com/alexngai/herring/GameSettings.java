package com.alexngai.herring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.alexngai.framework.FileIO;

public class GameSettings {

    public static boolean soundEnabled = true;
    public static boolean SFXEnabled = true;
    public static int currentLevel = 0;
    public static int controlState = 3;
    public static int highScore = 0;
	
    public static void save(FileIO files){
    	BufferedWriter output = null;
    	try{
    		//create new file called .savedata
    		output = new BufferedWriter(new OutputStreamWriter(
    				files.writeFile(".savedata")));
    				
    		//write value of each variable separated by \n characters
    		//sound level
    		output.write(Boolean.toString(soundEnabled));
    		output.write("\n");
    		
    		output.write(Integer.toString(currentLevel));
    		output.write("\n");
    		
    		output.write(Integer.toString(controlState));
    		output.write("\n");
    		
    		output.write(Integer.toString(highScore));
    		output.write("\n");
    		
    		output.write(Boolean.toString(SFXEnabled));
    		output.write("\n");
    		
    	} catch (IOException e){
    	} finally {
    		try {
    			if (output != null) output.close();
    		} catch (IOException e){
    		}	
    	}
    }

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            // Reads file called Save Data
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".savedata")));

            // Loads values from the file and replaces default values.
            // loaded and saved IN ORDER
            soundEnabled = Boolean.parseBoolean(in.readLine());
            currentLevel = Integer.parseInt(in.readLine());
            controlState = Integer.parseInt(in.readLine());
            highScore = Integer.parseInt(in.readLine());
            SFXEnabled = Boolean.parseBoolean(in.readLine());
            
        } catch (IOException e) {
            // Catches errors. Default values are used.
        } catch (NumberFormatException e) {
            // Catches errors. Default values are used.
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

	public static boolean isSoundEnabled() {
		return soundEnabled;
	}

	public static int getCurrentLevel() {
		return currentLevel;
	}

	public static int getControlState() {
		return controlState;
	}

	public static void setSoundEnabled(boolean soundEnabled) {
		GameSettings.soundEnabled = soundEnabled;
	}

	public static void setCurrentLevel(int currentLevel) {
		GameSettings.currentLevel = currentLevel;
	}

	public static void setControlState(int controlState) {
		GameSettings.controlState = controlState;
	}

	public static int getHighScore() {
		return highScore;
	}

	public static void setHighScore(int highScore) {
		GameSettings.highScore = highScore;
	}

	public static boolean isSFXEnabled() {
		return SFXEnabled;
	}

	public static void setSFXEnabled(boolean SFXEnabled) {
		GameSettings.SFXEnabled = SFXEnabled;
	}
    
    

}
