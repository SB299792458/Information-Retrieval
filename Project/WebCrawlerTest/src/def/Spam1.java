

package def;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;

/**
 *
 * @author Gaurav
 */
public class Spam1 {
   public static ArrayList<String> wordList = null;
   public static ArrayList<String> uniquewordList=null;
    
    
    public static int find_no_of_unique_words(String filename){
        int uniquewords = 0;
        int words=0;
        wordList = new ArrayList<String>(); 
        uniquewordList = new ArrayList<String>(); 
        ArrayList delim = new ArrayList();
        delim.add("+"); delim.add("=");delim.add("!");delim.add("@");delim.add("#");delim.add("$");delim.add("%");delim.add("^");delim.add("&");delim.add("*");delim.add("<");delim.add(">");delim.add("?");delim.add("\\");delim.add("\"");delim.add("'");delim.add("/");delim.add(".");delim.add(",");delim.add("-");   delim.add(" "); delim.add("@");             
        StringBuilder builder = new StringBuilder();
        BufferedReader buffer = null;
        String current,fulltext;
        PrintWriter writer = null;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();
        try{
        
            writer = new PrintWriter("uniquesample.txt","UTF-8");
            buffer = new BufferedReader(new FileReader(filename));
            //writer.println("this is doc"+i);
            current = buffer.readLine();
            current = buffer.readLine();
            while((current = buffer.readLine())!=null){
                builder.append(current);
            }
            
            fulltext = Jsoup.parse(builder.toString()).text();
            
            //String check = "/+-\"\'\\!@#$%^&*()=.~`|";
            String check=",.:;\"(-)?*[]!|><» ";
            StringTokenizer strtok = new StringTokenizer(fulltext,check);
            while(strtok.hasMoreTokens()){
                String str = strtok.nextToken();
                if(!list.contains(str)){
                   list.add(str);
                }
                list1.add(str);
            }
            Iterator itr = list.iterator();
            while(itr.hasNext()){
                
                String str;
                str = itr.next().toString();
               
               if(!delim.contains(str)){
                writer.println(str);
                uniquewordList.add(str.toLowerCase());
                //System.out.println(str);
                uniquewords++;
                 }
            }
            
             Iterator itr1 = list1.iterator();
            while(itr1.hasNext()){
                
                String str;
                str = itr1.next().toString();
               
               if(!delim.contains(str) && str.length()>=1){
                wordList.add(str.toLowerCase());
                words++;
                 }
            }
            //writer.println(fulltext);
            writer.close();    
           
        
        
        }catch(Exception e){
            System.out.println("file not found");
        }
        
        return words;
        
    }
    
    public static int words_in_title(String filename){
        ArrayList delim = new ArrayList();
        delim.add("+"); delim.add("=");delim.add("!");delim.add("@");delim.add("#");delim.add("$");delim.add("%");delim.add("^");delim.add("&");delim.add("*");delim.add("<");delim.add(">");delim.add("?");delim.add("\\");delim.add("\"");delim.add("'");delim.add("/");delim.add(".");delim.add(",");delim.add("-"); delim.add("@");                 
        int no_of_words = 0;
        StringBuilder builder = new StringBuilder();
        BufferedReader buffer = null;
        String current,fulltext;
        
        ArrayList<String> list = new ArrayList<String>();
        try{
            
            buffer = new BufferedReader(new FileReader(filename));
            //writer.println("this is doc"+i);
            current = buffer.readLine();
            current = buffer.readLine();
            while((current = buffer.readLine())!=null){
                builder.append(current);
            }
            fulltext = builder.toString();
            String title = fulltext.substring(fulltext.indexOf("<title>")+7, fulltext.indexOf("</title>"));
            StringTokenizer strtok = new StringTokenizer(title," ");
            while(strtok.hasMoreTokens()){
                String token = strtok.nextToken();
                if(!delim.contains(token)){
                    list.add(token);
                    //System.out.println(token);
                    no_of_words ++;
                }
                
            }
            //System.out.println(title);
        }catch(Exception e){
            System.out.println(e);
        }
        
        return no_of_words;
    }
    
    
    
    
    public static double find_ratio_of_stop_words(){
        double ratio=0,no_of_matches=0,main_list_size=wordList.size();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("list_of_stop_words.txt"));
            String current;
            ArrayList<String> stop_list = new ArrayList<String>();
            while((current = reader.readLine())!=null){
                if(current.length()>1){
                    stop_list.add(current);
                }
            }
            for(int i=0;i<wordList.size();i++){
                String str = wordList.get(i);
                for(int j=0;j<stop_list.size();j++){
                    if(str.equals(stop_list.get(j))){
                        //System.out.println(wordList.get(i));
                        
                        no_of_matches++;
                        break;
                    }
                }
            }
            
