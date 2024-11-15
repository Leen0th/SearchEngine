# Simple Search Engine Project

## Overview

This project is a **Simple Search Engine** developed as part of the CSC 212 course. It focuses on building a search engine capable of **indexing, retrieving, and ranking documents** using Boolean and ranked retrieval methods. The implementation emphasizes data structures, specifically lists and binary search trees (BSTs), to handle the underlying mechanics of the search process.

## Features

### Document Processing
- Reads documents from a CSV file.
- Tokenizes and preprocesses text by:
  - Converting to lowercase.
  - Removing stop words and punctuation.
- Builds an index mapping document IDs to words.

### Indexing
- Creates an inverted index mapping terms to lists of document IDs.
- Enhances the inverted index with Binary Search Trees (BSTs) for efficient lookups.

### Query Processing
- Supports **Boolean Retrieval** (AND, OR queries).
- Implements **Ranked Retrieval** using term frequency (TF) to rank results.

### Ranking
- Scores documents based on query term frequencies.
- Ranks documents in descending order of relevance.

### Performance Analysis
- Compares the efficiency of various indexing methods (basic index, inverted index, BST-based index).
- Includes Big-O analysis in the documentation.

### User Interface
- Provides a simple menu-driven interface for:
  - Boolean queries.
  - Ranked queries.
  - Displaying indexed document and token counts.

## Data Structures

- **Lists**: Used for the document collection, vocabulary, and basic indices.
- **Inverted Index**: Maps terms to document lists for quick retrieval.
- **Binary Search Trees (BSTs)**: Enhance the performance of the inverted index by reducing search time.

## Deliverables

1. **Implementation**:
   - Document processing, indexing, query processing, and ranking.
   - BST-enhanced inverted index.
2. **Performance Analysis**:
   - Comparisons of efficiency between different indexing methods.
3. **Documentation**:
   - Clear explanation of design, implementation, and usage.
   - Class diagram illustrating project structure.
4. **Test Menu**:
   - Boolean Retrieval.
   - Ranked Retrieval.
   - Indexed document and token counts.
5. **CSV-based Input**:
   - Preprocessed dataset of documents for testing.
