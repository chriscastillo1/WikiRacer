import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Author: Chris Castillo
 * Purpose: This program constructs a class to comb through and process the HTML gathered
 * 			from a website to obtain all valid wikipedia links.
 * Class: CSC 210
 * 
 * This program contains a public methods used for processing HTML:
 * findWikiLinks - Returns a set of all valid wiki links from a given wiki page
 * 
 * EXAMPLE USAGE:
 * Set<String> testLinks = WikiScraper.findWikiLinks("Fruit")
 * (NOTE: This returns a set of all valid links found on the wiki page "Fruit")
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above
 */
public class WikiScraper {
	
	private static Map<String, Set<String>> visited = new HashMap<>();
	
	/*
	 * A method that takes in a String link (which is a name of a valid wiki url) and it
	 * looks through all the HTML code and finds all VALID wiki links on the page.
	 * 
	 * Return value: Returns a Set<String> of all valid wiki links found in the HTML code
	 * (NOTE: The links are NOT URLs, they are just the URL name)
	 */
	public static Set<String> findWikiLinks(String link) {
		// MEMOIZATION OF findWikiLinks; If Link already has been searched, returns result
		if (visited.containsKey(link)) {
			return visited.get(link);
			
		} else {
			// Gets the wiki page and scrapes all links from page.
			String html = fetchHTML(link);
			Set<String> links = scrapeHTML(html);
			
			visited.put(link, links);
			return links;
		}
	}
	
	/*
	 * A method that takes in a link name (String format) and it goes creates a valid
	 * website URL (wiki URL) which it goes and reads all the HTML code from the website
	 * and returns it as a String of HTML code.
	 * 
	 * Return Value: A String HTML that contains the entire website HTML from the input
	 * 				 link.
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
	 * A method that takes in a String link name, and it constructs a valid web URL.
	 * 
	 * Return value: Returns a String URL that leads to a wiki page
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
	 * 			     URL)
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
		// If the HTML code is empty, it returns empty set of links
		if (html.isEmpty()) { return links; }
		
		// Checks if the HTML code contains a valid wiki link (and what index)
		int index = html.indexOf("<a href=\"/wiki/");
		
		if (index == -1) {
			return links;
		} else {		
			// Cuts the string to the URL name
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
