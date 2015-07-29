package def;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class SpamCrawler extends WebCrawler {

  private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

  /**
   * You should implement this function to specify whether the given url
   * should be crawled or not (based on your crawling logic).
   */
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    // Ignore the url if it has an extension that matches our defined set of image extensions.
    if (IMAGE_EXTENSIONS.matcher(href).matches()) {
      return false;
    }

    // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
    //return href.startsWith("http://www.ics.uci.edu/");
    return true;
    
  }

  /**
   * This function is called when a page is fetched and ready to be processed
   * by your program.
   */
  @Override
  public void visit(Page page) {
    int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();
    
    try {
	/*	PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\shikhar\\Desktop\\javaspace\\WebCrawlerTest\\crawlerOp\\"+String.valueOf(docid)+".txt",true)));
		PrintWriter spamfile=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\shikhar\\Desktop\\javaspace\\WebCrawlerTest\\spamText.txt" , true)));
		PrintWriter spamfileList=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\shikhar\\Desktop\\javaspace\\WebCrawlerTest\\spamFileList.txt" , true)));
	*/	
		PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("D:\\spam\\crawlerOp\\"+String.valueOf(docid)+".txt",true)));
		PrintWriter spamfile=new PrintWriter(new BufferedWriter(new FileWriter("D:\\spam\\spamText.txt" , true)));
		PrintWriter spamfileList=new PrintWriter(new BufferedWriter(new FileWriter("D:\\spam\\spamFileList.txt" , true)));
		
		spamfileList.println(String.valueOf(docid) + ".txt");
		//	PrintWriter spamfile=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\shikhar\\Desktop\\javaspace\\WebCrawlerTest\\normText.txt" , true)));
		/*logger.debug("Docid: {}" + docid);
	    logger.info("URL: {}" + url);
	    logger.debug("Domain: '{}'" + domain);
	    logger.debug("Sub-domain: '{}'" + subDomain);
	    logger.debug("Path: '{}'" + path);
	    logger.debug("Parent page: {}" + parentUrl);
	    logger.debug("Anchor text: {}" + anchor);
		 */
		
		if(parentUrl==url)
		pw.println("spam");
			
		pw.println("url->"+ url);
	
		int THRESHHOLD=10;
		pw.println("anchor->"+ anchor );
		if(anchor!=null && anchor.trim().length()> THRESHHOLD)
		spamfile.println(anchor);
		
		
		
		//code to count broken links on current page
		int brokenLinkCount=0;
		/*Document doc = Jsoup.connect(url).get();
		    Connection.Response response = Jsoup.connect(url)
		            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chroe/19.0.1042.0 Safari/535.21")
		            .timeout(10000)
		            .execute();
		int statusCode = response.statusCode();
		 if (statusCode != HttpStatus.SC_OK) {
		    if (statusCode == HttpStatus.SC_NOT_FOUND) {
		    brokenLinkCount++;
		   		  }
		 	}
		*/
		 
		// pw.println(brokenLinkCount);
		 if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			int outgoing= links.size();
			//pw.println(outgoing/brokenLinkCount);
			 
			/*
		      logger.debug("Text length: {}" + text.length());
		      logger.debug("Html length: {}" + html.length());
		      logger.debug("Number of outgoing links: {}" + links.size());
			 */   
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			//List<WebURL> links = htmlParseData.getOutgoingUrls();
			pw.println(html);
			}
	pw.close();
	spamfile.close();
	spamfileList.close();
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
   /* Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
      logger.debug("Response headers:");
      for (Header header : responseHeaders) {
        logger.debug("\t{}: {}" + header.getName() + header.getValue());
      }
      }*/
    

    logger.debug("=============");
  }
}