          ratio = no_of_matches/main_list_size;  
            
        
        }catch(Exception e){
            System.out.println(e);
        }
        
        
        
        
        
        return ratio;
    }
    
    public static int count_of_trending_words(){
        int count=0;
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("list_of_trending_words.txt"));
            ArrayList<String> list = new ArrayList<String>();
            String current;
            
            while((current=reader.readLine())!=null){
               list.add(current);
            }
            
            for(int i=0;i<uniquewordList.size();i++){
                String str= uniquewordList.get(i);
                for(int j=0;j<list.size();j++){
                    if(str.equals(list.get(j))){
                        //System.out.println(str);
                        count++;
                        break;
                    }
                }
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return count;
    }
    
    public static double average_word_length(){
        double avg=0,sum=0,length;
        
        for(int i=0;i<wordList.size();i++){
            String str = wordList.get(i);
            length = str.length();
            sum = sum + length;
        }
        double length1 = wordList.size();
        avg = sum / length1; 
        
        return avg;
    }
    
    public static double find_size_ratio(){
        double ratio = 0;
        double unique_size = uniquewordList.size();
        
        double total_size = wordList.size();
        //System.out.println(unique_size + "   "+ total_size);
        ratio = total_size / unique_size;
        
        return ratio;
    }
    
    public static double find_3_gram_prob(){
        double ratio=0,sum=1;
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<(wordList.size()-2);i++){
            String current = wordList.get(i)+wordList.get(i+1)+wordList.get(i+2);
            list.add(current);
            //System.out.println(current);
        }
        double len = list.size();
        for(int i=0;i<list.size();i++){
            String current = list.get(i);
            double times = 0;
            for(int j=0;j<list.size();j++){
                if(current.equals(list.get(j))){
                    times++;
                }
            }
            
            double prob = times/len;
            if(prob >0){
                sum = sum+ Math.log(prob);
            }
            
        }
        
        ratio = 0 - (1/len)*(sum);
        
        
        
        return ratio;
    }
    
    public static double find_2_gram_prob(){
        double ratio=0,sum=1;
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<(wordList.size()-1);i++){
            String current = wordList.get(i)+wordList.get(i+1);
            list.add(current);
            //System.out.println(current);
        }
        double len = list.size();
        for(int i=0;i<list.size();i++){
            String current = list.get(i);
            double times = 0;
            for(int j=0;j<list.size();j++){
                if(current.equals(list.get(j))){
                    times++;
                }
            }
            
            double prob = times/len;
            if(prob >0){
                sum = sum+ Math.log(prob);
            }
            
        }
        
        ratio = 0 - (1/len)*(sum);
        
        
        
        return ratio;
    }
    
     public static double find_5_gram_prob(){
        double ratio=0,sum=1;
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<(wordList.size()-4);i++){
            String current = wordList.get(i)+wordList.get(i+1)+wordList.get(i+2)+wordList.get(i+3)+wordList.get(i+4);
            list.add(current);
            //System.out.println(current);
        }
        double len = list.size();
        for(int i=0;i<list.size();i++){
            String current = list.get(i);
            double times = 0;
            for(int j=0;j<list.size();j++){
                if(current.equals(list.get(j))){
                    times++;
                }
            }
            
            double prob = times/len;
            if(prob >0){
                sum = sum+ Math.log(prob);
            }
            
        }
        
        ratio = 0 - (1/len)*(sum);
        
        
        
        return ratio;
    }
    
    public static double find_visibility_ratio(String filename){
        double ratio=0;
        BufferedReader buffer = null;
        PrintWriter writer = null;
        StringBuilder builder = new StringBuilder();
        String current,fulltext;
        try{
            writer = new PrintWriter("Check_size.txt","UTF-8");
            buffer = new BufferedReader(new FileReader(filename));
            //writer.println("this is doc"+i);
            while((current = buffer.readLine())!=null){
                builder.append(current);
            }
            
            fulltext = Jsoup.parse(builder.toString()).text();
            writer.println(fulltext);
            writer.close();
            
            File file = new File("Check_size.txt");
            File file2 = new File(filename);
            double visible_len,total_len;
            visible_len = file.length();
            total_len = file2.length();
            //System.out.println(visible_len + "  "+total_len);
            ratio = visible_len / total_len;
        }catch(Exception e){
            System.out.println(e);
        }
        
        
        return ratio;
        
    }
    
    
    public static void train_from_file(){
        BufferedReader reader = null,reader2=null;
        PrintWriter writer = null;
        String filename,current;
        FileReader file = null;
        int i;
        try{
            writer= new PrintWriter("D:\\spam\\content_results.txt","UTF-8");
            
            reader = new BufferedReader(new FileReader("D:\\spam\\spamFileList.txt"));
            
            while((current=reader.readLine())!=null){
                
              //String filestr = reader.readLine();
            //String current;
             //writer.println("filename--uniquewords--title_words--trending_words--ratio--avg_word_length--size_ratio--n_gram--visibility_ratio");
                file = new FileReader("D:\\spam\\crawlerOp\\"+current);
                if(file==null){
                    continue;
                }
                
                reader2 = new BufferedReader(file);
                String url = reader2.readLine();
                double check = 0;
                filename = "D:\\spam\\crawlerOp\\"+current;
                int uniquewords = find_no_of_unique_words(filename);
                int title_words = words_in_title(filename);
                int trending_words = count_of_trending_words();
                //int trending_words = 100;
                double ratio = find_ratio_of_stop_words();
                double avg_word_length = average_word_length();
                double size_ratio = find_size_ratio();
                double _3_gram = find_3_gram_prob();
                double _2_gram = find_2_gram_prob();
                double _5_gram = find_5_gram_prob();
                double visibility_ratio = find_visibility_ratio(filename);
                //writer.println(urlstr + " --"+uniquewords+"--"+title_words+"--"+trending_words+"--"+ratio+"--"+avg_word_length+"--"+size_ratio+"--"+_2_gram+"--"+_3_gram+"--"+_5_gram+"--"+visibility_ratio+"--1");
                //System.out.println("--"+uniquewords+"--"+title_words+"--"+trending_words+"--"+ratio+"--"+avg_word_length+"--"+size_ratio+"--"+_2_gram+"--"+_3_gram+"--"+_5_gram+"--"+visibility_ratio);                
                check  = check_spam(uniquewords,title_words,trending_words,ratio,avg_word_length,size_ratio,_3_gram,_2_gram,_5_gram,visibility_ratio);
             if(check >=0.67){
                //System.out.print(check);
                System.out.println(current +"  "+ check +"   spam");
                writer.println(url+"-----------> "+   "spam");
            }
            else{
            //System.out.print(check);
            System.out.println(current+"  "+check+"   not spam");
            writer.println(url+" ------------->  not spam");
                }
             
            }
           
            
            //result_writer.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        writer.close();
    }
    
    public static double check_spam(double words,double title_words,double trend_words,double stop_ratio,double avg_word,double size_ratio,double _3_gram,double _2_gram,double _5_gram,double vis_ratio){
        int check = 0;
        double sum=0;
        
        //checking for no of words
        if(words>=1000){
            double value = ((words - 1000)/500)*0.02;
            sum = sum+ 0.5 + value;
        }
        else{
            sum = sum + 0.5;
        }
        
        //checking doe title words
        
        if(title_words>10){
            double value = ((title_words - 10)/5)*0.02;
            sum = sum + 0.5 +value;
        }
        else{
            sum = sum + 0.5;
        }
        
        //checking for avg_word length
        
        if(avg_word>5.5){
            double value = ((avg_word - 5.5)/0.5)*0.05;
            sum = sum + value+0.5;
        }
        else{
            sum = sum+0.5;
        }
        
        //checking visibility
        
        if(vis_ratio>0.25){
        double value = ((vis_ratio-0.25)/0.05)*0.04;    
        sum = sum+value+0.5;
        }
        else{
            sum = sum +0.5;
        }
        
        //checking size ratio
        if(size_ratio>2.5){
            double value = ((size_ratio - 2.5)/0.5)*0.05;
            sum = sum + value+0.5;
        }
        else{
            sum = sum+0.5;
        }
        //check_stop words ratio
        if(stop_ratio>0.25){
            double value = ((stop_ratio-0.25)/0.5)*0.05;
            sum = sum+0.5+value;
        }
        else{
            sum = sum+0.5;
        }
        
        //check_ngram
        if(_3_gram > 8.5){
            double value = ((_3_gram-8.5)/0.5)*0.05;
            sum = sum+0.5+value;
        }
        else if(_3_gram<7.5){
            double value = ((7.5 - _3_gram)/0.5)*0.05;
            sum = sum + value + 0.5;
        }
        else{
            sum = sum+0.5;
        }
        
        if(_2_gram > 8.5){
            double value = ((_2_gram-8.5)/0.5)*0.05;
            sum = sum+0.5+value;
        }
        else if(_2_gram<7.5){
            double value = ((7.5 - _2_gram)/0.5)*0.05;
            sum = sum + value + 0.5;
        }
        else{
            sum = sum+0.5;
        }
        
        if(_5_gram > 8.5){
            double value = ((_5_gram-8.5)/0.5)*0.05;
            sum = sum+0.5+value;
        }
        else if(_5_gram<7.5){
            double value = ((7.5 - _5_gram)/0.5)*0.05;
            sum = sum + value + 0.5;
        }
        else{
            sum = sum+0.5;
        }
        
        double check_value = sum/7.0;
        return check_value;
    }
    
    public static void classify_link(){
       BufferedReader reader = null;
       PrintWriter writer = null;
       String filename;
       try{
           reader = new BufferedReader(new FileReader("input.txt"));
           writer = new PrintWriter("output.txt","UTF-8");
           String current,urlstr;
           urlstr = reader.readLine();
           current= reader.readLine();
           filename = "input.txt";
                int uniquewords = find_no_of_unique_words(filename);
                int title_words = words_in_title(filename);
                int trending_words = count_of_trending_words();
                //int trending_words = 100;
                double ratio = find_ratio_of_stop_words();
                double avg_word_length = average_word_length();
                double size_ratio = find_size_ratio();
                double _3_gram = find_3_gram_prob();
                double _2_gram = find_2_gram_prob();
                double _5_gram = find_5_gram_prob();
                double visibility_ratio = find_visibility_ratio(filename);
                writer.println(urlstr + " --"+uniquewords+"--"+title_words+"--"+trending_words+"--"+ratio+"--"+avg_word_length+"--"+size_ratio+"--"+_2_gram+"--"+_3_gram+"--"+_5_gram+"--"+visibility_ratio);
                writer.close();
           
       }catch(Exception e){
           System.out.println(e);
       }
    }
    
    public static void check_for_given_pages(){
        BufferedReader reader =null;
        FileReader fread = null;
        PrintWriter writer = null;
        String urlstr,filename;
        try{
           writer = new PrintWriter("Content_results.txt","UTF-8");
           int i=0;
           for(i=1;i<100;i++){
               fread = new FileReader("D:\\spam\\IR\\"+i+".txt");
               //if(fread==null){
                //   continue;
               //}
               reader = new BufferedReader(fread);
               urlstr = reader.readLine();
               double check = 0;
                filename = "D:\\spam\\IR\\"+i+".txt";
                int uniquewords = find_no_of_unique_words(filename);
                int title_words = words_in_title(filename);
                int trending_words = count_of_trending_words();
                //int trending_words = 100;
                double ratio = find_ratio_of_stop_words();
                double avg_word_length = average_word_length();
                double size_ratio = find_size_ratio();
                double _3_gram = find_3_gram_prob();
                double _2_gram = find_2_gram_prob();
                double _5_gram = find_5_gram_prob();
                double visibility_ratio = find_visibility_ratio(filename);
                //writer.println(urlstr + " --"+uniquewords+"--"+title_words+"--"+trending_words+"--"+ratio+"--"+avg_word_length+"--"+size_ratio+"--"+_2_gram+"--"+_3_gram+"--"+_5_gram+"--"+visibility_ratio+"--1");
                //System.out.println("--"+uniquewords+"--"+title_words+"--"+trending_words+"--"+ratio+"--"+avg_word_length+"--"+size_ratio+"--"+_2_gram+"--"+_3_gram+"--"+_5_gram+"--"+visibility_ratio);                
                check  = check_spam(uniquewords,title_words,trending_words,ratio,avg_word_length,size_ratio,_3_gram,_2_gram,_5_gram,visibility_ratio);
             if(check >=0.682){
                //System.out.print(check);
                writer.println( urlstr + "   "+check +"   spam");
            }
            else{
            //System.out.print(check);
            writer.println(urlstr + "   "+check+"   not spam");
                }
             
            
           
            writer.close();
               
           }
            
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    
    public static void main(String[] args) {
       URL url;
      InputStream is = null;
      BufferedReader br;
      String line;
      PrintWriter writer = null;
        FileReader reader = null;
       // try{
      /*			
            br = new BufferedReader(new FileReader("input_url.txt"));
            String current;
            writer = new PrintWriter("input.txt","UTF-8");    
        
		while((current=br.readLine())!=null){
		url = new URL(current);
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));

       while ((line = br.readLine()) != null) {
            writer.println(line);
        }   */
            train_from_file();
            //classify_link();
		//check_for_given_pages();	
		
      //  }catch(Exception e){
       //     System.out.println(e);
      //  }
        //int i=find_no_of_unique_words("sample1.html");
        //System.out.println(i);
    }
    
}
//http://creativecommons.org/licenses/by-nd/2.0/