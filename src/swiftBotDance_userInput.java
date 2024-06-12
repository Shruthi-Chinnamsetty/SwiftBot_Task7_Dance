import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import swiftbot.ImageSize;
import swiftbot.SwiftBotAPI;

public class swiftBotDance_userInput {
    static SwiftBotAPI swiftBot;
    static Scanner scanner;
    static String decodedHex = ""; 
    static int randomDecimal;
    static String convertedHex = "";
    static int convertedDecimal;
    static int convertedOctal;
    static String convertedBinary = "";
	static int speed;
    static int redValue;
    static int greenValue;
    static int blueValue;
	static int picsTaken = 0;
	static String imagePath;
    static ArrayList<String> currentHex = new ArrayList<>();
    private static volatile boolean stopMusic = false;
    private static volatile boolean danceCompleted = false;
    

    // Code for scanning QR code
    public static void scanQRCode() throws InterruptedException {
        try {
            BufferedImage img = swiftBot.getQRImage();
            decodedHex = swiftBot.decodeQRImage(img); // Set decodedText here
            if (!decodedHex.isEmpty()) {
                System.out.println("Scan successful!");	  
                updateCurrentHex(decodedHex);                         
                conversions(decodedHex); // Returns value of 'decodedHex'
            } else {
                System.out.println("Invalid Input: Press Y to capture it again.");	
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Y")) {
                    scanQRCode(); // Retry scanning
                }
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } 
		//sortedHex.add(decodedHex);       
    }

	// Converts randomly generated decimal into hexadecimal
    public static String randomHex(int randomDecimal) {
		// 0 will be returned if 'randomDecimal' = 0
		if (randomDecimal == 0) {
			return "0";
		}
		
		// Creating an array of all the hexadecimal values
		char[] hexNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; 
		// This string will hold appended values of the remainders 
		String hexValue = "";
		// This string will hold the reversed string of 'hexValue'
		String convertedHex = "";
		
		// This block of code runs only if 'convertedDecimal' > 0
		while (randomDecimal > 0) {
			// The remainder when 'convertedDecimal' is divided by 8 is calculated and stored in 'remainder'
			int remainder = randomDecimal % 16;			
			// The value in 'remainder' is converted to a string and appended to 'octalValue'
			hexValue += hexNumbers[remainder];		
			// 'convertedDecimal' is divided by 8
			randomDecimal /= 16;			
		}
		// Reversing the string 'hexValue'
		for (int i = 0; i < hexValue.length(); i++) {
			convertedHex = hexValue.charAt(i) + convertedHex;
		}
		return convertedHex;
	}

	// Hexadecimal to Decimal conversion 
	public static int hexToDecimal(String decodedText) {
	    if (decodedHex == null || decodedHex.isEmpty()) {  
	        System.out.println("Invalid hex string: " + decodedHex);
	        return 0;
	    }
	    
		// Creating a HashMap which maps each hexadecimal character into integers
		HashMap<Character, Integer> hexDigits = new HashMap<>();
		
		hexDigits.put('0' , 0);
	    hexDigits.put('1' , 1);
	    hexDigits.put('2' , 2);
	    hexDigits.put('3' , 3);
	    hexDigits.put('4' , 4);
	    hexDigits.put('5' , 5);
	    hexDigits.put('6' , 6);
	    hexDigits.put('7' , 7);
	    hexDigits.put('8' , 8);
	    hexDigits.put('9' , 9);
	    hexDigits.put('A' , 10);
	    hexDigits.put('B' , 11);
	    hexDigits.put('C' , 12);
	    hexDigits.put('D' , 13);
	    hexDigits.put('E' , 14);
	    hexDigits.put('F' , 15);
	    
	    // This character holds the first value in 'convertedHex'
	    char firstValue;
	    // This character holds the second value in 'convertedHex'
	    char secondValue;
	    // This integer holds the decimal value of 'convertedHex'
	    int convertedDecimal;
	    
	    // This block of code runs only if the length of 'convertedHex' = 1 
	    if (decodedText.length() == 1) {
	    	// Assigning 0th index of 'convertedHex' into 'firstValue'
	    	firstValue = decodedText.charAt(0);
	    	// 'convertedDecimal' holds the mapped integer of the value in 'firstValue'
	    	convertedDecimal = hexDigits.get(firstValue);
	    }
	    
	    // This block of code runs only if the length of 'convertedHex' != 1 
	    else {
	    	
	    	// Assigning 0th index of 'convertedHex' into 'firstValue'
	    	firstValue = decodedText.charAt(0);
	    	// Assigning 1st index of 'convertedHex' into 'secondValue'
	    	secondValue = decodedText.charAt(1);
	    	 
	    	// The decimal value of 'convertedHex' is calculated by multiplying it's first value's mapped integer by 16 and adding it to the second value's mapped integer 
	    	convertedDecimal = (hexDigits.get(firstValue) * 16) + hexDigits.get(secondValue);
	    }
	    
	    return convertedDecimal;
	}
	
