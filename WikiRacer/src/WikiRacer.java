import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WikiRacer {

	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
	 * Do not edit the method signature/header of this function
	 * TODO: Fill this function in.
	 */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ queue = new MaxPQ();
		List<String> initial = new ArrayList<>();
		initial.add(start);
		queue.enqueue(initial, 0);
		
		while (!queue.isEmpty()) {
			List<String> current = queue.dequeue();
			String currLink = current.get(current.size() -1);
			
			Set<String> links = WikiScraper.findWikiLinks(currLink);
			
			if (links.contains(end)) { current.add(end); return current; }
			
			for (String nextLink: links) {
				if (!WikiScraper.hasVisited(nextLink)) {
					List<String> currCopy = new ArrayList<>(current);
					currCopy.add(nextLink);
					
					Set<String> nextLinkSet = WikiScraper.findWikiLinks(nextLink);
					nextLinkSet.retainAll(WikiScraper.findWikiLinks(end));
					
					queue.enqueue(currCopy, nextLinkSet.size());
				}
			}
		}
		return new ArrayList<>();
	}
}