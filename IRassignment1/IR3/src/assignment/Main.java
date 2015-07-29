package assignment;

import java.io.*;
import java.util.*;


public class Main {

	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		
		//String query=args[0];
		
		
		BufferedReader in = new BufferedReader(new FileReader("DataSet.txt"));
		BufferedReader stopFile = new BufferedReader(new FileReader("Stopwords.txt"));
		PrintWriter ipw = new PrintWriter(new FileWriter("InvertedIndex.txt"));
		PrintWriter ppw = new PrintWriter(new FileWriter("PermuteTermIndex.txt"));
		
		Map<String, ArrayList<Integer>> inverted1= new HashMap<String, ArrayList<Integer>>();
	    
		ArrayList<String> stopwords= new ArrayList<String>();
		//read Stopwords.txt
		String stopline = "";
		while ((stopline = stopFile.readLine()) != null) {
	 
			        // use comma as separator
				String[] line1 = stopline.split(",");
				for(int i=0; i<line1.length; i++)
				{
					stopwords.add(line1[i].trim());
				}
			
		}
		

		//read DataSet and initialize hashmap with all terms	
		String document=in.readLine();
		int docCount=1;
		while(document!=null)
		{
			StringTokenizer str=new StringTokenizer(document);
			while(str.hasMoreTokens())
			{
				String token=str.nextToken();
				if(stopwords.contains(token))
					continue;
				
				if(inverted1.containsKey(token)== false)
				{
					ArrayList<Integer> b= new ArrayList<Integer>();
					b.add(docCount);
					inverted1.put(token, b);
				}
				else
				{
					ArrayList<Integer> b= new ArrayList<Integer>();
					b=inverted1.get(token);
					b.add(docCount);
					inverted1.put(token, b);
				}
					
			}
			docCount++;
			document=in.readLine();
		}
		
		
	      List<String> v = new ArrayList<String>(inverted1.keySet());
	      Collections.sort(v);
	      for (String str : v) {
		        ipw.println(str + " " + inverted1.get(str).toString());
		        permute(str, ppw);
		  	  
		      }
	      
		in.close();
		stopFile.close();
		ipw.close();
		ppw.close();
		queryExecute(inverted1);
	}

	private static void queryExecute(Map<String, ArrayList<Integer>> inverted1)throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Ready to execute query:");
		String query=br.readLine();
		String search=query.substring(query.indexOf("*")+1) +"$" + query.substring(0,query.indexOf("*")); 
		
		BufferedReader in1 = new BufferedReader(new FileReader("DataSet.txt"));
		BufferedReader in2 = new BufferedReader(new FileReader("InvertedIndex.txt"));
		BufferedReader in3 = new BufferedReader(new FileReader("PermuteTermIndex.txt"));
		PrintWriter out1 = new PrintWriter(new FileWriter("RetrievedDocuments.txt"));
		
		// Read and store doc lines
		ArrayList<String> doc=new ArrayList<String>();
		String docline=in1.readLine();
		while(docline!= null)
		{
			doc.add(docline);
			docline=in1.readLine();
		}
		
		//System.out.println(doc.size());
		
		
		String permline=in3.readLine();
		//System.out.println(permarray[0]);
		
		while(permline!= null)
		{
			String permarray[]=permline.split(" ");
			
			if(permarray[0].endsWith(search)==true)
			{
			ArrayList<Integer> c=inverted1.get(permarray[1]);
		//	System.out.println(c.size());
			
			for (int i=0; i< c.size(); i++)	
			out1.println(doc.get(c.get(i)-1));	
			}
			permline=in3.readLine();
		}
		
		
		
		
		out1.close();
		in1.close();
		in2.close();
		in3.close();
	}

	static void permute(String f, PrintWriter ppw)throws IOException
	{
		String str=f;
		int len=str.length();
		String str2= str + '$';
		char ch[]=str2.toCharArray();
	
		ppw.print(ch);
		ppw.print(" "+ str);
		ppw.println();
	
	int pos=ch.length;	
	int count=ch.length;
	while(count-- > 1)
	{
		char temp=ch[0];
		for(int i=0; i<pos-1; i++)
		{
			ch[i]=ch[i+1];
		}
		ch[pos-1]=temp;
		ppw.print(ch);
		ppw.print(" "+ str);
		ppw.println();
	}
	
	
	}
		
	}

