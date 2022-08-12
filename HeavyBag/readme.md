Heavy Bag Project 
You will write a class called DenseBag, which will extend the AbstractCollection class. We will be employing Java generics, so your DenseBag can be used to store any type of objects the user desires. The DenseBag must support the "iterator" method, and so you will also be writing an inner class implementing an iterator over the DenseBag.
"Bag" is an abstract data type that is like a set, but allows duplication of elements. By "Dense Bag" we mean a bag that is capable of efficiently handling cases where some of the elements are repeated a huge number of times. For example, if the bag contains 100,000,000 copies of a particular item then you would not want to store 100,000,000 references to the item. Think about how you could store information about the number of copies of each element that are in the bag without actually storing multiple copies.
 
 
