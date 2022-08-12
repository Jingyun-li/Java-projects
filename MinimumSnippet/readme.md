Given a document (a sequence of words) and set of search terms, my code is able to find the minimal length subsequence in the document that contains all of the search terms. If there are multiple subsequences that have the same minimal length, you may return any one of them.

For example, suppose we are searching for the terms "A", "B", and "C" in the document below:
K W D A I B D W C D W S D B F A C E S D B C D E S A D B X

There are many subsequences of this document that contain all of the search terms. For example:
A I B D W C
C D W S D B F A
B C D E S A
etc.

But, the shortest subsequence that contains all of the search terms is this one:
B F A C
