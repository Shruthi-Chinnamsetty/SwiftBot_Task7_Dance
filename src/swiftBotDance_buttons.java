import swiftbot.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
public class swiftBotDance_buttons {
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
	static ArrayList<String> sortedHex = new ArrayList<>();
	static String imagePath;
	static boolean button;

	//static String move = "";
    


    // Code for scanning QR code
    public static void scanQRCode() throws InterruptedException {

    	 try {
             BufferedImage img = swiftBot.getQRImage();
             decodedHex = swiftBot.decodeQRImage(img); // Set decodedText here
             if (!decodedHex.isEmpty()) {
                 System.out.println("Scan successful!");	                            
                 hexValues(decodedHex); // Returns value of 'decodedHex'
             } else {
            	 System.out.println("Invalid Input: Press Y to to capture it again.");	
          
             }
         } catch(IllegalArgumentException e) {
             e.printStackTrace();
             
         }        
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
		System.out.println(redValue);
		greenValue = 3*(decimal % 80);
		System.out.println(greenValue);
		if (greenValue > redValue) {
			blueValue = greenValue;
		}
		else {
			blueValue = redValue;
		}
		System.out.println(blueValue);
		
		int[] colours = {redValue, greenValue, blueValue};
		
		try{
			swiftBot.fillUnderlights(colours);
			Thread.sleep(300);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }  
	}
	
	public static void hexValues(String decodedHex) throws InterruptedException {
		
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
		
	}

	public static void playMusic() {

		int decimalValue = hexToDecimal(decodedHex);
        int octal = decimalToOctal(decimalValue);
        String binary = decimalToBinary(decimalValue);
        int speed = swiftbotSpeed(octal);
	
    	swiftBot.disableButton(Button.Y);
    	swiftBot.disableButton(Button.B);
    	
        // Ask the user if they want to play music
        System.out.println("Would you like the Swiftbot to play music when it dances? Press button Y for yes and button B for no.");
        
        swiftBot.enableButton(Button.Y, () -> {
			System.out.println("Here's the list of music tracks you can choose from!");
			try{
				swiftbotUnderLights(decimalValue);
				danceSequence(decodedHex, binary, speed);
			} catch (Exception e){
				e.printStackTrace();
			}
		});

        swiftBot.enableButton(Button.B, () -> {
			try{
				swiftbotUnderLights(decimalValue);
				danceSequence(decodedHex, binary, speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	public static void danceSequence (String convertedHex, String convertedBinary, int speed) {
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
					System.out.println("Moving forwards for 1 second");
					captureImage();
				} else {
					try{
						swiftBot.move(speed, speed, 500);
					}
					catch (Exception e){
						e.printStackTrace();
						System.exit(5);
					} // Move forward for 0.5 seconds

					System.out.println("Moving forwards for 0.5 seconds");
					captureImage();
				}
				picsTaken += 1;
				imagePath = "/home/pi/Documents/capturedImage/imagestaken.jpg"; // Example image path
				fileHandling(convertedHex, imagePath, picsTaken);

			} else {
				double timeDouble = (((Math.PI) * (14.5))/(speed))*1000;
				int time = (int) Math.round(timeDouble);

        		try{
            		swiftBot.move(speed, speed*(-1) , time);
        		}
        		catch (Exception e){
            		e.printStackTrace();
            		System.exit(5);
        		} // Spin 360 degrees
				System.out.println("Spin");
			}
			swiftBot.disableUnderlights();
			System.out.println("\nEnter X if you wanna run again or B to exit : ");
			enableButtonsForResponse();
			while (button = true){

			}
			button = false;
			System.out.println(picsTaken);
		}
	}

	public static void captureImage() {
		try {
			BufferedImage img = swiftBot.takeStill(ImageSize.SQUARE_144x144);
			ImageIO.write(img, "jpg", new File("/home/pi/Documents/imagestaken.jpg"));
			System.out.println("Picture taken and saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void fileHandling(String decodedHex, String imagePath, int picsTaken) {
		sortedHex.add(decodedHex); // Add the decodedHex to the list

        // Sort the list in ascending order
        Collections.sort(sortedHex);

        try {
            File file = new File("/home/pi/Documents/danceFile1/.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter myWriter = new FileWriter("/home/pi/Documents/danceFile1.txt")) {
            // Write each sorted decodedHex value to the file
            for (String hex : sortedHex) {
                myWriter.write(hex + "\n");
            }
            // Write the number of pictures taken and the image path to the file
            myWriter.write("Number of pictures taken: " + picsTaken + "\n");
            myWriter.write("Image location: " + imagePath);
            System.out.println("Images successfully taken and file updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	// Button handlers during the beginning part 
	public static void enableButtonsForResponse() {
	swiftBot.disableButton(Button.Y);
    	swiftBot.disableButton(Button.B);
		swiftBot.enableButton(Button.Y, () -> {
			swiftBot.setButtonLight(Button.Y, true);
			firstPart();
			swiftBot.setButtonLight(Button.Y, false);
		});
	
		swiftBot.enableButton(Button.B, () -> {
			swiftBot.setButtonLight(Button.B, true);
			fileHandling(decodedHex, imagePath, picsTaken);
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
			System.out.println("See you Again!");
			System.exit(5);
			swiftBot.setButtonLight(Button.B, false);
		});
		button = true;
	}
	
	public static void firstPart() {
		swiftBot.enableButton(Button.Y, () -> {
			swiftBot.setButtonLight(Button.Y, true);
			try {
				scanQRCode();
				enableButtonsForResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swiftBot.setButtonLight(Button.Y, false);
		});
	
		swiftBot.enableButton(Button.B, () -> {
			swiftBot.setButtonLight(Button.B, true);
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
	
			try {
				swiftbotUnderLights(randomDecimal);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			playMusic(); 
			danceSequence(hex, binary, speed);
			enableButtonsForResponse();
			swiftBot.setButtonLight(Button.B, false);
		});
	
		swiftBot.enableButton(Button.X, () -> {
			swiftBot.setButtonLight(Button.X, true);
			System.out.println("Invalid Input. Press button Y to scan your QR code or B if you want a random dance sequence");
			System.out.println("If you want your QR code to be scanned, please click button Y. Else if you want a random dance sequence, click button B.");
			swiftBot.setButtonLight(Button.X, false);
		});
	
		swiftBot.enableButton(Button.A, () -> {
			swiftBot.setButtonLight(Button.A, true);
			System.out.println("Invalid Input. Press button Y to scan your QR code or B if you want a random dance sequence");
			System.out.println("If you want your QR code to be scanned, please click button B. Else if you want a random dance sequence, click button B.");
			swiftBot.setButtonLight(Button.A, false);
		});
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
		System.out.println("If you want your QR code to be scanned, please click button Y. Else if you want a random dance sequence, click button B.");
		firstPart();
	
		// Keep the program running
		while (button) {
			firstPart();
		}
		
	}
}	