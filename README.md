# Dynamic Java Spell Checker

A simple, command-line spell checking application built in Java. This tool identifies misspelled words in a user-provided sentence and suggests the most likely corrections based on the Levenshtein (edit distance) algorithm.

---

## Features 

* **Interactive CLI**: Simple and easy-to-use command-line interface.
* **Dictionary-Based Checking**: Cross-references words against a provided `words.txt` file.
* **Error Highlighting**: Clearly flags misspelled words in the output sentence using `<angle brackets>`.
* **Intelligent Suggestions**: Generates the top 5 most similar words for each misspelled entry.
* **Efficient Algorithms**: Uses efficient data structures and algorithms for fast lookups and suggestions.

---

## How It Works 

The program's logic is centered around efficiency and accuracy.

1.  **Dictionary Loading**: On startup, the application loads all words from `words.txt` into a **`HashSet`**. This provides O(1) average time complexity for checking if a word exists in the dictionary.

2.  **Input Processing**: The user's input sentence is split into individual words. Each word is then simplified by converting it to lowercase and removing punctuation to ensure accurate comparison against the dictionary.

3.  **Suggestion Generation**: For each misspelled word, the program calculates its **edit distance** to every word in the dictionary. This is achieved using a recursive implementation of the Levenshtein distance algorithm, which is optimized with **dynamic programming (memoization)** to avoid re-computing results.

4.  **Ranking Suggestions**: A **`PriorityQueue`** is used to efficiently manage the dictionary words based on their calculated edit distance. This data structure keeps the words with the lowest edit distance at the front, allowing the program to quickly retrieve the top 5 most relevant spelling corrections for the user. [Image of a Priority Queue data structure]

---

## Getting Started

Follow these instructions to get the project running on your local machine.

### Prerequisites

* Java Development Kit (JDK) 8 or higher.

### Installation & Execution

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/your-username/your-repository-name.git](https://github.com/your-username/your-repository-name.git)
    cd your-repository-name
    ```

2.  **Ensure `words.txt` is present:**
    This file must be in the root directory of the project. It contains the dictionary the spell checker will use.

3.  **Compile the Java source files:**
    ```sh
    javac src/*.java
    ```

4.  **Run the application:**
    ```sh
    java -cp src DynamicSpellCheckerDemo
    ```

---

## Demo Usage

Once running, the application will prompt you to enter a sentence.
