import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Author: Chris Castillo
 * Purpose: This program constructs and runs a WikiRacer game, where the goal is to find a
 * 			ladder between two wiki pages. A ladder is defined as a set of links from one
 * 			wiki page to another.
 * 			(ie Milkshake -> Gene, the ladder would look like (Milkshake -> Barley -> Gene)).
 * Class: CSC 210
 * 
 * This class ONLY HAS MAIN public method. But takes in two arguments
 * arg[0] - Wiki page that the WikiRacer starts off on.
 * arg[1] - Wiki page that the WikiRacer ends on (or needs to reach).
 * 
 * (NOTE: Only valid WIKI page link names will work)
 * 
 * EXAMPLE USAGE:
 * args[0] - Emu
 * args[1] - Stanford_University
 * (NOTE: SPELLING MATTERS, and Spaces between words symbolized by underscore _
 * 
 * OUTPUT: [Emu, Food_and_Drug_Administration, Duke_University, Stanford_University]
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above
 */
public class WikiRacer {
	
	private static Set<String> visitedLinks = new HashSet<>();
	
	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
	 * A private method that builds a valid wiki ladder from a start wiki page to a
	 * specified end wiki page.
	 * 
	 * Arguments: String start (Starting wiki page)
	 * 			  String end (Endign wiki page or target wiki page)
	 * 
	 * Return Value: A List<String> that contains String link names that connect
	 * 				 the start wiki page to the end wiki page.
	 * 				 (ie Start = Milkshake, End = gene; Ladder would be
	 * 				  Milkshake -> Barley -> Gene)
	 */
	private static List<String> findWikiLadder(String start, String end) {
		// Creates a Maximum Queue to hold the links
		MaxPQ queue = new MaxPQ();
		List<String> initial = new ArrayList<>();
		initial.add(start);
		queue.enqeueue(initial, 0);
		
		while (!queue.isEmpty()) {
			// Gets a Ladder from the queue and pulls the most current link
			List<String> currLadder = queue.dequeue();
			String currentLink = currLadder.get(currLadder.size() - 1);
			
			// Gets a set of all links from the wiki page (specified by link)
			Set<String> links = WikiScraper.findWikiLinks(currentLink);
			
			if (links.contains(end)) { currLadder.add(end); return currLadder; }
			
			// Runs the findWikiLinks on ALL links in the Set in parallel
			links.parallelStream().forEach(link -> {WikiScraper.findWikiLinks(link);});
			visitedLinks.add(currentLink);
			
			// Goes through each of the neighbor links
			for (String neighbor: links) {
				// If the link hasnt been visited yet, process and add to queue
				if (!visitedLinks.contains(neighbor)) {
					List<String> currCopy = new ArrayList<>(currLadder);
					currCopy.add(neighbor);
					
					// Gets the wiki links for the neighbor and end wiki page
					// And gets only the common links shared between neighbor and end page
					Set<String> nextLinkSet = WikiScraper.findWikiLinks(neighbor);
					nextLinkSet.retainAll(WikiScraper.findWikiLinks(end));
					
					// Adds the ladder to the queue, with priority equal to the number
					// of links in common with the end page
					queue.enqeueue(currCopy,  nextLinkSet.size());
				}
			}
		}
		return new ArrayList<>();
	}
}
