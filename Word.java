
public class Word implements Comparable<Word> {

	private String word;
	private int edit_distance;

	public Word(String word, int edit_distance) {
		this.word = word;
		this.edit_distance = edit_distance;
	}
	public int getEditDistance() {
		return this.edit_distance;
	}

	public void setEditDistance (int ed) {
		this.edit_distance = ed;
	}
	// override compareTo method so that Word objects are compared to eachother based on their edit distance
	@Override
	public int compareTo(Word other) {
		if(this.edit_distance < other.getEditDistance()) {
			return -1;
		}else if(this.edit_distance > other.getEditDistance()) {
			return 1;
		}else { //they are equal
			return 0;
		}
	}
	// override toString method so that when word objects are printed out it prints out the word followed by its 
	// edit distance in parentheses 
	@Override 
	public String toString() {
		return this.word+"("+this.edit_distance+")";
	}
}
