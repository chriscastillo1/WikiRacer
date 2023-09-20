import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * TODO: You will have to implement memoization somewhere in this class.
 */
public class WikiScraper {
	
	private static Map<String, Set<String>> visitedURLs = new HashMap<>();
	
	/*
	 * A method that goes to a valid wiki website (input string link) and it takes all the 
	 * HTML code from the website and finds all valid links to other wiki websites in
	 * the HTML.
	 * 
	 * Return Value: Returns a Set of all valid links in the HTML code.
	 * 				 (NOTE: These links ARE NOT URL, they are just String names to the
	 * 				 wiki page. ie wiki/PAGENAME)
	 */
	public static Set<String> findWikiLinks(String link) {
		if (visitedURLs.containsKey(link)) {
			return visitedURLs.get(link);
		} else {
			String html = fetchHTML(link);
			Set<String> links = scrapeHTML(html);
			visitedURLs.put(link, links);
			return links;
		}
	}
	
	public static boolean hasVisited(String link) {
		if (visitedURLs.containsKey(link)) {
			return true;
		} else return false;
	}
	
	/*
	 * TODO: Comment this function. What does it do at
	 * a high level. I don't expect you to read/understand
	 * the StringBuffer and while loop. But from the spec
	 * and your understanding of this assignment, what is
	 * the purpose of this function.
	 */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * This is a private helper method that returns a valid url link to another
	 * wiki page. (NOTE: Link is gotten from the findWikiLink method that finds all the
	 * valid links in the HTML code)
	 * 
	 * Return Value: Returns a String URL to a wiki page
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * A method that takes in a HTML website page and it scans through all the
	 * HTML code looking for valid wiki links that follow the formatting:
	 * <a href=\"/wiki/PAGENAME
	 * 
	 * Return Value: Returns a set of all valid links (in the form of strings) from
	 * 				 HTML code. (NOTE: The links are JUST the name, not the entire
	 * 			     url)
	 */
	private static Set<String> scrapeHTML(String html) {
		return findLinks(html, new HashSet<String>());
	}
	
	/*
	 * A private helper method that goes through all the given HTML code to find
	 * all valid HTML links follow the format as described above.
	 * 
	 * Return Value: A set of links (in string format)
	 */
	private static Set<String> findLinks(String html, Set<String> links) {
		// If the html code is emtpy, it returns empty set of links
		if (html.isEmpty()) { return links; }
		
		// Checks if the HTML code contains a valid wiki link (and what index)
		int index = html.indexOf("<a href=\"/wiki/");
		
		if (index == -1) {
			return links;
		} else {		
			// Cuts the string to the pagename
			html = html.substring(index+15);
			
			// Strips the substring of all other formatting like titles, etc.
			String[] link = html.substring(0, html.indexOf("\">")).split("\"");
			
			// Checks if its a valid link, which DO NOT CONTAIN # or :
			if (link[0].contains("#") || link[0].contains(":")) {
				return findLinks(html, links);
			} else {
				links.add(link[0]);
				return findLinks(html, links);
			}
		}
	}
}
