# WikiRacer
Java implementation of Wiki-Racer game. 2 versions, Optimized and Unoptimized versions. Inputs is a string Start, and a string End.

Both are using a MaxPQ which is a priority queue backed by an array. This follows the Binary Max-Heap convention.
Front is at Index 1, not Index 0

WikiScraper is a class that combs through the HTML of wikipedia pages and gathers all valid wikipedia links. Stores them in a set.

# Optimized
Uses Java's Parallel Streams to calculate all links trajectories from Wiki page in parallel.

# Unoptimized Version
Uses traditional method of priority queue and goes through every single link until it finds landing link.