	// Decimal to Octal conversion
	public static int decimalToOctal(int convertedDecimal) {
		// 0 will be returned if 'convertedDecimal' = 0
		if (convertedDecimal == 0) {
			return 0;
		}
		
		// This string will hold appended values of the remainders 
		String octalValue = "";
		// This string will hold the reversed string of 'binaryValue'
		String convertedOctalStr = "";
		
		// This block of code runs only if 'convertedDecimal' > 0
		while (convertedDecimal > 0) {
			// The remainder when 'convertedDecimal' is divided by 8 is calculated and stored in 'remainder'
			int remainder = convertedDecimal % 8;
			// The value in 'remainder' is converted to a string and appended to 'octalValue'
			octalValue += Integer.toString(remainder);
			// 'convertedDecimal' is divided by 8
			convertedDecimal /= 8;
		}
		
		// Reversing the string 'octalValue'
		for (int i = 0; i < octalValue.length(); i++) {
			convertedOctalStr = octalValue.charAt(i) + convertedOctalStr;
		}
		int convertedOctal = Integer.parseInt(convertedOctalStr);
		return convertedOctal;
	}
	
	// Decimal to Binary conversion
	public static String decimalToBinary(int convertedDecimal) {
		// 0 will be returned if 'convertedDecimal' = 0
		if (convertedDecimal == 0) {
			return "0"; 
		}
		
		// This string will hold appended values of the remainders 
		String binaryValue = "";
		// This string will hold the reversed string of 'binaryValue'
		String convertedBinary = "";
		
		// This block of code runs only if 'convertedDecimal' > 0
		while (convertedDecimal > 0) {
			// The remainder when 'convertedDecimal' is divided by 2 is calculated and stored in 'remainder'
			int remainder = convertedDecimal % 2;
			// The value in 'remainder' is converted to a string and appended to 'binaryValue'
			binaryValue += Integer.toString(remainder);
			// 'convertedDecimal' is divided by 2
			convertedDecimal /= 2;
		}
		
		// Reversing the string 'binaryValue'
		for (int i = 0; i < binaryValue.length(); i++) {
			convertedBinary = binaryValue.charAt(i) + convertedBinary;
		}
		
		return convertedBinary;

	}


	// Calculating the speed of the Swiftbot
	public static int swiftbotSpeed(int convertedOctal) {
    	int maximumSpeed = 100;
		int speed = 0;
    	if (convertedOctal > 50) {
    		if (convertedOctal > maximumSpeed) {
    			speed = maximumSpeed;
    		}
    		else {
    			speed = convertedOctal - 50;
    		}
    	}
    	else {
    		speed = convertedOctal + 50;
    	}
    	
		return speed;	
	}

	// Calculating the red, green and blue values of the Swiftbot's underlights
	public static void swiftbotUnderLights(int decimal) throws InterruptedException {

		redValue = decimal;
		System.out.println("Red Value: " + redValue);
		greenValue = 3*(decimal % 80);
		System.out.println("Green Value: " + greenValue);
		if (greenValue > redValue) {
			blueValue = greenValue;
		}
		else {
			blueValue = redValue;
		}
		System.out.println("Blue Value: " + blueValue);
		
		int[] colours = {redValue, greenValue, blueValue};
		
		try{
			swiftBot.fillUnderlights(colours);
			Thread.sleep(300);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }  
	}
	
