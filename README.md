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
- **Term Retrieval**: Allows users to retrieve documents containing specific terms efficiently.

### 📊 Performance
- Implements and compares indexing methods:
  - **Basic Index** (Lists).
  - **Inverted Index** (Lists of Lists).
  - **BST-enhanced Inverted Index** for faster lookups.
  - **AVL Tree-based Inverted Index** for optimal search and update efficiency.
- Provides Big-O analysis to evaluate the efficiency of each method.

---

## How it Works

1. **Document Processing**:
   - Preprocesses text:
     - Removes stop words and punctuation.
     - Converts text to lowercase.
   - Builds two data structures:
     - **Index**: Maps document IDs to tokens.
     - **Inverted Index**: Maps tokens to document IDs for efficient term retrieval.
   
2. **Query Handling**:
   - **Term Retrieval**: Retrieves documents containing specific terms.
   - **Boolean Retrieval**: Processes queries like `word1 AND word2` or `word1 OR word2` using set operations (intersection and union).
   - **Ranked Retrieval**: Scores documents based on Term Frequency (TF) and ranks them in order of relevance.

3. **Performance Analysis**:
   - Evaluates the time complexity of different indexing methods:
     - **List-based indexing**.
     - **Tree-based indexing (BST and AVL)**.
   
---

## Team Members

- Leen Alotaibi
- Rand Albarqan
- Reema Al kharaan

---

### Thank you for checking out our project! 😊 
