# Simple Search Engine Project

## Introduction

Hi! 👋 We are Leen Alotaibi, Rand Albarqan, and Reema Al kharaan, and this is our **Simple Search Engine Project**, developed for the CSC 212 course. This project demonstrates our collective understanding of data structures and algorithms by building a search engine capable of indexing, retrieving, and ranking documents efficiently. 

The entire project is implemented without using external libraries or prebuilt data structures.

---

## About the Project

### What it Does
- **Indexes Documents**: Builds mappings between documents and terms for efficient searching.
- **Processes Queries**: Handles simple Boolean queries (`AND`, `OR`) and ranked retrieval using term frequency.
- **Ranks Results**: Provides ordered search results with the most relevant documents at the top.
- **Analyzes Performance**: Compares the efficiency of different indexing techniques.

---

## Features

### 📄 Document Processing
- Reads documents from a CSV file.
- Cleans and tokenizes text:
  - Converts text to lowercase.
  - Removes punctuation and stop words (like "the", "is", "and").
- Builds both **index** and **inverted index** structures.

### 🔍 Query Processing
- **Boolean Retrieval**: Supports queries like `word1 AND word2` or `word1 OR word2`.
- **Ranked Retrieval**: Scores and ranks documents based on term frequency (TF).

### 📊 Performance
- Implements and compares indexing methods:
  - **Basic Index** (Lists).
  - **Inverted Index** (Lists of Lists).
  - **BST-enhanced Inverted Index** for faster lookups.
- Provides Big-O analysis to evaluate the efficiency of each method.

### 🖥️ User Interface
- A menu-driven interface allows users to:
  - Enter Boolean or Ranked queries.
  - View the number of indexed documents and tokens.

---

## How it Works

1. **Document Processing**:
   - Preprocesses text by removing stop words and punctuation, and converting it to lowercase.
   - Builds the index and inverted index.
   
2. **Query Handling**:
   - Processes **Boolean queries** using set operations (intersection for `AND`, union for `OR`).
   - Scores and ranks results for **Ranked Retrieval** using Term Frequency (TF).

3. **Performance Analysis**:
   - Compares the efficiency of list-based and tree-based indexing structures.

---

## Deliverables

- **Index**: Maps documents to words using lists.
- **Inverted Index**: Maps terms to documents containing those terms.
- **BST-enhanced Inverted Index**: Optimizes search performance.
- **Query Processor**: Supports Boolean and Ranked Retrieval.
- **Ranking**: Implements Term Frequency (TF) for relevance scoring.
- **Documentation**: Includes design, implementation details, and performance analysis.
- **Test Menu**: A user-friendly menu for testing the search engine.

---

## Challenges & Learnings

- Building and optimizing **data structures** like lists and BSTs was a great learning experience.
- Implementing both **Boolean Retrieval** and **Ranked Retrieval** posed interesting challenges.
- We enjoyed analyzing the performance and trade-offs between different indexing approaches.

---

## Team Members

- Leen Alotaibi
- Rand Albarqan
- Reema Al kharaan

---

### Thank you for checking out our project! 😊 
