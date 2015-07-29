package def;


import java.io.*;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicCrawlController {
  private static final Logger logger = LoggerFactory.getLogger(BasicCrawlController.class);

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      logger.info("Needed parameters: ");
      logger.info("\t rootFolder (it will contain intermediate crawl data)");
      logger.info("\t numberOfCralwers (number of concurrent threads)");
      return;
    }

    /*
     * crawlStorageFolder is a folder where intermediate crawl data is
     * stored.
     */
    String crawlStorageFolder = args[0];

    /*
     * numberOfCrawlers shows the number of concurrent threads that should
     * be initiated for crawling.
     */
    int numberOfCrawlers = Integer.parseInt(args[1]);

    CrawlConfig config = new CrawlConfig();

    config.setCrawlStorageFolder(crawlStorageFolder);

    /*
     * Be polite: Make sure that we don't send more than 1 request per
     * second (1000 milliseconds between requests).
     */
    config.setPolitenessDelay(1000);

    /*
     * You can set the maximum crawl depth here. The default value is -1 for
     * unlimited depth
     */
    config.setMaxDepthOfCrawling(5);

    /*
     * You can set the maximum number of pages to crawl. The default value
     * is -1 for unlimited number of pages
     */
    config.setMaxPagesToFetch(1000);

    config.setIncludeBinaryContentInCrawling(false);

    config.setResumableCrawling(false);

    
    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

   BufferedReader k=new BufferedReader(new FileReader("seeds.txt"));
  //  BufferedReader k=new BufferedReader(new FileReader("norm.txt"));

    String str=k.readLine();
    while(str!=null && str.length()>0)
    {
    	controller.addSeed(str);
    	str=k.readLine();
    }
   // controller.addSeed("http://pdf20.uqnnug.org/");
   // controller.addSeed("http://autocarspecs2015.top/");
   /* controller.addSeed("http://www.ics.uci.edu/");
    controller.addSeed("http://www.ics.uci.edu/~lopes/");
    controller.addSeed("http://www.ics.uci.edu/~welling/");
   */
   k.close(); 
    controller.start(SpamCrawler.class, numberOfCrawlers);
  }
}