	public static void conversions(String decodedHex) throws InterruptedException {
		
        int decimalValue = hexToDecimal(decodedHex);
        int octal = decimalToOctal(decimalValue);
        String binary = decimalToBinary(decimalValue);
        int speed = swiftbotSpeed(octal);
        System.out.println("Decimal Value: " + decimalValue);
        System.out.println("Octal Value: " + octal);
        System.out.println("Binary Value: " + binary);
        System.out.println("Hex Value: " + decodedHex);
        System.out.println("Speed: " + speed);
		
		playMusic();
        swiftbotUnderLights(decimalValue);
		danceSequence(decodedHex, binary, speed);
	}

	public static void playMusic() {
	
        // Ask the user if they want to play music
        System.out.println("Do you want to play music too? Press Y else Press X");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Y")) {
            System.out.println("");           
        } else {
            System.out.println("");
        }
	}

    public static void audioBackground(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            while (!stopMusic && clip.isOpen()) {
                Thread.sleep(100);
            }

            audioStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

	public static void danceSequence(String convertedHex, String convertedBinary, int speed) {
		String hex = convertedHex;
		// Reversing the binary string to read it from right to left
		String reversedBinary = new StringBuilder(convertedBinary).reverse().toString();
	
		// Converting the binary string to a character array
		char[] moves = reversedBinary.toCharArray();
	
		// Iterating each move indicated by the binary string that has been reversed
		for (char move : moves) {
			// Checking if the value in array equals to 1
			if (move == '1') {
				// Perform forward movement based on the hexadecimal length
				if (hex.length() == 1) {
					try{
						swiftBot.move(speed, speed, 1000);
					}
					catch (Exception e){
						e.printStackTrace();
						System.exit(5);
					} // Move forward for 1 second

				} 
				else {
					try{
						swiftBot.move(speed, speed, 500);
					}
					catch (Exception e){
						e.printStackTrace();
						System.exit(5);
					} // Move forward for 0.5 seconds
				}
				takePicture();
			} else {
				double timeDouble = (((Math.PI) * (14.5))/(speed))*4500;
				int time = (int) Math.round(timeDouble);

        		try{
            		swiftBot.move(speed, 0 , time);
        		}
        		catch (Exception e){
            		e.printStackTrace();
            		System.exit(5);
        		} // Spin 360 degrees
			}
		}

        stopMusic = true;
	}

	/*
	 * This function saves the picture taken by bot when the binary value is 1 and hex length equal to 1.
	 * We generate a unique id of 8 characters to name the image. So, we can save all of them.
	 */
	public static void takePicture() {
		try {
			File folder = new File("/home/pi/Documents/capturedImage/");
			if (!folder.exists()) {
                folder.mkdirs(); // Creates the folder and any necessary parent folders
            }
			BufferedImage img = swiftBot.takeStill(ImageSize.SQUARE_1080x1080);
			String name = UUID.randomUUID().toString().substring(0, 8);
			
			// Write the image to a file
			File file = new File("/home/pi/Documents/capturedImage/" + name + ".png");
			ImageIO.write(img, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * The current hex array list will contain the hex values of current execution.
	 * Everytime when user scans a QR code or generate random, the hex value will be added by calling this function.
	 */
    public static void updateCurrentHex(String hexvalue) {
        currentHex.add(hexvalue);
    }
    
	/*
	 * This function will work on list of all the Hex Values.
	 * At first, it will fetch all the values from the text files by finding the index of where it starts and ends.
	 * It will put all those hex values inside an array list 
	 * and we add the arraylist of the hex values which we got in the current execution.
	 * Then for sorting, we convert it into integer, sort it and convert it back into hexa-decimals.
	 * Then, we write the arraylist which was list of lines readed back to the file.
	 */
    public static void updateHex() {
		File file = new File("dancefile.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("The Hex Values are:\n\n");
                writer.write("--------------\n");
                writer.write("The number of Images are: 0\n");
                writer.close();
            }
            BufferedReader reader = new BufferedReader(new FileReader("dancefile.txt"));
            ArrayList<String> lines = new ArrayList<>();
            String line;            
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
    
            int hexValuesStartIndex = lines.indexOf("The Hex Values are:");
            int hexValuesEndIndex = lines.indexOf("--------------");
            if (hexValuesStartIndex != -1 && hexValuesEndIndex != -1) {
                ArrayList<String> sortedHex = new ArrayList<>(lines.subList(hexValuesStartIndex + 1, hexValuesEndIndex));
                sortedHex.addAll(currentHex); 

                List<Integer> hexIntegers = new ArrayList<>();
                for (String str : sortedHex) {
                    try{
                        hexIntegers.add(Integer.parseInt(str, 16));
                    }
                    catch(NumberFormatException ex){
                    }
                }
        
                Collections.sort(hexIntegers);
                ArrayList<String> newHex = new ArrayList<>();
                for (Integer value : hexIntegers) {
                    newHex.add(Integer.toHexString(value).toUpperCase());
                }
    
                newHex.add("");
    
                lines.subList(hexValuesStartIndex + 1, hexValuesEndIndex).clear();
                lines.addAll(hexValuesStartIndex + 1, newHex);
            } else {
                System.out.println("Hex values section not found in the file.");
            }
    
            PrintWriter writer = new PrintWriter(new FileWriter("dancefile.txt"));
            for (String updatedLine : lines) {
                writer.println(updatedLine);
            }
            writer.close();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/*
	 * This method will update the value of number of total images that bot has taken.
	 * It will find the line of the number of images in the  text file and replace it with the new value.
	 * It will get the length of the list of files inside that folder and add it as the new value.
	 */
    public static void fileNumberUpdate() {
        String filePath = "dancefile.txt";
        String folderLocation = "/home/pi/Documents/capturedImage/";
  
        File folder = new File(folderLocation);
        File[] files = folder.listFiles();
        long fileCount = (files != null) ? files.length : 0;

        try {
            // Read the file
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("The number of Images are")) {
                    stringBuilder.append("The number of Images are: ").append(fileCount).append("\n");
                } else {
                    stringBuilder.append(line).append("\n");
                }
            }
            reader.close();

            // Write the modified content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(stringBuilder.toString());
            writer.close();

          

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
	
	// User input instead of button clicks
	public static void userInput() {
		System.out.println("If you want your QR code to be scanned, please enter 'QR'.");
		System.out.println("Else if you want a random dance sequence, enter 'R'.");
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("QR")) {
			try {
				scanQRCode();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//playMusic(); 
		} else if (input.equalsIgnoreCase("R")) {
			int minimum = 0;
			int maximum = 255;
			Random random = new Random();
			int randomDecimal = random.nextInt((maximum - minimum) + 1);
			String hex = randomHex(randomDecimal);
			int octal = decimalToOctal(randomDecimal);
			String binary = decimalToBinary(randomDecimal);
			int speed = swiftbotSpeed(octal);
            
			System.out.println("Decimal Value: " + randomDecimal);
			System.out.println("Octal Value: " + octal);
			System.out.println("Binary Value: " + binary);
			System.out.println("Hex Value: " + hex);
			System.out.println("Speed: " + speed);
            
            updateCurrentHex(hex);  

			try {
            	swiftbotUnderLights(randomDecimal);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//playMusic();
			/*
		
			System.out.println("Do you want to play music too? Press Y else Press X");
            String check = scanner.nextLine();
           if (check.equalsIgnoreCase("Y")) {

		
                System.out.println("Here's the music!!!!");
                Thread danceThread = new Thread(() -> {
                    danceSequence(hex, binary, speed);
                    danceCompleted = true;
                });
                Thread musicThread = new Thread(() -> audioBackground("test.wav"));
                danceThread.start();
                musicThread.start();
        
                
                while (!danceCompleted) {
                    try {
                        Thread.sleep(1000); // Check every second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
					   }
                }
            
            } else {
                System.out.println("");
            }*/
			System.out.println("Starting dance sequence...");
			danceSequence(hex, binary, speed);
		} else {
			System.out.println("Invalid input.");
		}
	}

	/*
	 * This function will add the location of the image folder in the text file.
	 * It willcheck if the folder location already exists in the text file.
	 * If it doesn't exists then it will append to the file.  
	 */

	public static void updateLocation(){
		String filePath = "dancefile.txt"; 
			String folderLocation = "/home/pi/documents/captureImage/"; 

			try {
				boolean lineExists = false;
				File file = new File(filePath);

				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("The folder Location:")) {
						lineExists = true;
						break;
					}
				}
				reader.close();

				if (!lineExists) {
					FileWriter writer = new FileWriter(filePath, true);
					writer.write("\nThe folder Location: " + folderLocation + "\n");
					writer.close();
					
				} else {
					
				}

			} catch (IOException e) {
				System.out.println("An error occurred: " + e.getMessage());
				e.printStackTrace();
			}
		}

		/*
		 * We will use this function to run the entire program again.
		 * The function call will be inside the loop. 
		 * It will return true and start executing again if user enters Y.
		 */
	public static Boolean response(){
		System.out.print("\nEnter Y if you wanna run again or type X to exit : ");
		final String input = scanner.nextLine();
		if (input.equalsIgnoreCase("Y")){
			return true;
		}
		else{
			return false;
		}
	}

    public static void main(String args[]) throws Exception {
        swiftBot = new SwiftBotAPI();
        scanner = new Scanner(System.in); // Initialise the scanner
        
        System.out.println(" __      __       .__                               \r\n"
				+ "/  \\    /  \\ ____ |  |   ____  ____   _____   ____  \r\n"
				+ "\\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \r\n"
				+ " \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \r\n"
				+ "  \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  >\r\n"
				+ "       \\/       \\/          \\/            \\/     \\/ \r\n"
				+ "                  To Swiftbot Dance!\r\n"
				+ "****************************************************\r\n"
		);

		userInput();

		while (response()){
        
       	 userInput();
		}
		//fileHandling(decodedHex, imagePath, picsTaken);
        updateHex();
        fileNumberUpdate();
        updateLocation();

		System.out.println("                                                        ███████                                                                   \r\n"
    	 		+ "                                                     ██▓░░░░░░░▓██                                                                \r\n"
    	 		+ "                          ████████                  ██░░░▓▓░░░▓▓░██                                                               \r\n"
    	 		+ "                        ██░░░░░░░░▒████████▒░░░░░░░░░░▒██░░░░░░░█░█                                                               \r\n"
    	 		+ "                       █▓░█▓░░░▓▓▒█▓░░░░░░░░░░░░░░░░░░░░░░░█▒░░░█░██                                                              \r\n"
    	 		+ "                      ██░█░░░░░▒▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██░▓█                                                               \r\n"
    	 		+ "                       █░▓░░░░▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                ░░░░░░░░░░░░░░░░                                \r\n"
    	 		+ "                       ██░▓▓▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██          ░░░░░▓█▒░░░░░░░░░░░░░░░░░░░░                         \r\n"
    	 		+ "                        ██▓█░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██       ░░░░░░▒████████░░░░░░░░░░░░░░░░░░░                     \r\n"
    	 		+ "                          █░░░░░░░░░░░░░░░░░░░░░░░░░░░██░░░░░░░░░░░█      ░░░░░░░███░░░░███░░░░░░░░░░░░░░░░░░░░░                  \r\n"
    	 		+ "                         ██░░░░░░░░░▒██▒░░░░░░░░░░░░░░░░░░░░░░░░░░░██     ░░░░░░██████████░░░░░░░░░░░░░░░░░░░░░░░░░               \r\n"
    	 		+ "                         ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░███░░░░░░░░░▒█     ░░░░░███░░░▓██░░░███░░░░░░░░░░░░░░░░░░░░░░░             \r\n"
    	 		+ "                         ██░░░░░░░░░░▓█▓░░░░░░░░░░░░░░░███░░░░░░░░░░░█     ░░░███░░░░░▓██░░███░░░███░░░░░░░░░░░░░███░░░           \r\n"
    	 		+ "                         ██░░░░░░░░░░███░░░░░░████░░░░░░░░░░░░░░░░░░░██    ░░░▓█████████░░░██▓░███░░░███▒▒███░░░███░░░░░          \r\n"
    	 		+ "                          █░░░░░░░░░░░░░░░░░░░░▒█░░░░░░░░░░░░░░░░░░░░▓█      ░░░░░░░░░░░░░░█████░░░░████▒░░██▒░▒██▓░░░░░          \r\n"
    	 		+ "                         ██░░░░░░░░░░░░░░░░░░░████████░░░░░░░░░░░░░░░██        ░░░░░░░░░░░░███▓░░░░░██░░░▒███░░███░░░░░░          \r\n"
    	 		+ "                         ██░░░░░░░░░░░░░░░░░░░▒▒░▒▒░░░░░░░░░░░░░░░░░▓█           ░░░░░░░░███▓░░░░░░░▓██▓░░░░░░░░░░░░░░░░          \r\n"
    	 		+ "                          █▓░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒██                ░░░░░░░░░░░░░░░░░░░░▓▒░░███░░░░░░░           \r\n"
    	 		+ "                           ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓██                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░             \r\n"
    	 		+ "                             ██▒░░░░░░░░░░░░░░░░░░░░░░░░▒▓█████                        ░░░░░░░░░░░░░░░░░░░░░░░░░░                 \r\n"
    	 		+ "                                ████████▓▓▓▓▓▓▓█████▓░░░░▒█████████████████          ░░░░░░░░░░                                   \r\n"
    	 		+ "                             ████▒░░░░▒█░░░░░░░░░░░░░░░░░░█░░░░░░░░░░░░░░░░██                                                     \r\n"
    	 		+ "                    ███████▓▒░░░░░░░░██░░░░░░░░░░░░░░░░░░░█▓▒░░░░░░░░░░░░░░░░██                                                   \r\n"
    	 		+ "                   ██░░░░░░░░░░░░░░░█▓░░░░░░░░░░░░░░░░░░░░█  ████▓░░░░░░▒███▓▓█                                                   \r\n"
    	 		+ "                   ██░░░░░░░░░░░░░░█▒░░░░░░░░░░░░░░░░░░░░░█       ███▒░░░▒▓▒▓▓█                                                   \r\n"
    	 		+ "                    ██░░░░░░░░░░░██░░░░░░░░░░░░░░░░░░░░░░░█          ███▓▓▓███                                                    \r\n"
    	 		+ "                     ██▓░░░░░░░██░░░░░░░░░░░░░░░░░░░░░░░░░█                                                                       \r\n"
    	 		+ "                         ██▓██░░░░░░░░░░░░░░░░░░░░░░░░░░░░█                                                                       \r\n"
    	 		+ "                          █▓▒█░░░░░░░░░░░░░░░░░░░░░░░░░░░░█                                                                       \r\n"
    	 		+ "                           ████░░░░░░░░░░░░░░░░░░░░░░░░░░██                                                                       \r\n"
    	 		+ "                              █░░░░░░░░░░░░░░░░░░░░░░░░░█                                                                         \r\n"
    	 		+ "                              ██░░░░░▓░░░░░░░░░░░░░░░░███                                                                         \r\n"
    	 		+ "                              ██░░░░░░░▓█▒░░░░░░░░░▒█▓▓██     ████                                                                \r\n"
    	 		+ "                              ██░░░░░░░░░▓█▒▒▒▓███▒░░░░░░░░█▓░░░░▒░█                                                              \r\n"
    	 		+ "                               █▒░░░░░░░░░██  ██▒░░░░░░░░░░░░░░░░█░▓█                                                             \r\n"
    	 		+ "                              ██░░░░░░░░░░░░▓██ ██░░░░░░░░░░░░░░█▓▒██                                                             \r\n"
    	 		+ "                              █▓░░░░░░░░░░░░░▓█    ██░░░░░░░░░▒████                                                               \r\n"
    	 		+ "                              ██░░░░░░░▓░░░▒███      ███░░░░▒█████                                                                \r\n"
    	 		+ "                                █████████████           ██████         ");
		

		System.out.println("\nSee you again!!!");
    }

}
