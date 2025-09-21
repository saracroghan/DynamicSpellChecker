import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DynamicSpellChecker {

	private HashSet<String> dictionary;
	//The constructor for this class generates a dictionary HashSet from the input words.txt file
	// using the loadDictionary method from below
	public DynamicSpellChecker() {
		this.dictionary = new HashSet<String>();
		loadDictionary();
	}
	//The simulate method continually prompts the user for a sentence (loadUserInput()), simplifies the sentence 
	//for easier spell checking (simplify()) identifies misspelled words in the sentence (spellCheck())
	//and gives the user the 5 most similar words that they may have meant. The most similar words are
	// determined by the editDistance() method, called in the generateCorrections() method. 
	public void simulate() {
		loadDictionary();
		ArrayList<String> user_input = loadUserInput();
		ArrayList<String> simplfied_input = simplify(user_input);
		ArrayList<String> msw = spellCheck(user_input, simplfied_input);
		//if there are no misspelled words, tell user good job
		if(msw.size() == 0) {
			System.out.println("You spelled everything correctly -- good job!");
		}else {
		generateCorrections(msw);
		}
		simulate();
	}

	//loadDictionary() takes an input file line by line, and for the String in each line adds new words to 
	//global variable HashSet dictionary, otherwise throwing an exception if error reading file
	public void loadDictionary() {
		String fileName = "words.txt";
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			String word;
			//scans through file until null value is read
			while((word = reader.readLine()) != null) {
				//add word to dictionary
				dictionary.add(word);
			}
			//if error reading file throw a catch
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//loadUserInput() prompts the user for a sentence, and then scans in the sentence as a String. It then splits
	// the sentence into an array of each word in the sentence which is then used to generate an ArrayList that
	// contains every word in the user input sentence in the same order and return this ArrayList
	public ArrayList<String> loadUserInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a sentence: ");
		String input_line;
		//reads in user input
		input_line = scanner.nextLine(); 
		//splits user input string into array of words
		String[] input_array = input_line.split(" ");
		ArrayList<String> input_list = new ArrayList<String>();
		//iterates over array of words to create an array list of these same words
		for(String word: input_array) {
			input_list.add(word);
		}
		return input_list;	
		
	}

	//simplify() takes in an array list of words that is intended to make up the user input, it puts each word 
	// into all lowercase and removes any punctuation for easier processing by other functions down the line. 
	// it also returns an ArrayList with each word, in the same order, but in its simplified form
	public ArrayList<String> simplify(ArrayList<String> input) {
		ArrayList<String> input_simplified = new ArrayList<String>();
		//iterates through every word in user input array list
		for(String word: input) {
			//changes to lowercase
			word = word.toLowerCase();
			// if statements check for punctuation and if found they remove punctuation from the word
			if(word.contains(".")) {
				word = word.replace(".", "");
			}
			if(word.contains(",")) {
				word = word.replace(",", "");
			}
			if(word.contains("?")) {
				word = word.replace("?", "");
			}
			if(word.contains("!")) {
				word = word.replace("!", "");
			}
			//after word has been simplified it is added to an array list the assembles the user input sentence 
			// in simplified form
			input_simplified.add(word);
		}
		return input_simplified;
	}


	//SpellCheck takes in an ArrayList of the original user input (all capitalization and punctuation intact) 
	// and an ArrayList for the simplified user input (all lowercase and no punctuation) it then iterates through
	// each word in the simplified array checks to see if the dictionary contains this word. If the word is found,
	// the word is taken from the original input (so that capitalization and punctuation remain intact) and is 
	// added to an ArrayList called output. If misspelled the word is still taken from the original input but angle 
	// brackets are added on either side to indicate the word is misspelled, or does not exist in the given list
	// the user input sentence is then reconstructed from the output ArrayList and printed.
	// While this method runs all misspelled (or not found words) are recorded in a separate array list (misspelled)
	// that is returned at the end of the method for use in later methods
	public ArrayList<String> spellCheck(ArrayList<String>original_input, ArrayList<String> simplified_input) {
		//keeps track of the word index within the user input sentence
		int counter = 0;
		//create ArrayList to keep track of misspelled words
		ArrayList<String> misspelled_words = new ArrayList<String>();
		ArrayList<String> output = new ArrayList<String>();
		// assume all words are correct, copy original input to output ArrayList
		output.addAll(original_input);
		//iterate through each word in simplified input checking if it is in the dictionary
		for(String word: simplified_input) {
			if(!dictionary.contains(word)) {
				// if the word is not found add the word to misspelled ArrayList
				misspelled_words.add(word);
				// find the same word in the original input by using the counter variable to find the
				// same word at the same index in the original input, but with capitalization/punctuation
				String flagged_word = original_input.get(counter);
				String modified_word = flagged_word;
				//keep track of end of line characters to be added back into output
				if(flagged_word.equals("\n")) {
					modified_word = flagged_word;	
					//add angle bracket around misspelled words while keep punctuation intact
				}else if(flagged_word.contains(".")) {
					flagged_word = flagged_word.replace(".", "");
					modified_word = "<"+flagged_word+">.";
				}else if(flagged_word.contains(",")) {
					flagged_word = flagged_word.replace(",", "");
					modified_word ="<"+flagged_word+">,";
				}else if(flagged_word.contains("?")) {
					flagged_word = flagged_word.replace("?", "");
					modified_word ="<"+flagged_word+">?";
				}else if(flagged_word.contains("!")) {
					flagged_word = flagged_word.replace("!", "");
					modified_word = "<"+flagged_word+">!";
				}else{
					modified_word = "<"+flagged_word+">";
				}
				// after the angle brackets have been added, replace the same word in the output with the 
				// the word modified to have angle brackets
				output.set(counter, modified_word);
			}
			counter ++;
			//take input array list and spell check and create output array list and print
		}
		//From the output ArrayList use string builder to reassemble the sentence, then print the sentence
		StringBuilder output_string = new StringBuilder();
		for(String word: output) {
			output_string.append(word).append(" ");
		}
		System.out.println(output_string.toString());
		return misspelled_words;
	}
	//generateCorrections takes in an ArrayList of misspelled words generated from spellCheck. For each word in 
	//the ArrayList it iterates through the dictionary, making a new dictionary a PriorityQueue of Word objects
	// for each Word object, an edit distance is calculated from the original dictionary word to the misspelled word
	// the edit distance is then used to create a Word object that is then added to the dictionary PriorityQueue. 
	// The PriorityQueue sorts the Word objects from least the greatest edit distance and then prints out the first
	// 5 words from the PriorityQueue dictionary with the smallest edit distance from each misspelled word. 
	public void generateCorrections(ArrayList<String> misspelled) {
		//iterate through misspelled words
		for(String input_word: misspelled) {
			// take not of misspelled word length (m)
			int m = input_word.length();
			//create an individual PriorityQueue dictionary for each misspelled word, sorted by Word object edit distance
			PriorityQueue <Word> dictionary_sorted = new PriorityQueue<Word>();
			//iterate through each word in global HashSet dictionary
			for(String dictionary_word: dictionary) {
				//take note of each word's length
				int n = dictionary_word.length();
				//create a dynamic programming memoization table to be used for computing the edit distance between
				// each dictionary word and the misspelled word
				int[][] dp = new int[n+1][m+1];
				//initialize each memoization table spot to be -1
				for(int i = 0; i < n + 1; i++) {
					Arrays.fill(dp[i], -1);
				}
				//calculate edit distance for each dictionary word
				int edit_distance = editDistance(input_word, dictionary_word, m, n, dp);
				//create a new Word object for each dictionary word and its computer edit distance and add
				// to dictionary PriorityQueue
				dictionary_sorted.add(new Word(dictionary_word, edit_distance));
			}
			System.out.println(input_word);
			// for each misspelled word print out the 5 most similar words or words with the smallest edit distance
			for(int i = 0; i <5; i++) {
				System.out.println("-Did you mean "+dictionary_sorted.poll()+"?");
			}
		}
	}

	// editDistance calculates the number of moves that would need to be made to turn one word (str1) into
	// another word (str2) it takes in both Strings, their respective lengths and a memoization table that has each
	// value already set to -1. As the method recursively calls itself it fills out the memoization table, finding
	// the optimized number of moves required to make the two Strings the same, and returns this number.
	public int editDistance(String str1, String str2, int i, int j, int[][] dp) {

		//base case
		if(i == 0) {
			return j;
		}
		else if(j == 0) {
			return i;
		}
		//check memoization table to see if we already have answer
		if(dp[j][i] != -1) {
			return dp[j][i];
		}

		if(str1.charAt(i-1) == str2.charAt(j-1)) {
			int x = editDistance(str1, str2, i-1, j-1, dp);
			dp[j][i] = x;
			return dp[j][i];
		}

		//try space in x
		int x_space = 1 + editDistance(str1, str2, i, j-1, dp);
		//try space in y
		int y_space = 1 + editDistance(str1, str2, i-1, j, dp);
		//no spaces, align characters normally
		int misalign = 1 + editDistance(str1, str2, i-1, j-1, dp);

		int solution = Math.min( Math.min(x_space,  y_space), misalign );
		dp[j][i] = solution;
		return dp[j][i];

	}



}
