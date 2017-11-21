package ntou.jt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class wordSeg 
{
	public FileWriter fw;
	void readingSentenceAndCheck()
	{
		//寫檔預備
		try {
			fw = new FileWriter("result.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//初始化字典
		ArrayList<String> lex = null;
		try {
			lex = initialLex();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//初始化輸入字串
		ArrayList<String> input = null;
		try {
			input = initialInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//開始分析字串
		for(int k = 0;k < input.size();k++)
		{
			String userInput = input.get(k);
			System.out.println("開始分析字串『"+userInput+"』");
					
			//i~k分析tree結構，開始建立Word lattice
					
			//totalLattice是第二層(最外層)，用來儲存分析過後的latticeTmp，前後順序即為輸入字串的每個字順序
			ArrayList<ArrayList<String>> totalLattice = new ArrayList<ArrayList<String>>();
					
			//把輸入的字每個字分開來分析
			for(int i = 0;i < userInput.length();i++)
			{
				//latticeTmp是第一層的，分析以這個字為開頭有哪些字詞符合(i往後分析，到i = lenth)
				ArrayList<String> latticeTmp = new ArrayList<String>();
						
				//開始分析
				for(int j = i+1;j < userInput.length()+1;j++)
				{
					//userInput.substring(i, j) 為 第i~第j個字
					String word = userInput.substring(i, j);
					//丟進字典分析，如果確定在字典內就丟到arraylist內
					if(checkLexicon(lex,word))
					{
						latticeTmp.add(word);
					}
				}
						
				//把第i個字開頭的分析丟到總Lattice內
				totalLattice.add(latticeTmp);
			}
			//prtTotalLattice(totalLattice);
			checkingLoop("",totalLattice.get(0),0,totalLattice);
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	ArrayList<String> initialLex() throws IOException
	{
		File f = new File("resource/wordSeg_testDictionary_1061.txt"); 

		InputStreamReader read = new InputStreamReader (new FileInputStream(f),"UTF-8"); 

		BufferedReader reader=new BufferedReader(read); 

		String line; 
		ArrayList<String> tmp = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) 
		{ 
			tmp.add(line);
			//System.out.println(line);
		}
		return tmp;
	}
	
	ArrayList<String> initialInput() throws IOException
	{
		File f = new File("resource/wordSeg_testInput_1061.txt"); 

		InputStreamReader read = new InputStreamReader (new FileInputStream(f),"UTF-8"); 

		BufferedReader reader=new BufferedReader(read); 

		String line; 
		ArrayList<String> tmp = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) 
		{ 
			tmp.add(line);
			//System.out.println(line);
		}
		return tmp;
	}
	
	
	void startCheckWord()
	{
		//初始化字典
		ArrayList<String> lex = null;
		try {
			lex = initialLex();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//使用者輸入分析字串
		System.out.println("請輸入欲分析字串：");
		String userInput;
		Scanner input = new Scanner(System.in);
		userInput = input.nextLine();
		System.out.println("開始分析字串『"+userInput+"』");
		
		//i~k分析tree結構，開始建立Word lattice
		
		//totalLattice是第二層(最外層)，用來儲存分析過後的latticeTmp，前後順序即為輸入字串的每個字順序
		ArrayList<ArrayList<String>> totalLattice = new ArrayList<ArrayList<String>>();
		
		//把輸入的字每個字分開來分析
		for(int i = 0;i < userInput.length();i++)
		{
			//latticeTmp是第一層的，分析以這個字為開頭有哪些字詞符合(i往後分析，到i = lenth)
			ArrayList<String> latticeTmp = new ArrayList<String>();
			
			//開始分析
			for(int j = i+1;j < userInput.length()+1;j++)
			{
				//userInput.substring(i, j) 為 第i~第j個字
				String word = userInput.substring(i, j);
				//丟進字典分析，如果確定在字典內就丟到arraylist內
				if(checkLexicon(lex,word))
				{
					latticeTmp.add(word);
				}
			}
			
			//把第i個字開頭的分析丟到總Lattice內
			totalLattice.add(latticeTmp);
		}
		//prtTotalLattice(totalLattice);
		checkingLoop("",totalLattice.get(0),0,totalLattice);
	}
	
	boolean checkLexicon(ArrayList<String> lex,String word)
	{
		for(int i = 0;i < lex.size();i++)
		{
			//如果確認字典內有這個字詞
			if(lex.get(i).equals(word))
			{
				return true;
			}
		}
		return false;
	}
	
	void prtTotalLattice(ArrayList<ArrayList<String>> totalLattice)//這只是用來測試用的~測試我的Lattice是不是正確的
	{
		for(int i = 0;i < totalLattice.size();i++)
		{
			ArrayList<String> tmp = totalLattice.get(i);
			System.out.println("("+tmp.get(0)+"開頭)");
			for(int j = 0;j < tmp.size();j++)
			{
				System.out.println(tmp.get(j));
			}
		}
	}
	
	void checkingLoop(String nowSentence,ArrayList<String> nextLattice,int top,ArrayList<ArrayList<String>> totalLattice)
	{
		//System.out.println("目前分析："+nowSentence);
		
		for(int i = 0;i < nextLattice.size();i++)
		{
			String tmp = nextLattice.get(i);
			
			//結束判定
			if(top+tmp.length() >= totalLattice.size())
			{
				
				try {
					fw.write(nowSentence+tmp+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(nowSentence+tmp);
				return;
			}
			
			checkingLoop(nowSentence+tmp+" ",totalLattice.get(top+tmp.length()),top+tmp.length(),totalLattice);
		}
		
	}
	
	public static void main(String args[])
	{
		wordSeg wordSeg = new wordSeg();
		//這是自己輸入的function
		//wordSeg.startCheckWord();
		//這是讀檔分析的function
		wordSeg.readingSentenceAndCheck();
	}
}
